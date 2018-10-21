package com.example.asus.ilmuonedata;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import gr.net.maroulis.library.EasySplashScreen;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        EasySplashScreen config = new EasySplashScreen(SplashScreen.this).withFullScreen()
                .withTargetActivity(LogInActivity.class)
                .withSplashTimeOut(5000)
                .withBackgroundColor(R.color.accent_material_dark_1)
                .withLogo(R.mipmap.ic_launcher)
                .withFooterText("Copyright 2018")
                .withAfterLogoText("Jakarta");

        //Set text
        config.getFooterTextView().setTextColor(Color.WHITE);
        config.getAfterLogoTextView().setTextColor(Color.WHITE);

        //Set to view
        View view = config.create();

        //Set view to content view
        setContentView(view);
    }
}
