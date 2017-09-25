package com.maciek.facebooktest;

/**
 * Created by Geezy on 21.09.2017.
 */

public class Spot {

    private double number;
    private boolean isAvailable;
    private int level;
    private double price;

    public Spot(double number, boolean isAvailable, int level, double price) {
        this.number = number;
        this.isAvailable = isAvailable;
        this.level = level;
        this.price = price;
    }

    public Spot() {
    }

    public double getNumber() {
        return number;
    }

    public void setNumber(double number) {
        this.number = number;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
