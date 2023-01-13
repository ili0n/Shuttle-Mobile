package com.example.shuttlemobile.ride;

import com.example.shuttlemobile.common.Entity;
import com.example.shuttlemobile.driver.Driver;
import com.example.shuttlemobile.passenger.Passenger;
import com.example.shuttlemobile.vehicle.Vehicle;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class Ride extends Entity {
    private boolean hasBaby;
    private boolean hasPets;
    private LocalTime estimate;
    private LocalDateTime start;
    private LocalDateTime end;
    private double cost;
    private Status state;
    private Passenger passenger;
    private Driver driver;

    public Ride() {
        this.passenger = new Passenger();
    }

    public enum Status {
        Pending, Accepted, Rejected, Canceled, Finished, Started
        // TODO: Started - why?! For now it's mock-added as a response.
    }

    public Driver getDriver() { return driver; }

    public void setDriver(Driver d) { this.driver = d; }

    public boolean isHasBaby() {
        return hasBaby;
    }

    public void setHasBaby(boolean hasBaby) {
        this.hasBaby = hasBaby;
    }

    public boolean isHasPets() {
        return hasPets;
    }

    public void setHasPets(boolean hasPets) {
        this.hasPets = hasPets;
    }

    public LocalTime getEstimate() {
        return estimate;
    }

    public void setEstimate(LocalTime estimate) {
        this.estimate = estimate;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public void setState(Status state) {
        this.state = state;
    }

    public Status getState() {
        return state;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }
}
