package com.example.weather_app;

import android.annotation.SuppressLint;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import java.util.Calendar;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DisplayWeather extends AppCompatActivity {
    private MainWeather weather;

   TextView time,tempMax,tempMin,humidity,cityTemp,cityPressure,cityName,feels_like,info;
   SwipeRefreshLayout swipe;
    private String iconUrl = "https://openweathermap.org/img/wn/";
    ImageView image;
    String city;
    Toast error;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_weather);
        Intent intent = getIntent();
        city = intent.getStringExtra("CITY_NAME");
        weather = (MainWeather) intent.getSerializableExtra("WEATHER_CLASS");
        cityName=findViewById(R.id.cityName);
        image=findViewById(R.id.imageView);
        cityPressure=findViewById(R.id.cityPressure);
        cityTemp=findViewById(R.id.cityTemp);
        time=findViewById(R.id.time);
        tempMax=findViewById(R.id.cityTempMax);
        tempMin=findViewById(R.id.cityTempMin);
        humidity=findViewById(R.id.cityHumidity);
        feels_like=findViewById(R.id.cityFeelsLike);
        swipe = findViewById(R.id.swipeRefresh);
        info=findViewById(R.id.dataInfo);
        cityName.setText(city.toUpperCase());
        update(weather);

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(network()) {
                    refreshData();
                    swipe.setRefreshing(false);
                    error = Toast.makeText(getApplicationContext(), "Data refreshed.", Toast.LENGTH_SHORT);
                    error.setGravity(Gravity.TOP, 0, 200);
                    error.show();
                }
                else{
                    error = Toast.makeText(getApplicationContext(), "No internet connection!", Toast.LENGTH_SHORT);
                    error.setGravity(Gravity.TOP, 0, 200);
                    error.show();
                    swipe.setRefreshing(false);
                }
            }
        });
        Thread t=new Thread(){
            public void run(){
                try{
                    while(!isInterrupted()){
                        Thread.sleep(60000);
                        runOnUiThread(new Runnable(){
                            public void run(){
                                if(network()) {
                                    refreshData();
                                    error = Toast.makeText(getApplicationContext(), "Data refreshed.", Toast.LENGTH_SHORT);
                                    error.setGravity(Gravity.TOP, 0, 200);
                                    error.show();
                                }
                                else{
                                    error = Toast.makeText(getApplicationContext(), "No internet connection!", Toast.LENGTH_SHORT);
                                    error.setGravity(Gravity.TOP, 0, 200);
                                    error.show();

                                }
                            }
                        });
                    }
                } catch(InterruptedException e){
                    System.out.println("ERROR");
                }
            }
        };
        t.start();
    }


    private void refreshData() {
        Call<MainWeather> call = MainActivity.findData(city);
        call.enqueue(new Callback<MainWeather>() {
            @Override
            public void onResponse(Call<MainWeather> call, Response<MainWeather> response) {
                weather=response.body();
                update(weather);
            }
            @Override
            public void onFailure(Call<MainWeather> call, Throwable t) {
                System.out.println("error");
            }
        });
    }


    public void update(MainWeather weather){
        iconUrl = iconUrl + weather.getWeather()[0].getIcon() + "@2x.png";
        Picasso.with(this).load(iconUrl).error(R.drawable.error).into(image);
        TimeZone tz = TimeZone.getDefault();
        Calendar c = Calendar.getInstance(tz);
        @SuppressLint("DefaultLocale") String time3 = String.format("%02d", c.get(Calendar.HOUR_OF_DAY)) + ":" +
                String.format("%02d", c.get(Calendar.MINUTE));
        time.setText(time3);
        cityTemp.setText(weather.getMain().getTemp()+ " °C");
        cityPressure.setText(weather.getMain().getPressure() + " hPa");
        tempMax.setText(weather.getMain().getTemp_max()+" °C");
        tempMin.setText(weather.getMain().getTemp_min()+" °C");
        humidity.setText(weather.getMain().getHumidity()+" %");
        feels_like.setText(weather.getMain().getFeels_like()+ " °C");

    }

    public boolean network(){
        boolean wifi = false;
        boolean mobile = false;
        ConnectivityManager connManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mMobile = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (mWifi.isConnected()) {
            wifi = true;
        }
        if(mMobile.isConnected()){
            mobile = true;
        }
        return wifi||mobile;
    }

    }
