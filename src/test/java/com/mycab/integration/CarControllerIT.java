package com.mycab.integration;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.mycab.MyCabServerApplication;
import com.mycab.dataaccessobject.CarRepository;
import com.mycab.domainobject.CarDO;
import com.mycab.domainvalue.EngineType;
import com.mycab.utils.TestUtils;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = MyCabServerApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class CarControllerIT
{

    @Autowired
    private MockMvc mvc;

    @Autowired
    private CarRepository repository;


    @After
    public void tearDown()
    {
        repository.deleteAll();
    }


    @Test
    @WithMockUser(username = "user", password = "pass")
    public void whenValidCar_thenCreateCar() throws IOException, Exception
    {
        CarDO car = new CarDO("CreatedCar", 4, false, EngineType.DIESEL, "MG");
        mvc.perform(
            post("/v1/cars")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.toJson(car)));

        CarDO result = repository.findByLicensePlate("CreatedCar");

        assertThat(result.getEngineType(), is(equalTo(car.getEngineType())));
        assertThat(result.getLicensePlate(), is(equalTo(car.getLicensePlate())));

    }


    @Test
    @WithMockUser(username = "user", password = "pass")
    public void givenGASCars_whenGetEngineType_thenReturnJsonCarArray() throws Exception
    {

        CarDO car1 = new CarDO("TestPlate1", 4, false, EngineType.GAS, "Renault");
        repository.saveAndFlush(car1);
        CarDO car2 = new CarDO("TestPlate2", 4, false, EngineType.GAS, "Renault");
        repository.save(car2);

        mvc
            .perform(get("/v1/cars/engine").param("engineType", EngineType.GAS.toString()).contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(2))))
            .andExpect(jsonPath("$[0].licensePlate", is(equalTo(car1.getLicensePlate()))))
            .andExpect(jsonPath("$[1].licensePlate", is(equalTo(car2.getLicensePlate()))));
    }


    @Test
    @WithMockUser(username = "user", password = "pass")
    public void given4SeatsCar_whenGetSeatCount_thenReturnJsonCarArray() throws Exception
    {

        CarDO car1 = new CarDO("TestPlate1", 7, false, EngineType.GAS, "Renault");
        repository.saveAndFlush(car1);

        mvc
            .perform(get("/v1/cars/seatCount").param("seatCount", "7").contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
            .andExpect(jsonPath("$[0].licensePlate", is(equalTo(car1.getLicensePlate()))));
    }


    @Test
    @WithMockUser(username = "user", password = "pass")
    public void givenValidLicensePlate_whenGetCar_thenReturnJsonCar() throws Exception
    {

        CarDO car1 = new CarDO("TestPlate", 2, false, EngineType.GAS, "Renault");
        repository.saveAndFlush(car1);

        mvc
            .perform(get("/v1/cars/plate/TestPlate").contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.licensePlate", is(equalTo(car1.getLicensePlate()))))
            .andExpect(jsonPath("$.manufacturer", is(equalTo(car1.getManufacturer()))));
    }


    @Test
    @WithMockUser(username = "user", password = "pass")
    public void givenValidCarId_whenGetCar_thenReturnJsonCar() throws Exception
    {
        String plate = "GetCar";
        CarDO car1 = new CarDO(plate, 2, false, EngineType.GAS, "Renault");
        repository.saveAndFlush(car1);

        Long id = repository.findByLicensePlate(plate).getId();

        mvc
            .perform(get("/v1/cars/" + id).contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.licensePlate", is(equalTo(plate))));
    }


    @Test
    @WithMockUser(username = "user", password = "pass")
    public void givenValidCarId_whenDeleteCar_thenCarIsRemoved() throws Exception
    {
        String plate = "DeleteCar";
        CarDO car1 = new CarDO(plate, 2, false, EngineType.GAS, "Renault");
        repository.saveAndFlush(car1);

        Long id = repository.findByLicensePlate(plate).getId();

        mvc
            .perform(delete("/v1/cars/" + id))
            .andDo(print())
            .andExpect(status().isOk());
    }
    
    @Test
    public void givenUnauthorizedAccess_then401IsReturn() throws Exception
    {
        String plate = "DeleteCar";
        CarDO car1 = new CarDO(plate, 2, false, EngineType.GAS, "Renault");
        repository.saveAndFlush(car1);

        Long id = repository.findByLicensePlate(plate).getId();

        mvc
            .perform(delete("/v1/cars/" + id))
            .andDo(print())
            .andExpect(status().isUnauthorized());
    }
}
