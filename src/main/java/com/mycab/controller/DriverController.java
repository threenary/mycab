package com.mycab.controller;

import java.util.List;

import javax.validation.Valid;

import com.mycab.controller.mapper.DriverMapper;
import com.mycab.datatransferobject.DriverDTO;
import com.mycab.domainobject.DriverDO;
import com.mycab.domainvalue.OnlineStatus;
import com.mycab.exception.BusinessException;
import com.mycab.exception.CarAlreadyInUseException;
import com.mycab.exception.ConstraintsViolationException;
import com.mycab.exception.EntityNotFoundException;
import com.mycab.service.driver.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * All operations with a driver will be routed by this controller.
 * <p/>
 */
@RestController
@RequestMapping("v1/drivers")
public class DriverController
{

    private final DriverService driverService;


    @Autowired
    public DriverController(final DriverService driverService)
    {
        this.driverService = driverService;
    }


    @GetMapping("/{driverId}")
    public DriverDTO getDriver(@Valid @PathVariable long driverId) throws EntityNotFoundException
    {
        return DriverMapper.makeDriverDTO(driverService.find(driverId));
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DriverDTO createDriver(@Valid @RequestBody DriverDTO driverDTO) throws ConstraintsViolationException
    {
        DriverDO driverDO = DriverMapper.makeDriverDO(driverDTO);
        return DriverMapper.makeDriverDTO(driverService.create(driverDO));
    }


    @DeleteMapping("/{driverId}")
    public void deleteDriver(@Valid @PathVariable long driverId) throws EntityNotFoundException
    {
        driverService.delete(driverId);
    }


    @PutMapping("/{driverId}")
    public void updateLocation(
        @Valid @PathVariable long driverId,
        @RequestParam double longitude,
        @RequestParam double latitude)
        throws ConstraintsViolationException, EntityNotFoundException
    {
        driverService.updateLocation(driverId, longitude, latitude);
    }


    @GetMapping
    public List<DriverDTO> findDrivers(@RequestParam OnlineStatus onlineStatus)
        throws ConstraintsViolationException, EntityNotFoundException
    {
        return DriverMapper.makeDriverDTOList(driverService.find(onlineStatus));
    }


    @GetMapping("/cars/{carId}")
    public DriverDTO findDriver(@Valid @PathVariable long carId)
        throws ConstraintsViolationException, EntityNotFoundException
    {
        return DriverMapper.makeDriverDTO(driverService.findByCar(carId));
    }


    @PutMapping("/selectCar")
    public void selectCar(
        @RequestParam long driverId,
        @RequestParam long carId)
        throws EntityNotFoundException, BusinessException, CarAlreadyInUseException
    {
        driverService.selectCar(driverId, carId);
    }


    @PutMapping("/deselectCar/{driverId}")
    public void deselectCar(@Valid @PathVariable long driverId) throws EntityNotFoundException
    {
        driverService.deselectCar(driverId);
    }
}
