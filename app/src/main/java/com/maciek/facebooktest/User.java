package com.maciek.facebooktest;

/**
 * Created by Geezy on 21.09.2017.
 */

public class User {

    private String name;
    private boolean hasSpot;
    private Spot spot;

    public User(String name, boolean hasSpot, Spot spot) {
        this.name = name;
        this.hasSpot = hasSpot;
        this.spot = spot;
    }

    public String getName() {
        return name;
    }

    public User() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHasSpot(boolean hasSpot) {
        this.hasSpot = hasSpot;
    }

    public void setSpot(Spot spot) {
        this.spot = spot;
    }

    public boolean isHasSpot() {
        return hasSpot;
    }

    public Spot getSpot() {
        return spot;
    }
}
