package com.example.shuttlemobile.user;

import com.example.shuttlemobile.common.Entity;

import java.io.Serializable;

public class User extends Entity {
    private String name;
    private String lastName;
    private String location;
    private String phone;
    private String email;
    private String password;
    private String pfp;
    private boolean blocked;
    private boolean active;

    public User(String name, String lastName, String location, String phone, String email, String password, String pfp, boolean blocked, boolean active) {
        this.name = name;
        this.lastName = lastName;
        this.location = location;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.pfp = pfp;
        this.blocked = blocked;
        this.active = active;
    }

    public User() {
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPfp() {
        return pfp;
    }

    public void setPfp(String pfp) {
        this.pfp = pfp;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
