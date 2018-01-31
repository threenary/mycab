package com.yourcab.dataaccessobject;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yourcab.domainobject.CarDO;
import com.yourcab.domainvalue.EngineType;

/**
 * Database Access Object for car table.
 * <p/>
 */
public interface CarRepository extends JpaRepository<CarDO, Long>
{
    CarDO findByLicensePlate(String licensePlate);

    List<CarDO> findByEngineType(EngineType engineType);
    
    List<CarDO> findBySeatCount(int seatCount);
   
}
