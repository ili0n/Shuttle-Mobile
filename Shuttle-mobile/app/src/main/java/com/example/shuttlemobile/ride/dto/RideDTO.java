package com.example.shuttlemobile.ride.dto;

import com.example.shuttlemobile.ride.Ride;

import java.util.List;

public class RideDTO {
    private Long id;
    private List<RouteDTO> locations;
    private String startTime;
    private String endTime;
    private Double totalCost;
    private RideDriverDTO driver;
    private List<RidePassengerDTO> passengers;
    private Integer estimatedTimeInMinutes;
    private Boolean babyTransport;
    private Boolean petTransport;
    private String vehicleType;
    private CancellationDTO rejection;
    private Ride.Status status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<RouteDTO> getLocations() {
        return locations;
    }

    public void setLocations(List<RouteDTO> locations) {
        this.locations = locations;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public RideDriverDTO getDriver() {
        return driver;
    }

    public void setDriver(RideDriverDTO driver) {
        this.driver = driver;
    }

    public List<RidePassengerDTO> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<RidePassengerDTO> passengers) {
        this.passengers = passengers;
    }

    public Integer getEstimatedTimeInMinutes() {
        return estimatedTimeInMinutes;
    }

    public void setEstimatedTimeInMinutes(Integer estimatedTimeInMinutes) {
        this.estimatedTimeInMinutes = estimatedTimeInMinutes;
    }

    public Boolean getBabyTransport() {
        return babyTransport;
    }

    public void setBabyTransport(Boolean babyTransport) {
        this.babyTransport = babyTransport;
    }

    public Boolean getPetTransport() {
        return petTransport;
    }

    public void setPetTransport(Boolean petTransport) {
        this.petTransport = petTransport;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public CancellationDTO getRejection() {
        return rejection;
    }

    public void setRejection(CancellationDTO rejection) {
        this.rejection = rejection;
    }

    public Ride.Status getStatus() {
        return status;
    }

    public void setStatus(Ride.Status status) {
        this.status = status;
    }
}

