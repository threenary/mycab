package com.yourcab;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yourcab.YourCabServerApplication;
import com.yourcab.controller.DriverController;
import com.yourcab.controller.HomeController;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = YourCabServerApplication.class)
public class YourCabServerApplicantTestApplicationTests
{
    @Autowired
    private HomeController home;

    @Autowired
    private DriverController driver;


    @Test
    public void contexLoads() throws Exception
    {
        assertThat(home).isNotNull();
        assertThat(driver).isNotNull();
    }

}
