package com.yourcab.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.yourcab.controller.mapper.CarMapper;
import com.yourcab.datatransferobject.CarDTO;
import com.yourcab.domainobject.CarDO;
import com.yourcab.domainvalue.EngineType;
import com.yourcab.exception.ConstraintsViolationException;
import com.yourcab.exception.EntityNotFoundException;
import com.yourcab.service.car.CarService;

/**
 * All operations with a car will be routed by this controller.
 * <p/>
 */
@RestController
@RequestMapping("v1/cars")
public class CarController
{

    private final CarService carService;


    @Autowired
    public CarController(final CarService carService)
    {
        this.carService = carService;
    }

    /**
     * Returns the car asociated with the given id
     * @param carId
     * @return {@link CarDTO}
     * @throws EntityNotFoundException
     */
    @GetMapping("/{carId}")
    public CarDTO getCar(@Valid @PathVariable long carId) throws EntityNotFoundException
    {
        return CarMapper.makeCarDTO(carService.find(carId));
    }

    /**
     * Looks for the given license plate in the database
     * @param licensePlate
     * @return {@link CarDTO}
     * @throws EntityNotFoundException
     */
    @GetMapping("/plate/{licensePlate}")
    public CarDTO getCarByLicensePlate(@Valid @PathVariable String licensePlate) throws EntityNotFoundException
    {
        return CarMapper.makeCarDTO(carService.find(licensePlate));
    }

    /**
     * Creates a car based on the data transfer object received
     * @param carDTO
     * @return {@link CarDTO} 
     * @throws ConstraintsViolationException
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CarDTO createCar(@Valid @RequestBody CarDTO carDTO) throws ConstraintsViolationException
    {
        CarDO carDO = CarMapper.makeCarDO(carDTO);
        return CarMapper.makeCarDTO(carService.create(carDO));
    }

    /**
     * Deletes the car linked to the received id
     * @param carId
     * @throws EntityNotFoundException
     */
    @DeleteMapping("/{carId}")
    public void deleteCar(@Valid @PathVariable long carId) throws EntityNotFoundException
    {
        carService.delete(carId);
    }

    /**
     * List all the cars with the given engine type
     * @param engineType
     * @return {@link List<CarDTO>}
     * @throws ConstraintsViolationException
     * @throws EntityNotFoundException
     */
    @GetMapping("/engine")
    public List<CarDTO> findCarsByEngine(@RequestParam EngineType engineType)
        throws ConstraintsViolationException, EntityNotFoundException
    {
        return CarMapper.makeCarDTOList(carService.findCars(engineType));
    }

    /**
     * List all the cars with the given seat count
     * @param seatCount
     * @return {@link List<CarDTO>}
     * @throws ConstraintsViolationException
     * @throws EntityNotFoundException
     */
    @GetMapping("/seatCount")
    public List<CarDTO> findCarsBySeatCount(@RequestParam int seatCount)
        throws ConstraintsViolationException, EntityNotFoundException
    {
        return CarMapper.makeCarDTOList(carService.findCars(seatCount));
    }
}
