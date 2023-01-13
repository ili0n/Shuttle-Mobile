package com.example.shuttlemobile.ride;

import com.example.shuttlemobile.common.RouteDTO;
import com.example.shuttlemobile.user.RidePassengerDTO;

import java.util.List;

public class RideDTO {

    public RideDTO() {
    }

    private Long id;
    private List<RouteDTO> locations;
    private String startTime;
    private String endTime;
    private Double totalCost;
    private RidePassengerDTO driver;
    private List<RidePassengerDTO> passengers;
    private Integer estimatedTimeInMinutes;
    private Boolean babyTransport;
    private Boolean petTransport;
    private String vehicleType;
    private CancellationDTO rejection;
    private Ride.Status status;
}
