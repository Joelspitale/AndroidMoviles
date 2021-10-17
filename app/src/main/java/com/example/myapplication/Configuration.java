package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.myapplication.fragments.ActivityContentFragmentListExhibits;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Configuration extends AppCompatActivity {
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);

        initPreference();
        ImageView imageProfile = findViewById(R.id.image_profile);
        imageProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                myIntent.setType("image/");
                startActivity(myIntent.createChooser(myIntent, "Seleccione la Aplicacion"));
            }
        });
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.menu_profile);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent myIntent;
                switch (item.getItemId()) {
                    case R.id.menu_home:
                        myIntent = new Intent(Configuration.this, ActivityContentFragmentListExhibits.class);
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
    public void btnSessionClose(View v){
        setSessionActive(false);
        boolean b =getSessionActive();
        Intent myIntent = new Intent(Configuration.this, SingIn.class);
        startActivity(myIntent);
    }

    public void initPreference(){
        preferences = getSharedPreferences("SHARED_PREFEF", Context.MODE_PRIVATE);
        editor = preferences.edit();
    }
    private void setSessionActive(boolean b){
        editor.putBoolean("sessionActive", b);
        editor.commit();
    }
    private boolean getSessionActive(){
        return preferences.getBoolean("sessionActive",false);
    }
}