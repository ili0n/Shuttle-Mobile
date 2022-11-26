package com.example.shuttlemobile.user;

import com.example.shuttlemobile.common.Entity;

import java.io.Serializable;

public class User extends Entity {
    private String name;
    private String lastName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
