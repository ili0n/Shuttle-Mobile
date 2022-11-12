package com.example.shuttlemobile.payment;

import com.example.shuttlemobile.Entity;
import com.example.shuttlemobile.passenger.Passenger;
import com.example.shuttlemobile.ride.Ride;

import java.time.LocalDateTime;

public class Payment extends Entity {
    public enum PaymentType {
        CARD, PAYPAL, BTC
    }

    private PaymentType type;
    private LocalDateTime time;
    private double amount;
    private Passenger passenger;
    private Ride ride;

    public Payment(PaymentType type, LocalDateTime time, double amount, Passenger passenger, Ride ride) {
        this.type = type;
        this.time = time;
        this.amount = amount;
        this.passenger = passenger;
        this.ride = ride;
    }

    public PaymentType getType() {
        return type;
    }

    public void setType(PaymentType type) {
        this.type = type;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    public Ride getRide() {
        return ride;
    }

    public void setRide(Ride ride) {
        this.ride = ride;
    }
}
