package com.example.shuttlemobile.passenger;

import java.util.Date;

public class Ride {
    private int id;
    private Date start;
    private Date end;
    private boolean baby;
    private boolean pets;
    private boolean splitFair;
    private double price;
    private long evaluatedTime;

    public Ride(int id, Date start, Date end, boolean baby, boolean pets, boolean splitFair, double price, long evaluatedTime) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.baby = baby;
        this.pets = pets;
        this.splitFair = splitFair;
        this.price = price;
        this.evaluatedTime = evaluatedTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public boolean isBaby() {
        return baby;
    }

    public void setBaby(boolean baby) {
        this.baby = baby;
    }

    public boolean isPets() {
        return pets;
    }

    public void setPets(boolean pets) {
        this.pets = pets;
    }

    public boolean isSplitFair() {
        return splitFair;
    }

    public void setSplitFair(boolean splitFair) {
        this.splitFair = splitFair;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getEvaluatedTime() {
        return evaluatedTime;
    }

    public void setEvaluatedTime(long evaluatedTime) {
        this.evaluatedTime = evaluatedTime;
    }
}
