package com.yourcab.controller.mapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.yourcab.datatransferobject.CarDTO;
import com.yourcab.domainobject.CarDO;

public class CarMapper
{
    public static CarDO makeCarDO(CarDTO dto)
    {
        return new CarDO(dto.getLicensePlate(), dto.getSeatCount(), dto.isConvertible(), dto.getEngineType(), dto.getManufacturer());
    }


    public static CarDTO makeCarDTO(CarDO car)
    {
        CarDTO.CarDTOBuilder CarDTOBuilder =
            CarDTO
                .newBuilder()
                .setId(car.getId()).setLicensePlate(car.getLicensePlate()).setConvertible(car.isConvertible()).setEngineType(car.getEngineType())
                .setManufacturer(car.getManufacturer()).setSeatCount(car.getSeatCount()).setRating(car.getRating());

        return CarDTOBuilder.createCarDTO();
    }


    public static List<CarDTO> makeCarDTOList(Collection<CarDO> cars)
    {
        return cars
            .stream()
            .map(CarMapper::makeCarDTO)
            .collect(Collectors.toList());
    }
}
