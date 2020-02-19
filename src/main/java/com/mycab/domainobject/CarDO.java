package com.mycab.domainobject;

import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.mycab.domainvalue.EngineType;

@Entity
@Table(
    name = "car",
    uniqueConstraints = @UniqueConstraint(name = "uc_licensePlate", columnNames = {"licensePlate"}))
public class CarDO
{
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime dateCreated = ZonedDateTime.now();

    @Column(nullable = false)
    @NotNull(message = "License plate can not be null!")
    private String licensePlate;

    @Column(nullable = false)
    private int seatCount;

    @Column(nullable = false)
    private boolean convertible;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EngineType engineType;

    @Column(nullable = false)
    private String manufacturer;

    @Column(nullable = false)
    private boolean deleted;

    @Column(nullable = false)
    private int rating;


    private CarDO()
    {

    }


    public CarDO(String licensePlate, int seatCount, boolean convertible, EngineType engineType, String manufacturer)
    {
        super();
        this.licensePlate = licensePlate;
        this.seatCount = seatCount;
        this.convertible = convertible;
        this.deleted = false;
        this.engineType = engineType;
        this.manufacturer = manufacturer;
        this.rating = 1;
    }


    public Long getId()
    {
        return id;
    }


    public void setId(Long id)
    {
        this.id = id;
    }


    public ZonedDateTime getDateCreated()
    {
        return dateCreated;
    }


    public void setDateCreated(ZonedDateTime dateCreated)
    {
        this.dateCreated = dateCreated;
    }


    public String getLicensePlate()
    {
        return licensePlate;
    }


    public void setLicensePlate(String licensePlate)
    {
        this.licensePlate = licensePlate;
    }


    public int getSeatCount()
    {
        return seatCount;
    }


    public void setSeatCount(int seatCount)
    {
        this.seatCount = seatCount;
    }


    public boolean isConvertible()
    {
        return convertible;
    }


    public void setConvertible(boolean convertible)
    {
        this.convertible = convertible;
    }


    public EngineType getEngineType()
    {
        return engineType;
    }


    public void setEngineType(EngineType engineType)
    {
        this.engineType = engineType;
    }


    public String getManufacturer()
    {
        return manufacturer;
    }


    public void setManufacturer(String manufacturer)
    {
        this.manufacturer = manufacturer;
    }


    public boolean isDeleted()
    {
        return deleted;
    }


    public void setDeleted(boolean deleted)
    {
        this.deleted = deleted;
    }


    public int getRating()
    {
        return rating;
    }


    public void setRating(int rating)
    {
        this.rating = rating;
    }

}
