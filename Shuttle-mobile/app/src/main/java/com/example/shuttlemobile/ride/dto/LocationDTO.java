package com.example.shuttlemobile.ride.dto;

import androidx.annotation.NonNull;

public class LocationDTO {
    private String address;
    private Double latitude;
    private Double longitude;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @NonNull
    @Override
    public String toString() {
        return address;
    }
}
