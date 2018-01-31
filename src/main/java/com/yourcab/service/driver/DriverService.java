package com.yourcab.service.driver;

import java.util.List;

import com.yourcab.domainobject.DriverDO;
import com.yourcab.domainvalue.OnlineStatus;
import com.yourcab.exception.BusinessException;
import com.yourcab.exception.CarAlreadyInUseException;
import com.yourcab.exception.ConstraintsViolationException;
import com.yourcab.exception.EntityNotFoundException;

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
