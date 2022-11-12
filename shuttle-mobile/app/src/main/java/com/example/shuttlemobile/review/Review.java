package com.example.shuttlemobile.review;

import com.example.shuttlemobile.Entity;
import com.example.shuttlemobile.passenger.Passenger;
import com.example.shuttlemobile.ride.Ride;

public class Review extends Entity {
    private int grade;
    private String comment;
    private Ride ride;
    private Passenger passenger;

    public Review(int grade, String comment, Ride ride, Passenger passenger) {
        this.grade = grade;
        this.comment = comment;
        this.ride = ride;
        this.passenger = passenger;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Ride getRide() {
        return ride;
    }

    public void setRide(Ride ride) {
        this.ride = ride;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }
}