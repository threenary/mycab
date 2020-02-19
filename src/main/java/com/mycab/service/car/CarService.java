package com.mycab.service.car;

import java.util.List;

import com.mycab.domainobject.CarDO;
import com.mycab.domainvalue.EngineType;
import com.mycab.exception.ConstraintsViolationException;
import com.mycab.exception.EntityNotFoundException;

public interface CarService
{

    /**
     * Searches the car linked with the given id
     * @param carId
     * @return {@link CarDO}
     * @throws EntityNotFoundException
     */
    CarDO find(Long carId) throws EntityNotFoundException;


    /**
     * Searches the car linked to the given license plate
     * @param licensePlate
     * @return
     * @throws EntityNotFoundException
     */
    CarDO find(String licensePlate) throws EntityNotFoundException;


    /**
     * Stores the recieved car in the database
     * @param carDO
     * @return {@link CarDO}
     * @throws ConstraintsViolationException
     */
    CarDO create(CarDO carDO) throws ConstraintsViolationException;


    /**
     * Deletes the car linked with the received id
     * @param carId
     * @throws EntityNotFoundException
     */
    void delete(Long carId) throws EntityNotFoundException;


    /**
     * Lists all the cars with the received engine type
     * @param engineType
     * @return {@link List<CarDO>}
     */
    List<CarDO> findCars(EngineType engineType);


    /**
     * Lists all the cars with the received seat count
     * @param engineType
     * @return {@link List<CarDO>}
     */
    List<CarDO> findCars(int seatCount);

}
