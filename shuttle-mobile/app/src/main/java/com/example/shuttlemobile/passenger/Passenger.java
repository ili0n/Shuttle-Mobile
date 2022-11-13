package com.example.shuttlemobile.passenger;

import com.example.shuttlemobile.user.User;

public class Passenger extends User {
    public Passenger() {
        super();
    }

    public Passenger(int id, String name, String surname, String phone, String email, String password, boolean blocked, String address) {
        super(id, name, surname, phone, email, password, blocked, address);
    }

}
