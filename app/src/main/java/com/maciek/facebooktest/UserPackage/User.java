package com.maciek.facebooktest.UserPackage;

/**
 * Created by Geezy on 21.09.2017.
 */

public class User {

    private String name;
    private Spot spot;

    public User(String name, Spot spot) {
        this.name = name;
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


    public void setSpot(Spot spot) {
        this.spot = spot;
    }

    public Spot getSpot() {
        return spot;
    }
}
