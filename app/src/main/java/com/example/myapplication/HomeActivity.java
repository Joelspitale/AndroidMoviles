package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

    }

    public void btnScanner(View v){
        Intent myIntent = new Intent(HomeActivity.this, Camera.class);
        startActivity(myIntent);
    }

    public void btnContent(View v){
        Intent myIntent = new Intent(HomeActivity.this, ListExhibits.class);
        startActivity(myIntent);
    }



}