package com.example.weather_app;

import java.io.Serializable;

public class Coord implements Serializable {
    private double lon;
    private double lat;

    public double getLon() {
        return lon;
    }

    public double getLat() {
        return lat;
    }
}
