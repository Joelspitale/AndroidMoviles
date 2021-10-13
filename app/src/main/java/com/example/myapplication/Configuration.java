package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Configuration extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.menu_profile);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent myIntent;
                switch (item.getItemId()) {
                    case R.id.menu_home:
                        myIntent = new Intent(Configuration.this, ListExhibits.class);
                        startActivity(myIntent);
                        return true;
                    case R.id.menu_google:
                        myIntent = new Intent(Intent.ACTION_WEB_SEARCH);
                        startActivity(myIntent);
                        return true;
                    case R.id.menu_favorites:
                        myIntent  = new Intent(Configuration.this, ListFavorites.class);
                        startActivity(myIntent);
                        return true;
                    default:
                        return false;
                }
            }
        });

        FloatingActionButton botonQr = findViewById(R.id.floating_action_button_qr);
        botonQr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(Configuration.this, CameraActivity.class);
                startActivity(myIntent);
            }
        });
    }
}