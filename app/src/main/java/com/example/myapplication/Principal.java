package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import com.example.myapplication.fragments.Configuracion;
import com.example.myapplication.fragments.FragmentExhibitsDetaills;
import com.example.myapplication.fragments.ListExhibitsFragments;
import com.example.myapplication.fragments.ListFavoritesExhibitsFragments;
import com.example.myapplication.fragments.interfaceFragments.IComunicationsFragment;
import com.example.myapplication.fragments.interfaceFragments.OnFragmentInteractionListener;
import com.example.myapplication.modelo.ItemMuseo;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Principal extends AppCompatActivity implements
        OnFragmentInteractionListener, IComunicationsFragment {

    FragmentTransaction transaction;
    Fragment fragmentInicio, fragmentConfig, fragmentFavorites;
    FragmentExhibitsDetaills fragmentExhibitsDetaills;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        fragmentConfig = new Configuracion();
        fragmentInicio = new ListExhibitsFragments();
        fragmentFavorites = new ListFavoritesExhibitsFragments();

        getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainer, fragmentInicio).commit();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        FloatingActionButton botonQr = findViewById(R.id.floating_action_button_qr);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent myIntent;
                transaction = getSupportFragmentManager().beginTransaction();

                switch (item.getItemId()) {
                    case R.id.menu_home:
                        transaction.replace(R.id.fragmentContainer, fragmentInicio).commit();
                        transaction.addToBackStack(null);
                        return true;
                    case R.id.menu_favorites:
                        transaction.replace(R.id.fragmentContainer, fragmentFavorites).commit();
                        transaction.addToBackStack(null);
                        return true;
                    case R.id.menu_google:
                        myIntent = new Intent(Principal.this, MapsActivity.class);
                        startActivity(myIntent);
                        return true;
                    case R.id.menu_profile:
                        transaction.replace(R.id.fragmentContainer, fragmentConfig).commit();
                        transaction.addToBackStack(null);
                        return true;
                    default:
                        return false;
                }
            }
        });
        botonQr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(Principal.this, CameraActivity.class);
                startActivity(myIntent);
            }
        });
    }

    @Override
    public void sentItemMuseo(ItemMuseo itemMuseo) {
        fragmentExhibitsDetaills = new FragmentExhibitsDetaills();
        //con la clase Bundle es como se transporta objetos entre fragments
        Bundle bundleSent = new Bundle();
        //envio el objeto cuya clave es "objeto"
        bundleSent.putSerializable("objeto", itemMuseo);
        fragmentExhibitsDetaills.setArguments(bundleSent);

        //cargo el fragments en la activity actual
        getSupportFragmentManager().beginTransaction().
                replace(R.id.fragmentContainer,fragmentExhibitsDetaills)
                .addToBackStack(null).commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}