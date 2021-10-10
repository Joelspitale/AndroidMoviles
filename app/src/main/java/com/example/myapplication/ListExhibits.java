package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import recyclerView.Exhibits;
import recyclerView.ListAdapter;

public class ListExhibits extends AppCompatActivity {

    private List<Exhibits> exhibitsList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_exhibits);
        exhibitsList = new ArrayList<Exhibits>(0);
        loadExhibits();

        RecyclerView recyclerView = findViewById(R.id.recycler_view_exhibits_list);
        recyclerView.setLayoutManager( new LinearLayoutManager(this));
        //le coloco el adapter que ya hemos hecho
        recyclerView.setAdapter(new ListAdapter((exhibitsList)));
    }
    private void loadExhibits(){
        exhibitsList.add(new Exhibits("Titulo1","Introduccion 1", "Contenido 1"));
        exhibitsList.add(new Exhibits("Titulo2","Introduccion 2", "Contenido 2"));
        exhibitsList.add(new Exhibits("Titulo3","Introduccion 3", "Contenido 3"));
        exhibitsList.add(new Exhibits("Titulo4","Introduccion 4", "Contenido 4"));
        exhibitsList.add(new Exhibits("Titulo5","Introduccion 5", "Contenido 5"));
    }

}