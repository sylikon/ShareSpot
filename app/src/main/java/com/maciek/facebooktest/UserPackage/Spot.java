package com.maciek.facebooktest.UserPackage;

/**
 * Created by Geezy on 21.09.2017.
 */

public class Spot {

    private String number;
    private boolean isAvailable;
    private double price;

    public Spot(String number, boolean isAvailable, double price) {
        this.number = number;
        this.isAvailable = isAvailable;
        this.price = price;
    }

    public Spot() {
    }

    public Spot(String number, boolean isAvailable) {
        this.number = number;
        this.isAvailable = isAvailable;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
