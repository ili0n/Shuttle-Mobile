package com.example.shuttlemobile.message;

import androidx.annotation.Nullable;

import com.example.shuttlemobile.common.Entity;
import com.example.shuttlemobile.ride.Ride;
import com.example.shuttlemobile.user.User;

import java.time.LocalDateTime;

public class Message extends Entity {
    private User sender;
    private User recipient;
    private String message;
    private LocalDateTime date;
    @Nullable
    private Ride ride;
    private Type type;

    public enum Type {
        SUPPORT, RIDE, PANIC
    }

    public Message() {

    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getRecipient() {
        return recipient;
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Nullable
    public Ride getRide() {
        return ride;
    }

    public void setRide(@Nullable Ride ride) {
        this.ride = ride;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
