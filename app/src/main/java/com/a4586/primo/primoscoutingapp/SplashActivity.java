package com.a4586.primo.primoscoutingapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    AppPreference appPreference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        appPreference = new AppPreference(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String pass = appPreference.getPass();
                Intent homeIntent;
                if (pass.equals("strat")||pass.equals("amitlaba0")||pass.equals("microgali0")||pass.equals("ITAMAAAR")||pass.equals("trigon")) {
                    homeIntent = new Intent(SplashActivity.this, ScoutingChooseActivity.class);
                    homeIntent.putExtra("name", appPreference.getName());
                    homeIntent.putExtra("level", pass);
                }
                else {
                    homeIntent = new Intent(SplashActivity.this, MainActivity.class);

                }
                startActivity(homeIntent);
                finish();
            }
        }, 1000);
    }
}
