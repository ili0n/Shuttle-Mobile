package com.example.shuttlemobile.ride;

import androidx.annotation.NonNull;

import com.example.shuttlemobile.Entity;
import com.example.shuttlemobile.user.User;

import java.time.LocalDateTime;

public class Rejection extends Entity {
    private @NonNull User rejector;
    private @NonNull Ride ride;
    private LocalDateTime time;
    private String reason;

    public Rejection(@NonNull User rejector, @NonNull Ride ride, LocalDateTime time, String reason) {
        this.rejector = rejector;
        this.ride = ride;
        this.time = time;
        this.reason = reason;
    }

    @NonNull
    public User getRejector() {
        return rejector;
    }

    public void setRejector(@NonNull User rejector) {
        this.rejector = rejector;
    }

    @NonNull
    public Ride getRide() {
        return ride;
    }

    public void setRide(@NonNull Ride ride) {
        this.ride = ride;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
