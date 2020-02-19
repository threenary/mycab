package com.mycab.service.driver;

import java.util.List;

import com.mycab.dataaccessobject.DriverRepository;
import com.mycab.domainobject.CarDO;
import com.mycab.domainobject.DriverDO;
import com.mycab.exception.BusinessException;
import com.mycab.exception.CarAlreadyInUseException;
import com.mycab.exception.ConstraintsViolationException;
import com.mycab.exception.EntityNotFoundException;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mycab.domainvalue.GeoCoordinate;
import com.mycab.domainvalue.OnlineStatus;
import com.mycab.service.car.CarService;

/**
 * Service to encapsulate the link between DAO and controller and to have business logic for some driver specific things.
 * <p/>
 */
@Service
public class DefaultDriverService implements DriverService
{

    private static org.slf4j.Logger LOG = LoggerFactory.getLogger(DefaultDriverService.class);

    private final DriverRepository driverRepository;

    private final CarService carService;


    public DefaultDriverService(final DriverRepository driverRepository, final CarService carService)
    {
        this.driverRepository = driverRepository;
        this.carService = carService;
    }


    /**
     * Selects a driver by id.
     *
     * @param driverId
     * @return found driver
     * @throws EntityNotFoundException if no driver with the given id was found.
     */
    @Override
    public DriverDO find(Long driverId) throws EntityNotFoundException
    {
        return findDriverChecked(driverId);
    }


    /**
     * Creates a new driver.
     *
     * @param driverDO
     * @return
     * @throws ConstraintsViolationException if a driver already exists with the given username, ... .
     */
    @Override
    public DriverDO create(DriverDO driverDO) throws ConstraintsViolationException
    {
        DriverDO driver;
        try
        {
            driver = driverRepository.save(driverDO);
        }
        catch (DataIntegrityViolationException e)
        {
            LOG.warn("Some constraints are thrown due to driver creation", e);
            throw new ConstraintsViolationException(e.getMessage());
        }
        return driver;
    }


    /**
     * Deletes an existing driver by id.
     *
     * @param driverId
     * @throws EntityNotFoundException if no driver with the given id was found.
     */
    @Override
    @Transactional
    public void delete(Long driverId) throws EntityNotFoundException
    {
        DriverDO driverDO = findDriverChecked(driverId);
        driverDO.setDeleted(true);
    }


    /**
     * Update the location for a driver.
     *
     * @param driverId
     * @param longitude
     * @param latitude
     * @throws EntityNotFoundException
     */
    @Override
    @Transactional
    public void updateLocation(long driverId, double longitude, double latitude) throws EntityNotFoundException
    {
        DriverDO driverDO = findDriverChecked(driverId);
        driverDO.setCoordinate(new GeoCoordinate(latitude, longitude));
    }


    /**
     * Find all drivers by online state.
     *
     * @param onlineStatus
     */
    @Override
    public List<DriverDO> find(OnlineStatus onlineStatus)
    {
        return driverRepository.findByOnlineStatus(onlineStatus);
    }


    private DriverDO findDriverChecked(Long driverId) throws EntityNotFoundException
    {
        DriverDO driverDO = driverRepository.findOne(driverId);
        if (driverDO == null)
        {
            throw new EntityNotFoundException("Could not find entity with id: " + driverId);
        }
        return driverDO;
    }


    @Override
    public void selectCar(long driverId, long carId) throws EntityNotFoundException, BusinessException, CarAlreadyInUseException
    {
        DriverDO driver = findDriverChecked(driverId);

        if (driver != null && !driver.getOnlineStatus().equals(OnlineStatus.ONLINE))
        {
            throw new BusinessException(String.format("Driver status %s does not allow this operation", driver.getOnlineStatus()));
        }

        DriverDO driverAlreadyUsingTheCar = findByCar(carId);
        if (driverAlreadyUsingTheCar != null && driverAlreadyUsingTheCar.getOnlineStatus().equals(OnlineStatus.ONLINE))
        {

            throw new CarAlreadyInUseException(String.format("Car is already in use by driver %s", driverAlreadyUsingTheCar.getUsername()));
        }

        CarDO car = carService.find(carId);
        driver.setCar(car);
        driverRepository.save(driver);
    }


    @Override
    public void deselectCar(long driverId) throws EntityNotFoundException
    {
        DriverDO driver = findDriverChecked(driverId);
        driver.setCar(null);
        driverRepository.save(driver);
    }


    @Override
    public DriverDO findByCar(long carId) throws EntityNotFoundException
    {
        DriverDO driver = driverRepository.findByCar(carId);
        return driver;
    }
}
