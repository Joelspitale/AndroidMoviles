package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import recyclerView.Exhibits;
import recyclerView.ListAdapter;


public class ListFavorites extends AppCompatActivity {

    private List<Exhibits> exhibitsList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_favorites);
        exhibitsList = new ArrayList<Exhibits>(0);
        loadExhibits();

        RecyclerView recyclerView = findViewById(R.id.recycler_view_exhibits_list);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.menu_favorites);
        recyclerView.setLayoutManager( new LinearLayoutManager(this));
        //le coloco el adapter que ya hemos hecho
        recyclerView.setAdapter(new ListAdapter((exhibitsList)));
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent myIntent;
                switch (item.getItemId()) {
                    case R.id.menu_home:
                        myIntent = new Intent(ListFavorites.this, ListExhibits.class);
                        startActivity(myIntent);
                        return true;
                    case R.id.menu_google:
                        myIntent = new Intent(Intent.ACTION_WEB_SEARCH);
                        startActivity(myIntent);
                        return true;
                    case R.id.menu_profile:
                        myIntent  = new Intent(ListFavorites.this, Configuration.class);
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
                Intent myIntent = new Intent(ListFavorites.this, CameraActivity.class);
                startActivity(myIntent);
            }
        });
    }
    private void loadExhibits(){
        exhibitsList.add(new Exhibits("Titulo1","Introduccion 1", "Contenido 1"));
        exhibitsList.add(new Exhibits("Titulo3","Introduccion 3", "Contenido 3"));
        exhibitsList.add(new Exhibits("Titulo5","Introduccion 5", "Contenido 5"));
        exhibitsList.add(new Exhibits("Titulo2","Introduccion 2", "Contenido 2"));
    }

}