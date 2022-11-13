package com.example.shuttlemobile.vehicle;

import com.example.shuttlemobile.driver.Driver;

public class Vehicle {
    public boolean isBaby() {
        return baby;
    }

    public int getSpots() {
        return spots;
    }

    public String getPlate() {
        return plate;
    }

    public String getLicenceNum() {
        return licenceNum;
    }

    public String getModel() {
        return model;
    }

    public Driver getDriver() {
        return driver;
    }

    boolean baby;

    public Vehicle(boolean baby, int spots, String plate, String licenceNum, String model, Driver driver) {
        this.baby = baby;
        this.spots = spots;
        this.plate = plate;
        this.licenceNum = licenceNum;
        this.model = model;
        this.driver = driver;
    }

    int spots;
    String plate;
    String licenceNum;
    String model;
    Driver driver;

}
