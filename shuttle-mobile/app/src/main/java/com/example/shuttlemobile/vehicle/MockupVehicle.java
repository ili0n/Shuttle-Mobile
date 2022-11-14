package com.example.shuttlemobile.vehicle;

import com.example.shuttlemobile.driver.Driver;

public class MockupVehicle {
    public static Vehicle getVehicle() {
        return new Vehicle(false, 1, "000000", "none", "wax wings",
                new Driver(20, "Icarus", "Son of Daedalus", "060123456",
                        "sunflyer@gmail.com", "high", false, "labyrinth in Knossos"));
    }
}
