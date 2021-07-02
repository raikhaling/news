package com.example.newsbulletin;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import gr.net.maroulis.library.EasySplashScreen;

public class SpashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        EasySplashScreen config = new EasySplashScreen(SpashScreenActivity.this)
                .withFullScreen()
                .withTargetActivity(MainActivity.class)
                .withSplashTimeOut(1500)
                .withHeaderText("")
                .withFooterText("News in your pocket")
                .withBeforeLogoText("")
                .withAfterLogoText("News Bulletin")
                .withLogo(R.drawable.loader);


        View easySplashScreen = config.create();
        setContentView(easySplashScreen);
    }
}
