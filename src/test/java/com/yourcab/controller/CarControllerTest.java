package com.yourcab.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.yourcab.controller.CarController;
import com.yourcab.domainobject.CarDO;
import com.yourcab.domainvalue.EngineType;
import com.yourcab.service.car.CarService;
import com.yourcab.utils.TestUtils;

@RunWith(SpringRunner.class)
@WebMvcTest(CarController.class)
public class CarControllerTest
{
    @Autowired
    private MockMvc mvc;

    @MockBean
    private CarService service;


    @Test
    @WithMockUser(username = "user", password = "pass")
    public void givenCarId_getCar_thenReturnJsonCar() throws Exception
    {
        CarDO car = getMockCar();
        when(service.find(anyLong())).thenReturn(car);

        this.mvc
            .perform(get("/v1/cars/1")).andDo(print()).andExpect(status().isOk())
            .andExpect(jsonPath("$.licensePlate", is(equalTo(car.getLicensePlate()))));
    }


    @Test
    @WithMockUser(username = "user", password = "pass")
    public void givenPlateNumber_getCarByPlateNumber_thenReturnsJsonCar() throws Exception
    {
        CarDO car = getMockCar();
        when(service.find(anyString())).thenReturn(car);

        this.mvc
            .perform(get("/v1/cars/plate/anyplate")).andDo(print()).andExpect(status().isOk())
            .andExpect(jsonPath("$.licensePlate", is(equalTo(car.getLicensePlate()))));
    }


    @Test
    @WithMockUser(username = "user", password = "pass")
    public void givenACar_createCar_thenReturnsJsonCar() throws Exception
    {
        CarDO car = getMockCar();
        when(service.create(any(CarDO.class))).thenReturn(car);

        this.mvc
            .perform(post("/v1/cars").contentType(MediaType.APPLICATION_JSON).content(TestUtils.toJson(car))).andExpect(status().isCreated())
            .andExpect(jsonPath("$.licensePlate", is(equalTo(car.getLicensePlate()))));

    }


    @Test
    @WithMockUser(username = "user", password = "pass")
    public void givenEngineType_getCars_thenReturnsAJsonCarList() throws Exception
    {
        List<CarDO> cars = getMockedCarList();

        when(service.findCars(any(EngineType.class))).thenReturn(cars);

        this.mvc
            .perform(get("/v1/cars/engine").param("engineType", EngineType.GAS.toString())).andDo(print()).andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(2))))
            .andExpect(jsonPath("$[0].licensePlate", is(equalTo(cars.get(0).getLicensePlate()))))
            .andExpect(jsonPath("$[1].licensePlate", is(equalTo(cars.get(1).getLicensePlate()))));

    }


    @Test
    @WithMockUser(username = "user", password = "pass")
    public void givenSeatCount_getCars_thenReturnsAJsonCarList() throws Exception
    {
        List<CarDO> cars = getMockedCarList();

        when(service.findCars(anyInt())).thenReturn(cars);

        this.mvc
            .perform(get("/v1/cars/seatCount").param("seatCount", "4")).andDo(print()).andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(2))))
            .andExpect(jsonPath("$[0].licensePlate", is(equalTo(cars.get(0).getLicensePlate()))))
            .andExpect(jsonPath("$[1].licensePlate", is(equalTo(cars.get(1).getLicensePlate()))));
    }

    private CarDO getMockCar()
    {
        return new CarDO("AZC675", 4, false, EngineType.GAS, "Renault");
    }


    private List<CarDO> getMockedCarList()
    {
        CarDO car1 = new CarDO("AZC675", 4, false, EngineType.GAS, "Renault");
        CarDO car2 = new CarDO("TXE950", 4, false, EngineType.GAS, "Renault");

        return (List<CarDO>) Arrays.asList(car1, car2);
    }

}
