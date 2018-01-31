package com.yourcab.dataaccess;

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

import com.yourcab.dataaccessobject.DriverRepository;
import com.yourcab.domainobject.CarDO;
import com.yourcab.domainobject.DriverDO;
import com.yourcab.domainvalue.EngineType;

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
