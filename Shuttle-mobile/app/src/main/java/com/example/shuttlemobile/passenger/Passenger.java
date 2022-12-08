package com.example.shuttlemobile.passenger;

import com.example.shuttlemobile.user.User;

public class Passenger extends User {
    public Passenger(String name, String lastName, String location, String phone, String email, String password, String pfp, boolean blocked, boolean active) {
        super(name, lastName, location, phone, email, password, pfp, blocked, active);
    }

    public Passenger() {

    }
}
