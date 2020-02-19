package com.mycab.service.driver;

import java.util.List;

import com.mycab.domainobject.DriverDO;
import com.mycab.exception.BusinessException;
import com.mycab.exception.CarAlreadyInUseException;
import com.mycab.exception.ConstraintsViolationException;
import com.mycab.exception.EntityNotFoundException;
import com.mycab.domainvalue.OnlineStatus;

public interface DriverService
{

    DriverDO find(Long driverId) throws EntityNotFoundException;


    DriverDO create(DriverDO driverDO) throws ConstraintsViolationException;


    void delete(Long driverId) throws EntityNotFoundException;


    void updateLocation(long driverId, double longitude, double latitude) throws EntityNotFoundException;


    List<DriverDO> find(OnlineStatus onlineStatus);


    void selectCar(long driverId, long carId) throws EntityNotFoundException, BusinessException, CarAlreadyInUseException;


    void deselectCar(long driverId) throws EntityNotFoundException;


    DriverDO findByCar(long carId) throws EntityNotFoundException;
}
