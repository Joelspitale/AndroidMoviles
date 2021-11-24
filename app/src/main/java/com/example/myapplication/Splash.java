package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.example.myapplication.utils.Preference;

public class Splash extends AppCompatActivity {
    private boolean isRemember =  false;
    private Preference preferences = new Preference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        preferences.initPreference(getBaseContext());
        preferences.savePreference();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                isRemember = preferences.getSessionActive();
                nextActivity();
            }
        },2000);
    }


    private void nextActivity(){
        if(isRemember){
            Intent myIntent = new Intent(Splash.this, Principal.class);
            startActivity(myIntent);
            finish();
        }else {
            Intent myIntent = new Intent(Splash.this, SingIn.class);
            startActivity(myIntent);
            finish();
        }
    }
}