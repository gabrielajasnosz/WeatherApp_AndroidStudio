package com.example.weather_app;

import android.annotation.SuppressLint;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.Calendar;
import java.util.TimeZone;

public class DisplayWeather extends AppCompatActivity {
    private MainWeather weather;

   TextView time,tempMax,tempMin,humidity,cityTemp,cityPressure,cityName,feels_like;
    private String iconUrl = "https://openweathermap.org/img/wn/";
    ImageView image;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_weather);

        Intent intent = getIntent();
        String city = intent.getStringExtra("CITY_NAME");
        weather = (MainWeather) intent.getSerializableExtra("WEATHER_CLASS");

        image=findViewById(R.id.imageView);
        cityName=findViewById(R.id.cityName);
        cityPressure=findViewById(R.id.cityPressure);
        cityTemp=findViewById(R.id.cityTemp);
        time=findViewById(R.id.time);
        tempMax=findViewById(R.id.cityTempMax);
        tempMin=findViewById(R.id.cityTempMin);
        humidity=findViewById(R.id.cityHumidity);
        feels_like=findViewById(R.id.cityFeelsLike);
        iconUrl = iconUrl + weather.getWeather()[0].getIcon() + "@2x.png";


        Picasso.with(this).load(iconUrl).error(R.drawable.error).into(image);

        TimeZone tz = TimeZone.getDefault();
        Calendar c = Calendar.getInstance(tz);
        @SuppressLint("DefaultLocale") String time3 = String.format("%02d", c.get(Calendar.HOUR_OF_DAY)) + ":" +
                String.format("%02d", c.get(Calendar.MINUTE));
        time.setText(time3);
        cityName.setText(city.toUpperCase());
        cityTemp.setText(weather.getMain().getTemp()+ " °C");
        cityPressure.setText(weather.getMain().getPressure() + " hPa");
        tempMax.setText(weather.getMain().getTemp_max()+" °C");
        tempMin.setText(weather.getMain().getTemp_min()+" °C");
        humidity.setText(weather.getMain().getHumidity()+" %");
        feels_like.setText(weather.getMain().getFeels_like()+ " °C");
    }

}