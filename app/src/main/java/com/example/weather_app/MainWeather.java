package com.example.weather_app;

import java.io.Serializable;

public class MainWeather implements Serializable {

    private Weather[] weather;
    private Main main;
    public Weather[] getWeather() {
        return weather;
    }
    public Main getMain() {
        return main;
    }


}