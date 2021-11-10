package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.fragments.ActivityContentFragmentListExhibits;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import com.example.myapplication.modelo.Exhibits;
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
        recyclerView.setAdapter(new ListAdapter(getBaseContext(),exhibitsList));
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent myIntent;
                switch (item.getItemId()) {
                    case R.id.menu_home:
                        myIntent = new Intent(ListFavorites.this, ActivityContentFragmentListExhibits.class);
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
    private void loadExhibits(){/*
        exhibitsList.add(new Exhibits(R.string.titleMamut,R.string.introMamut, R.string.loremIpsum,R.drawable.mamut,R.drawable.mamut2));
        exhibitsList.add(new Exhibits(R.string.titleOsoPolar,R.string.introOsoPolar, R.string.loremIpsum,R.drawable.oso_polar,R.drawable.oso_polar2));
        exhibitsList.add(new Exhibits(R.string.titleTigreDientes,R.string.titleTigreDientes, R.string.loremIpsum,R.drawable.tigre_dientes,R.drawable.tigre_dientes2));
        exhibitsList.add(new Exhibits(R.string.titleGroot,R.string.introGroot, R.string.contentGroot,R.drawable.groot,R.drawable.groot_details));
        */
    }

}