package com.example.weather_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import android.view.View;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private final String base = "http://api.openweathermap.org/";
    private final String apiid = "749561a315b14523a8f5f1ef95e45864";
    private final String units = "metric";
    Button b1;
    TextView errorMsg;
    EditText cityName;
    private MainWeather weather;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b1 = findViewById(R.id.checkButton);
        errorMsg = findViewById(R.id.errorMessage);
        cityName = findViewById(R.id.enterCity);


}
    public void sendText(View view) throws UnsupportedEncodingException {
        String cityNameText = cityName.getText().toString();
        intent = new Intent(this, display_weather.class);
        intent.putExtra("CITY_NAME", cityNameText);

        System.out.println(cityName);

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.openweathermap.org/").
                addConverterFactory(GsonConverterFactory.create()).build();
        JsonPlaceHolderAPI jsonPlaceHolderAPI = retrofit.create(JsonPlaceHolderAPI.class);

        Call<MainWeather> call = jsonPlaceHolderAPI.getMainWeatherClassCall(cityNameText + ",pl", apiid, units);
        call.enqueue(new Callback<MainWeather>() {
            @Override
            public void onResponse(Call<MainWeather> call, Response<MainWeather> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Code: " + response.code());
                    errorMsg.setText("Brak danych dla podanego miasta.");
                    return;
                }
                weather = response.body();
                intent.putExtra("WEATHER_CLASS", weather);
                startActivity(intent);
//                    saveData(cityName);
//                    error.setText("");
            }

            @Override
            public void onFailure(Call<MainWeather> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }

}
