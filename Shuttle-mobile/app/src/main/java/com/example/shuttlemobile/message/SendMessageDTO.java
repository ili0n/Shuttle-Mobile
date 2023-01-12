package com.example.shuttlemobile.message;

public class SendMessageDTO {
    private Long receiverId;
    private String message;
    private String type;
    private Long rideId;

    public SendMessageDTO() {
    }

    public SendMessageDTO(Long receiverId, String message, String type, Long rideId) {
        this.receiverId = receiverId;
        this.message = message;
        this.type = type;
        this.rideId = rideId;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getRideId() {
        return rideId;
    }

    public void setRideId(Long rideId) {
        this.rideId = rideId;
    }

    @Override
    public String toString() {
        return "SendMessageDTO{" +
                "receiverId=" + receiverId +
                ", message='" + message + '\'' +
                ", type='" + type + '\'' +
                ", rideId=" + rideId +
                '}';
    }
}
