package com.mycab.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.mycab.domainobject.CarDO;
import com.mycab.domainobject.DriverDO;
import com.mycab.domainvalue.EngineType;
import com.mycab.domainvalue.OnlineStatus;
import com.mycab.exception.BusinessException;
import com.mycab.exception.CarAlreadyInUseException;
import com.mycab.service.car.CarService;
import com.mycab.service.driver.DriverService;

@RunWith(SpringRunner.class)
@WebMvcTest(DriverController.class)
public class DriverControllerTest
{
    @Autowired
    private MockMvc mvc;

    @MockBean
    private CarService carService;

    @MockBean
    private DriverService driverService;


    @Test
    @WithMockUser(username = "user", password = "pass")
    public void givenCarIdDriverId_whenSelectCar_thenReturn200() throws Exception
    {
        CarDO car = new CarDO("AZC675", 4, false, EngineType.GAS, "Renault");
        DriverDO driver = new DriverDO("username", "password");

        when(carService.find(anyLong())).thenReturn(car);
        when(driverService.find(anyLong())).thenReturn(driver);

        this.mvc
            .perform(put("/v1/drivers/selectCar/").param("driverId", "1").param("carId", "1")).andDo(print())
            .andExpect(status().isOk());
    }


    @Test
    @WithMockUser(username = "user", password = "pass")
    public void givenDriverId_whenDeselectCar_thenReturn200() throws Exception
    {
        CarDO car = new CarDO("AZC675", 4, false, EngineType.GAS, "Renault");
        DriverDO driver = new DriverDO("username", "password");
        driver.setCar(car);

        when(driverService.find(anyLong())).thenReturn(driver);

        this.mvc
            .perform(put("/v1/drivers/deselectCar/{driverId}", 1L).contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());
    }


    @Test
    @WithMockUser(username = "user", password = "pass")
    public void givenCarId_whenFindByCar_thenReturn200() throws Exception
    {
        DriverDO driver = new DriverDO("username", "password");

        when(driverService.findByCar(anyLong())).thenReturn(driver);

        this.mvc
            .perform(get("/v1/drivers/cars/1").contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk())
            .andExpect(jsonPath("$.username", is(equalTo(driver.getUsername()))));;

    }


    @Test
    @WithMockUser(username = "user", password = "pass")
    public void givenACarAsignedToOfflineDriver_whenSelectCar_thenDriverIsUpdated_and200IsReturned() throws Exception
    {
        CarDO car = new CarDO("AZC675", 4, false, EngineType.GAS, "Renault");
        DriverDO driverAlreadyUsingTheCar = new DriverDO("username", "password", OnlineStatus.OFFLINE);
        driverAlreadyUsingTheCar.setCar(car);

        DriverDO driver = new DriverDO("username", "password", OnlineStatus.ONLINE);

        when(driverService.find(anyLong())).thenReturn(driver);
        when(driverService.findByCar(anyLong())).thenReturn(driverAlreadyUsingTheCar);
        when(carService.find(anyLong())).thenReturn(car);

        this.mvc
            .perform(put("/v1/drivers/selectCar").param("driverId", "1").param("carId", "1").contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());

    }


    @Test
    @WithMockUser(username = "user", password = "pass")
    public void whenSelectCar_thenBusinessException() throws Exception
    {
        doThrow(new BusinessException("some message")).when(driverService).selectCar(anyLong(), anyLong());

        this.mvc
            .perform(put("/v1/drivers/selectCar").param("driverId", "1").param("carId", "1").contentType(MediaType.APPLICATION_JSON)).andDo(print())
            .andExpect(status().isBadRequest());

    }
    
    @Test
    @WithMockUser(username = "user", password = "pass")
    public void whenSelectCar_thenCarAlreadyInUseException() throws Exception
    {
        doThrow(new CarAlreadyInUseException("some message")).when(driverService).selectCar(anyLong(), anyLong());

        this.mvc
            .perform(put("/v1/drivers/selectCar").param("driverId", "1").param("carId", "1").contentType(MediaType.APPLICATION_JSON)).andDo(print())
            .andExpect(status().isBadRequest());

    }
}
