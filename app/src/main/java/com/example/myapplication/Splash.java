package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class Splash extends AppCompatActivity {

    static final String EMAIL = "admin@admin.com";
    static final String PASSWORD = "administrador";
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private boolean isRemember =  false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initPreference();
        savePreference();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                isRemember = getSessionActive();
                nextActivity();
            }
        },2000);
    }

    public void initPreference(){
        preferences = getSharedPreferences("SHARED_PREFEF", Context.MODE_PRIVATE);
        editor = preferences.edit();
    }
    private boolean getSessionActive(){
        return preferences.getBoolean("sessionActive",false);
    }

    //guardo las credenciales que voy a usar
    public void savePreference(){
        editor.putString("email", EMAIL);
        editor.putString("password", PASSWORD);
        editor.commit();
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