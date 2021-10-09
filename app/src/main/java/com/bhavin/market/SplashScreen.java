package com.bhavin.market;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.widget.Button;

import com.bhavin.market.customViews.DarkImageView;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Helper.makeActivityFullscreen(this);

        new Handler().postDelayed(this::send,4000);
    }

    private void send() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}