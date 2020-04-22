package com.example.weather_app;



import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface JsonPlaceHolderAPI {
    @GET("data/2.5/weather")
    Call<MainWeather> getMainWeatherClassCall(@Query("q") String q,
                                                   @Query("APPID") String APPID,
                                                   @Query("units") String units);
}