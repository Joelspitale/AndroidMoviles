package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class Atraction extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atraction);

        ImageView imageView = (ImageView)findViewById(R.id.images_atraction);

        Glide.with(Atraction.this)
                .load("https://parquesalegres.org/wp-content/uploads/2018/07/los-mejores-parques-de-atracciones-en-londres.jpg")
                .into(imageView);

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