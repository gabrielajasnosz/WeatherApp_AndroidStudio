package com.example.weather_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Button b1=findViewById(R.id.checkButton);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent=new Intent(MainActivity.this,display_weather.class);
                startActivity(intent);

            }
        });
    }
}
