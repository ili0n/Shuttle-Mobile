package com.example.shuttlemobile.ride;

import com.example.shuttlemobile.Entity;
import com.example.shuttlemobile.driver.Driver;
import com.example.shuttlemobile.passenger.Passenger;
import com.example.shuttlemobile.route.Route;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class Ride extends Entity {
    public enum RideStatus {
        ON_HOLD, ACCEPTED, REJECTED, ACTIVE, COMPLETED
    }

    private LocalDateTime start;
    private LocalDateTime finish;
    private LocalTime estimation;
    private double price;
    private RideStatus status;
    private ArrayList<Passenger> passengers;
    private Driver driver;
    private long distancePassed;
    private ArrayList<Route> routes;

    public Ride(LocalDateTime start, LocalDateTime finish, LocalTime estimation, double price, RideStatus status, ArrayList<Passenger> passengers, Driver driver, long distancePassed, ArrayList<Route> routes) {
        this.start = start;
        this.finish = finish;
        this.estimation = estimation;
        this.price = price;
        this.status = status;
        this.passengers = passengers;
        this.driver = driver;
        this.distancePassed = distancePassed;
        this.routes = routes;
    }

    public ArrayList<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(ArrayList<Route> routes) {
        this.routes = routes;
    }

    public long getDistancePassed() {
        return distancePassed;
    }

    public void setDistancePassed(long distancePassed) {
        this.distancePassed = distancePassed;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getFinish() {
        return finish;
    }

    public void setFinish(LocalDateTime finish) {
        this.finish = finish;
    }

    public LocalTime getEstimation() {
        return estimation;
    }

    public void setEstimation(LocalTime estimation) {
        this.estimation = estimation;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public RideStatus getStatus() {
        return status;
    }

    public void setStatus(RideStatus status) {
        this.status = status;
    }

    public ArrayList<Passenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(ArrayList<Passenger> passengers) {
        this.passengers = passengers;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }
}
