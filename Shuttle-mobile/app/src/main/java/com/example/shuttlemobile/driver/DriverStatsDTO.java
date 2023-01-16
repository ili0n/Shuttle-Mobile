package com.example.shuttlemobile.driver;

public class DriverStatsDTO {
    int hours;
    int rejections;
    int rides;
    int money;

    public DriverStatsDTO() {
    }

    public DriverStatsDTO(int hours, int rejections, int rides, int money) {
        this.hours = hours;
        this.rejections = rejections;
        this.rides = rides;
        this.money = money;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public void setRejections(int rejections) {
        this.rejections = rejections;
    }

    public void setRides(int rides) {
        this.rides = rides;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getHours() {
        return hours;
    }

    public int getRejections() {
        return rejections;
    }

    public int getRides() {
        return rides;
    }

    public int getMoney() {
        return money;
    }
}
