package com.yourcab.dataaccessobject;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.yourcab.domainobject.DriverDO;
import com.yourcab.domainvalue.OnlineStatus;

/**
 * Database Access Object for driver table.
 * <p/>
 */
public interface DriverRepository extends CrudRepository<DriverDO, Long>
{

    List<DriverDO> findByOnlineStatus(OnlineStatus onlineStatus);


    DriverDO findByUsername(String username);


    @Query("SELECT p FROM DriverDO p WHERE p.car.id = :carId")
    public DriverDO findByCar(@Param("carId") long carId);
}
