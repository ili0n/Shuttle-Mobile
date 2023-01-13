package com.example.shuttlemobile.ride;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CancellationDTO {

    private String reason;
    private String timeOfRejection;


    public CancellationDTO(String reason, LocalDateTime timeOfRejection) {
        this.reason = reason;
        this.timeOfRejection = timeOfRejection.format(DateTimeFormatter.ISO_DATE_TIME);
    }

    public CancellationDTO() {
    }
}
