package com.example.weather_app;

import java.io.Serializable;

public class Wind implements Serializable {
    private double speed;
    private double deg;

    public double getSpeed() {
        return speed;
    }

    public double getDeg() {
        return deg;
    }
}