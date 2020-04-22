package com.example.weather_app;

import java.io.Serializable;

public class Sys implements Serializable {
    private int type;
    private int id;
    private String message;
    private String country;
    private Long sunrise;
    private Long sunset;

    public int getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public String getCountry() {
        return country;
    }

    public Long getSunrise() {
        return sunrise;
    }

    public Long getSunset() {
        return sunset;
    }
}