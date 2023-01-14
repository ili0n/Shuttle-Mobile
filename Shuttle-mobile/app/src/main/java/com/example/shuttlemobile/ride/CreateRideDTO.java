package com.example.shuttlemobile.ride;

import com.example.shuttlemobile.common.RouteDTO;
import com.example.shuttlemobile.user.RidePassengerDTO;

import java.util.List;

public class CreateRideDTO {
    private List<RidePassengerDTO> passengers;
    private List<RouteDTO> locations;
    private String vehicleType;
    private boolean babyTransport;
    private boolean petTransport;
    private String hour;
    private String minute;

    public void setPassengers(List<RidePassengerDTO> passengers) {
        this.passengers = passengers;
    }

    public void setLocations(List<RouteDTO> locations) {
        this.locations = locations;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public void setBabyTransport(boolean babyTransport) {
        this.babyTransport = babyTransport;
    }

    public void setPetTransport(boolean petTransport) {
        this.petTransport = petTransport;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }

    public List<RidePassengerDTO> getPassengers() {
        return passengers;
    }

    public List<RouteDTO> getLocations() {
        return locations;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public boolean isBabyTransport() {
        return babyTransport;
    }

    public boolean isPetTransport() {
        return petTransport;
    }

    public String getHour() {
        return hour;
    }

    public String getMinute() {
        return minute;
    }

    @Override
    public String toString() {
        return "CreateRideDTO{" +
                "passengers=" + passengers +
                ", locations=" + locations +
                ", vehicleType='" + vehicleType + '\'' +
                ", babyTransport=" + babyTransport +
                ", petTransport=" + petTransport +
                ", hour='" + hour + '\'' +
                ", minute='" + minute + '\'' +
                '}';
    }
}
