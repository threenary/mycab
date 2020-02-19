package com.mycab.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.isIn;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import com.mycab.dataaccessobject.CarRepository;
import com.mycab.domainobject.CarDO;
import com.mycab.domainvalue.EngineType;
import com.mycab.exception.ConstraintsViolationException;
import com.mycab.exception.EntityNotFoundException;
import com.mycab.service.car.CarService;
import com.mycab.service.car.CarServiceImpl;

@RunWith(SpringRunner.class)
public class CarServiceImplTest
{

    private CarService testSubject;

    private CarRepository carRepository;


    @Before
    public void setUp()
    {
        carRepository = mock(CarRepository.class);
        testSubject = new CarServiceImpl(carRepository);
    }


    @Test
    public void whenFindCar_thenReturnCar() throws EntityNotFoundException
    {
        //given
        CarDO car = new CarDO("AZC675", 4, false, EngineType.GAS, "Renault");
        when(carRepository.findOne(anyLong())).thenReturn(car);

        //when
        CarDO result = testSubject.find(new Long(55));

        //then
        assertThat(result.getLicensePlate(), is(equalTo(car.getLicensePlate())));
    }


    @Test
    public void whenFindCarByLicensePlate_thenReturnCar() throws EntityNotFoundException
    {
        //given
        CarDO car = new CarDO("AZC675", 4, false, EngineType.GAS, "Renault");
        when(carRepository.findByLicensePlate(anyString())).thenReturn(car);

        //when
        CarDO result = testSubject.find("AZC675");

        //then
        assertThat(result.getLicensePlate(), is(equalTo(car.getLicensePlate())));
    }


    @Test
    public void whenCreateCar_newCarIsCreated() throws ConstraintsViolationException
    {
        //given
        CarDO car = new CarDO("AZC675", 4, false, EngineType.GAS, "Renault");
        when(carRepository.save(any(CarDO.class))).thenReturn(car);

        //when
        CarDO result = testSubject.create(car);

        //then
        assertThat(result.getLicensePlate(), is(equalTo(car.getLicensePlate())));
    }


    @Test
    public void whenDeleteCar_carIsDeleted() throws EntityNotFoundException
    {
        //given
        CarDO car = new CarDO("AZC675", 4, false, EngineType.GAS, "Renault");
        when(carRepository.findOne(anyLong())).thenReturn(car);

        //when
        testSubject.delete(55L);

        //then
        Mockito.verify(carRepository, times(1)).findOne(anyLong());
        assertThat(car.isDeleted(), is(true));
    }


    @Test
    public void whenFindByEngine_thenReturnListOfCars()
    {
        //given    
        CarDO renault = new CarDO("AZC675", 4, false, EngineType.GAS, "Renault");
        CarDO ford = new CarDO("TKB587", 4, false, EngineType.GAS, "Ford");
        List<CarDO> carList = Arrays.asList(renault, ford);

        when(carRepository.findByEngineType(any(EngineType.class))).thenReturn(carList);

        //when
        List<CarDO> results = testSubject.findCars(EngineType.GASOLINE);

        //then
        assertThat(results.size(), is(2));
        assertThat(renault, isIn(results));
        assertThat(ford, isIn(results));
    }


    @Test
    public void whenFindBySeatCount_thenReturnListOfCars()
    {
        //given    
        CarDO renault = new CarDO("AZC675", 4, false, EngineType.GAS, "Renault");
        CarDO ford = new CarDO("TKB587", 4, false, EngineType.GAS, "Ford");
        List<CarDO> carList = Arrays.asList(renault, ford);

        when(carRepository.findBySeatCount(anyInt())).thenReturn(carList);

        //when
        List<CarDO> results = testSubject.findCars(4);

        //then
        assertThat(results.size(), is(2));
        assertThat(renault, isIn(results));
        assertThat(ford, isIn(results));
    }
}
