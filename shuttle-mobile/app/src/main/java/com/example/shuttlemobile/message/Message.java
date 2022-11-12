package com.example.shuttlemobile.message;

import androidx.annotation.Nullable;

import com.example.shuttlemobile.Entity;
import com.example.shuttlemobile.ride.Ride;
import com.example.shuttlemobile.user.User;

import java.time.LocalDateTime;

public class Message extends Entity {
    enum MessageType {
        SUPPORT, RIDE, PANIC
    }

    private String contents;
    private LocalDateTime sendTime;
    private MessageType type;
    private User sender;
    private User receiver;
    private @Nullable Ride ride;

    public Message(String contents, LocalDateTime sendTime, MessageType type, User sender, User receiver) {
        this.contents = contents;
        this.sendTime = sendTime;
        this.type = type;
        this.sender = sender;
        this.receiver = receiver;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public LocalDateTime getSendTime() {
        return sendTime;
    }

    public void setSendTime(LocalDateTime sendTime) {
        this.sendTime = sendTime;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }
}
