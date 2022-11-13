package com.example.shuttlemobile.driver;

import com.example.shuttlemobile.user.User;

public class Driver extends User {
    public Driver(int id, String name, String surname, String phone, String email, String password, boolean blocked, String address) {
        super(id, name, surname, phone, email, password, blocked, address);
    }
    public Driver(){
        super();
    }
}
