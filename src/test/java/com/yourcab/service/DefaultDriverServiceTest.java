package com.yourcab.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import com.yourcab.dataaccessobject.DriverRepository;
import com.yourcab.domainobject.CarDO;
import com.yourcab.domainobject.DriverDO;
import com.yourcab.domainvalue.EngineType;
import com.yourcab.domainvalue.OnlineStatus;
import com.yourcab.exception.BusinessException;
import com.yourcab.exception.CarAlreadyInUseException;
import com.yourcab.exception.EntityNotFoundException;
import com.yourcab.service.car.CarService;
import com.yourcab.service.driver.DefaultDriverService;
import com.yourcab.service.driver.DriverService;

@RunWith(SpringRunner.class)
public class DefaultDriverServiceTest
{

    private DriverService testSubject;

    private DriverRepository repository;

    private CarService carService;


    @Before
    public void setUp()
    {
        repository = mock(DriverRepository.class);
        carService = mock(CarService.class);
        testSubject = new DefaultDriverService(repository, carService);
    }


    @Test(expected = BusinessException.class)
    public void givenValidCar_whenSelectCar_andrDriverOffline_thenBusinessException() throws EntityNotFoundException, BusinessException, CarAlreadyInUseException
    {
        //given
        CarDO car = new CarDO("AZC675", 4, false, EngineType.GAS, "Renault");
        DriverDO driver = new DriverDO("username", "password");

        when(repository.findOne(anyLong())).thenReturn(driver);
        when(carService.find(anyLong())).thenReturn(car);

        //when
        testSubject.selectCar(1L, 1L);
    }


    @Test(expected = CarAlreadyInUseException.class)
    public void givenACarAsignedToOnlineDriver_whenSelectCar_CarAlreadyInUseException() throws EntityNotFoundException, BusinessException, CarAlreadyInUseException
    {
        //given
        CarDO car = new CarDO("AZC675", 4, false, EngineType.GAS, "Renault");
        DriverDO driverAlreadyUsingTheCar = new DriverDO("username", "password", OnlineStatus.ONLINE);
        driverAlreadyUsingTheCar.setCar(car);

        DriverDO driver = new DriverDO("username", "password", OnlineStatus.ONLINE);

        when(repository.findOne(anyLong())).thenReturn(driver);
        when(repository.findByCar(anyLong())).thenReturn(driverAlreadyUsingTheCar);
        when(carService.find(anyLong())).thenReturn(car);

        //when
        testSubject.selectCar(1L, 1L);
    }
    
    @Test
    public void givenACarAsignedToOfflineDriver_whenSelectCar_thenDriverIsUpdated() throws EntityNotFoundException, BusinessException, CarAlreadyInUseException
    {
        //given
        CarDO car = new CarDO("AZC675", 4, false, EngineType.GAS, "Renault");
        DriverDO driverAlreadyUsingTheCar = new DriverDO("username", "password", OnlineStatus.OFFLINE);
        driverAlreadyUsingTheCar.setCar(car);

        DriverDO driver = new DriverDO("username", "password", OnlineStatus.ONLINE);

        when(repository.findOne(anyLong())).thenReturn(driver);
        when(repository.findByCar(anyLong())).thenReturn(driverAlreadyUsingTheCar);
        when(carService.find(anyLong())).thenReturn(car);

        //when
        testSubject.selectCar(1L, 1L);
        
        //then
        verify(repository, times(1)).save(driver);
        verify(repository, times(1)).findOne(anyLong());
        verify(repository, times(1)).findByCar(anyLong());
        verify(carService, times(1)).find(anyLong());
        assertThat(driver.getCar().getLicensePlate(), is(equalTo(car.getLicensePlate())));
    }


    @Test
    public void givenValidCarNotAsignedToDriver_whenSelectCar_thenDriverIsUpdated() throws EntityNotFoundException, BusinessException, CarAlreadyInUseException
    {
        //given
        CarDO car = new CarDO("AZC675", 4, false, EngineType.GAS, "Renault");
        DriverDO driver = new DriverDO("username", "password", OnlineStatus.ONLINE);

        when(repository.findOne(anyLong())).thenReturn(driver);
        when(carService.find(anyLong())).thenReturn(car);

        //when
        testSubject.selectCar(1L, 1L);

        //then
        verify(repository, times(1)).save(driver);
        verify(repository, times(1)).findOne(anyLong());
        verify(repository, times(1)).findByCar(anyLong());
        verify(carService, times(1)).find(anyLong());
        assertThat(driver.getCar().getLicensePlate(), is(equalTo(car.getLicensePlate())));
    }


    @Test
    public void givenNoCar_whenSelectCar_thenDriverIsUpdated() throws EntityNotFoundException
    {
        //given
        CarDO car = new CarDO("AZC675", 4, false, EngineType.GAS, "Renault");
        DriverDO driver = new DriverDO("username", "password");
        driver.setCar(car);

        when(repository.findOne(anyLong())).thenReturn(driver);

        //when
        testSubject.deselectCar(1L);

        //then
        verify(repository, times(1)).save(driver);
        assertThat(driver.getCar(), is(nullValue()));
    }


    @Test
    public void givenCarLinkedToADriver_whenCarAlreadyInUse_returnTrue() throws EntityNotFoundException
    {
        //given
        DriverDO driver = new DriverDO("username", "password");

        when(repository.findByCar(anyLong())).thenReturn(driver);

        //when
        DriverDO result = testSubject.findByCar(55L);

        //then
        verify(repository, times(1)).findByCar(55L);
        assertThat(result.getUsername(), is(equalTo("username")));
    }

}
