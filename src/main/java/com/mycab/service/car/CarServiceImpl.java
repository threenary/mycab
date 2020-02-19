package com.mycab.service.car;

import java.util.List;

import com.mycab.dataaccessobject.CarRepository;
import com.mycab.domainobject.CarDO;
import com.mycab.domainvalue.EngineType;
import com.mycab.exception.ConstraintsViolationException;
import com.mycab.exception.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

/**
 * Service to encapsulate the link between DAO and controller and to have business logic for some car specific things.
 * <p/>
 */
@Service
public class CarServiceImpl implements CarService
{

    private static Logger LOG = LoggerFactory.getLogger(CarServiceImpl.class);

    private CarRepository carRepository;


    @Autowired
    public CarServiceImpl(CarRepository repository)
    {
        this.carRepository = repository;
    }


    @Override
    public CarDO find(Long carId) throws EntityNotFoundException
    {
        return findCarChecked(carId);
    }


    @Override
    public CarDO create(CarDO carDO) throws ConstraintsViolationException
    {
        CarDO car;
        try
        {
            car = carRepository.save(carDO);
        }
        catch (DataIntegrityViolationException e)
        {
            LOG.warn("Some constraints are thrown due to driver creation", e);
            throw new ConstraintsViolationException(e.getMessage());
        }
        return car;
    }


    @Override
    public void delete(Long carId) throws EntityNotFoundException
    {
        CarDO carDO = findCarChecked(carId);
        carDO.setDeleted(true);
    }


    @Override
    public List<CarDO> findCars(EngineType engineType)
    {
        return carRepository.findByEngineType(engineType);
    }


    @Override
    public List<CarDO> findCars(int seatCount)
    {
        return carRepository.findBySeatCount(seatCount);
    }


    @Override
    public CarDO find(String licensePlate) throws EntityNotFoundException
    {
        CarDO car = carRepository.findByLicensePlate(licensePlate);
        if (car == null)
        {
            throw new EntityNotFoundException("Could not find entity with license plate: " + licensePlate);
        }
        return car;
    }


    private CarDO findCarChecked(Long carId) throws EntityNotFoundException
    {
        CarDO carDO = carRepository.findOne(carId);
        if (carDO == null)
        {
            throw new EntityNotFoundException("Could not find entity with id: " + carId);
        }
        return carDO;
    }

}
