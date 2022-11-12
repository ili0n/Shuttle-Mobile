package com.example.shuttlemobile.route;

import com.example.shuttlemobile.Entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Route extends Entity {
    private long distance;
    private LocalDateTime start;
    private LocalDateTime finish;
    private LocalTime estimation;
    private double price;
    private int order;
    private Location pointStart;
    private Location pointEnd;

    public Route(long distance, LocalDateTime start, LocalDateTime finish, LocalTime estimation, double price, int order, Location pointStart, Location pointEnd) {
        this.distance = distance;
        this.start = start;
        this.finish = finish;
        this.estimation = estimation;
        this.price = price;
        this.order = order;
        this.pointStart = pointStart;
        this.pointEnd = pointEnd;
    }

    public String getPlaceName() {
        return "Town ABC Street 123";
    }

    public long getDistance() {
        return distance;
    }

    public void setDistance(long distance) {
        this.distance = distance;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getFinish() {
        return finish;
    }

    public void setFinish(LocalDateTime finish) {
        this.finish = finish;
    }

    public LocalTime getEstimation() {
        return estimation;
    }

    public void setEstimation(LocalTime estimation) {
        this.estimation = estimation;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public Location getPointStart() {
        return pointStart;
    }

    public void setPointStart(Location pointStart) {
        this.pointStart = pointStart;
    }

    public Location getPointEnd() {
        return pointEnd;
    }

    public void setPointEnd(Location pointEnd) {
        this.pointEnd = pointEnd;
    }
}
