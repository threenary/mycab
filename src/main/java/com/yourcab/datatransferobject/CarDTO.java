package com.yourcab.datatransferobject;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.yourcab.domainvalue.EngineType;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CarDTO
{
    @JsonIgnore
    private Long id;

    @NotNull(message = "License Plate can not be null!")
    private String licensePlate;

    private int seatCount;

    private boolean convertible;

    private EngineType engineType;

    private String manufacturer;

    private int rating;


    public CarDTO(Long id, String licensePlate, int seatCount, boolean convertible, EngineType engineType, String manufacturer, int rating)
    {
        super();
        this.id = id;
        this.licensePlate = licensePlate;
        this.seatCount = seatCount;
        this.convertible = convertible;
        this.engineType = engineType;
        this.manufacturer = manufacturer;
        this.rating = rating;
    }


    private CarDTO()
    {

    }


    @JsonProperty
    public Long getId()
    {
        return id;
    }


    public static CarDTOBuilder newBuilder()
    {
        return new CarDTOBuilder();
    }


    public String getLicensePlate()
    {
        return licensePlate;
    }


    public int getSeatCount()
    {
        return seatCount;
    }


    public boolean isConvertible()
    {
        return convertible;
    }


    public EngineType getEngineType()
    {
        return engineType;
    }


    public String getManufacturer()
    {
        return manufacturer;
    }


    public int getRating()
    {
        return rating;
    }

    public static class CarDTOBuilder
    {
        private Long id;
        private String licensePlate;
        private int seatCount;
        private EngineType engineType;
        private boolean convertible;
        private String manufacturer;
        private int rating;


        public CarDTOBuilder setId(Long id)
        {
            this.id = id;
            return this;
        }


        public CarDTOBuilder setLicensePlate(String licensePlate)
        {
            this.licensePlate = licensePlate;
            return this;
        }


        public CarDTOBuilder setSeatCount(int seatCount)
        {
            this.seatCount = seatCount;
            return this;
        }


        public CarDTOBuilder setEngineType(EngineType engineType)
        {
            this.engineType = engineType;
            return this;
        }


        public CarDTOBuilder setConvertible(boolean convertible)
        {
            this.convertible = convertible;
            return this;
        }


        public CarDTOBuilder setManufacturer(String manufacturer)
        {
            this.manufacturer = manufacturer;
            return this;
        }


        public CarDTOBuilder setRating(int rating)
        {
            this.rating = rating;
            return this;
        }


        public CarDTO createCarDTO()
        {
            return new CarDTO(id, licensePlate, seatCount, convertible, engineType, manufacturer, rating);
        }

    }
}
