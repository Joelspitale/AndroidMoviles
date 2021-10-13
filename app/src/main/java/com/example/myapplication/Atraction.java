package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Atraction extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atraction);

        Button buttonVolverAtraction = findViewById(R.id.buttonVolverAtraction);
        buttonVolverAtraction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(Atraction.this, CameraActivity.class);
                startActivity(myIntent);
            }
        });
    }
}