package com.example.shuttlemobile.ride.dto;

import com.example.shuttlemobile.route.RouteDTO;
import com.example.shuttlemobile.user.RidePassengerDTO;

import java.util.List;

public class CreateRideDTO {
    private List<RidePassengerDTO> passengers;
    private List<RouteDTO> locations;
    private String vehicleType;
    private boolean babyTransport;
    private boolean petTransport;
    private String scheduledTime;

    public CreateRideDTO(List<RidePassengerDTO> passengers, List<RouteDTO> locations, String vehicleType, boolean babyTransport, boolean petTransport, String scheduledTime) {
        this.passengers = passengers;
        this.locations = locations;
        this.vehicleType = vehicleType;
        this.babyTransport = babyTransport;
        this.petTransport = petTransport;
        this.scheduledTime = scheduledTime;
    }

    public CreateRideDTO() {
    }

    public List<RidePassengerDTO> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<RidePassengerDTO> passengers) {
        this.passengers = passengers;
    }

    public List<RouteDTO> getLocations() {
        return locations;
    }

    public void setLocations(List<RouteDTO> locations) {
        this.locations = locations;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public boolean isBabyTransport() {
        return babyTransport;
    }

    public void setBabyTransport(boolean babyTransport) {
        this.babyTransport = babyTransport;
    }

    public boolean isPetTransport() {
        return petTransport;
    }

    public void setPetTransport(boolean petTransport) {
        this.petTransport = petTransport;
    }

    public String getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(String scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    @Override
    public String toString() {
        return "CreateRideDTO{" +
                "passengers=" + passengers +
                ", locations=" + locations +
                ", vehicleType='" + vehicleType + '\'' +
                ", babyTransport=" + babyTransport +
                ", petTransport=" + petTransport +
                ", scheduledTime='" + scheduledTime + '\'' +
                '}';
    }
}