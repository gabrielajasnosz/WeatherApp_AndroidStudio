package com.example.weather_app;

import java.io.Serializable;
import java.util.TimeZone;

public class MainWeather implements Serializable {
    private Coord coord;
    private weather[] weather;
    private String base;
    private Main main;
    private int visibility;
    private Wind wind;
    private Clouds clouds;
    private Long dt;
    private Sys sys;
    private int id;
    private String name;
    private int cod;


    public Coord getCoord() {
        return coord;
    }

    public weather[] getWeather() {
        return weather;
    }

    public String getBase() {
        return base;
    }

    public Main getMain() {
        return main;
    }

    public int getVisibility() {
        return visibility;
    }

    public Wind getWind() {
        return wind;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public Long getDt() {
        return dt;
    }

    public Sys getSys() {
        return sys;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCod() {
        return cod;
    }
}