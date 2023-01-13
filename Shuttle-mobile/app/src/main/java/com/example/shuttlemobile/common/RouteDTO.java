package com.example.shuttlemobile.common;

import android.location.Location;

import java.io.Serializable;

public class RouteDTO implements Serializable {
    private LocationDTO departure;
    private LocationDTO destination;

    public void setDeparture(LocationDTO departure) {
        this.departure = departure;
    }

    public void setDestination(LocationDTO destination) {
        this.destination = destination;
    }

    public LocationDTO getDeparture() {
        return departure;
    }

    public LocationDTO getDestination() {
        return destination;
    }

    public RouteDTO(LocationDTO departure, LocationDTO destination) {
        this.departure = departure;
        this.destination = destination;
    }
}
