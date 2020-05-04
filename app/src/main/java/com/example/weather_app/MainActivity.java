package com.example.weather_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import android.view.View;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private String apiid = "749561a315b14523a8f5f1ef95e45864";
    private String units = "metric";
    Button b1;
    TextView errorMsg;
    EditText cityName;
    private MainWeather weather;
    private Intent intent;
    Toast error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b1 = findViewById(R.id.checkButton);
        errorMsg = findViewById(R.id.errorMessage);
        cityName = findViewById(R.id.enterCity);
        loadData();
    }

    public void sendText(View view) {
        if(network()) {
            final String cityNameText = cityName.getText().toString();
            intent = new Intent(this, DisplayWeather.class);
            intent.putExtra("CITY_NAME", cityNameText);

            Call<MainWeather> call = findData(cityNameText);
            call.enqueue(new Callback<MainWeather>() {
                @Override
                public void onResponse(Call<MainWeather> call, Response<MainWeather> response) {
                    if (!response.isSuccessful()) {
                        errorMsg.setText("No information.");
                        return;
                    }
                    weather = response.body();
                    intent.putExtra("WEATHER_CLASS", weather);
                    startActivity(intent);
                    saveData(cityNameText);

                }

                @Override
                public void onFailure(Call<MainWeather> call, Throwable t) {
                    System.out.println(t.getMessage());
                }
            });
        }
        else{
            error = Toast.makeText(getApplicationContext(), "No internet connection!", Toast.LENGTH_SHORT);
            error.setGravity(Gravity.TOP, 0, 200);
            error.show();
        }
    }

    public void saveData(String output){
        SharedPreferences sharedPreferences=getSharedPreferences("shared preferences",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("REMEMBERED_NAME",output);
        editor.apply();
    }
    private void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences",MODE_PRIVATE);
        String name= sharedPreferences.getString("REMEMBERED_NAME","City");
        cityName.setText(name);
    }

    public static Call<MainWeather> findData(String city){
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.openweathermap.org/").
                addConverterFactory(GsonConverterFactory.create()).build();

        JsonPlaceHolderAPI jsonPlaceholderAPI = retrofit.create(JsonPlaceHolderAPI.class);

        Call<MainWeather> call = jsonPlaceholderAPI.getMainWeatherClassCall(city+",pl", "749561a315b14523a8f5f1ef95e45864","metric" );

        return call;

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
