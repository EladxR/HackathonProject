package com.example.hackthonproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        int splashScreenTime=2000;
        SystemClock.sleep(splashScreenTime);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}