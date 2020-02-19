package com.mycab.dataaccessobject;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mycab.domainobject.CarDO;
import com.mycab.domainvalue.EngineType;

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
