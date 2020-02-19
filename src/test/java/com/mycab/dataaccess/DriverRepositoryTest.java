package com.mycab.dataaccess;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.mycab.dataaccessobject.DriverRepository;
import com.mycab.domainobject.CarDO;
import com.mycab.domainobject.DriverDO;
import com.mycab.domainvalue.EngineType;

@RunWith(SpringRunner.class)
@DataJpaTest
public class DriverRepositoryTest
{
    @Autowired
    private TestEntityManager entityManager;
 
    @Autowired
    private DriverRepository repository;
    
    @Ignore
    @Test
    public void whenFindByName_thenReturnDriver() {
        // given
        DriverDO driver = new DriverDO("username", "password");
        CarDO car = new CarDO("AZC675", 4, false, EngineType.GAS, "Renault");
        driver.setCar(car);
        entityManager.persist(driver);
        entityManager.flush();
     
        // when
        DriverDO found = repository.findByCar(car.getId());
     
        // then
        assertThat(found.getUsername(), is(equalTo("username")));
    }

}
