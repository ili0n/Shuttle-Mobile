package com.example.shuttlemobile.util;

import com.example.shuttlemobile.driver.Driver;
import com.example.shuttlemobile.passenger.Passenger;
import com.example.shuttlemobile.ride.Ride;
import com.example.shuttlemobile.route.Location;
import com.example.shuttlemobile.route.Route;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MockupData {
    public static List<Ride> getRides() {
        List<Ride> rides = new ArrayList<Ride>();

        Passenger passenger1 = new Passenger();
        Driver driver1 = new Driver();
        Route route1 = new Route(
                12,
                LocalDateTime.of(2022, 11, 11, 17, 00),
                LocalDateTime.of(2022, 11, 11, 18, 00),
                LocalTime.of(0, 50, 0),
                400,
                0,
                new Location(44, 40),
                new Location(39, 39)
        );

        Passenger[] passengers = new Passenger[]{passenger1};
        Route[] routes = new Route[]{route1};

        Ride r = new Ride(
                LocalDateTime.of(2022, 11, 11, 17, 00),
                LocalDateTime.of(2022, 11, 11, 18, 00),
                LocalTime.of(0, 50, 0),
                400,
                Ride.RideStatus.COMPLETED,
                new ArrayList<>(Arrays.asList(passengers)),
                driver1,
                12,
                new ArrayList<>(Arrays.asList(routes))
        );

        rides.add(r);
        rides.add(r);
        rides.add(r);

        return rides;
    }
}
