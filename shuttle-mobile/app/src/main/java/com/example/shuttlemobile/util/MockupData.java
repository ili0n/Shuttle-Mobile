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

        for (int j = 0; j < 7; j++) {
            Ride r1 = new Ride(
                    LocalDateTime.of(2022, 11, 11, 17, 00),
                    LocalDateTime.of(2022, 11, 11, 18, 00),
                    LocalTime.of(0, 50, 0),
                    400,
                    Ride.RideStatus.COMPLETED,
                    new ArrayList<Passenger>(Arrays.asList(passengers)),
                    driver1,
                    12,
                    new ArrayList<Route>(Arrays.asList(routes))
            );

            Ride r2 = new Ride(
                    LocalDateTime.of(2022, 11, 11, 23, 32),
                    LocalDateTime.of(2022, 11, 11, 23, 59),
                    LocalTime.of(0, 25, 0),
                    300,
                    Ride.RideStatus.COMPLETED,
                    new ArrayList<Passenger>(Arrays.asList(passengers)),
                    driver1,
                    8,
                    new ArrayList<Route>(Arrays.asList(routes))
            );

            Ride r3 = new Ride(
                    LocalDateTime.of(2022, 11, 12, 7, 15),
                    LocalDateTime.of(2022, 11, 12, 9, 0),
                    LocalTime.of(2, 10, 0),
                    1100,
                    Ride.RideStatus.COMPLETED,
                    new ArrayList<Passenger>(Arrays.asList(passengers)),
                    driver1,
                    32,
                    new ArrayList<Route>(Arrays.asList(routes))
            );

            rides.add(r1);
            rides.add(r2);
            rides.add(r3);

        }

        return rides;
    }
}
