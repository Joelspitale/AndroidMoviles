package com.example.myapplication.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.CameraActivity;
import com.example.myapplication.Configuration;
import com.example.myapplication.ListFavorites;
import com.example.myapplication.R;
import com.example.myapplication.fragments.interfaceFragments.OnFragmentInteractionListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.example.myapplication.modelo.Exhibits;

public class FragmentExhibitsDetaills extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private TextView txtTitleDetaills;
    private TextView txtIntroductionDetails;
    private TextView txtContentDetails;
    private ImageView imageDetails;

    public FragmentExhibitsDetaills() {
        // Required empty public constructor
    }

    public static FragmentExhibitsDetaills newInstance(String param1, String param2) {
        FragmentExhibitsDetaills fragment = new FragmentExhibitsDetaills();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_exhibits_detaills, container, false);

        //hago el direccionamiento de los layaout con variables del entorno
        txtTitleDetaills= view.findViewById(R.id.titleAtractionDetaills);
        txtIntroductionDetails = view.findViewById(R.id.introductionAtractionDetaills);
        txtContentDetails = view.findViewById(R.id.contentAtractionDetaills);
        imageDetails = view.findViewById(R.id.imageAtractionDetaills);

        //direcciono mis botones de mi menu
        BottomNavigationView bottomNavigationView = view.findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.menu_home);
        FloatingActionButton botonQr = view.findViewById(R.id.floating_action_button_qr);
        headClickMenuIcon(bottomNavigationView,botonQr);

        Button bottomVolverExhibits = view.findViewById(R.id.buttonReturnAtractionDetaills);
        bottomVolverExhibits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(getActivity(), ActivityContentFragmentListExhibits.class);
                startActivity(myIntent);
            }
        });

        //recibo y extraigo el objeto en el que hizo click en la lista
        Bundle objectExhibit = getArguments();
        Exhibits exhibits = null;

        //verifico que el objeto que me enviaron no esta vacio
        if (objectExhibit != null) {
            exhibits = (Exhibits) objectExhibit.getSerializable("objeto");
            //ahora que recibi una exhibicion con contenido empiezo a setear los componentes del fragments
            txtTitleDetaills.setText(exhibits.getTitle());
            txtIntroductionDetails.setText(exhibits.getIntroduction());
            txtContentDetails.setText(exhibits.getContent());
            //imageDetails.setImageResource(exhibits.getImagenDetails());
        }
        return view;
    }

    private void headClickMenuIcon(BottomNavigationView bottomNavigationView, FloatingActionButton botonQr) {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent myIntent;
                switch (item.getItemId()) {
                    case R.id.menu_home:
                        myIntent = new Intent(getActivity(), ActivityContentFragmentListExhibits.class);
                        startActivity(myIntent);
                        return true;
                    case R.id.menu_favorites:
                        myIntent = new Intent(getActivity(), ListFavorites.class);
                        startActivity(myIntent);
                        return true;
                    case R.id.menu_google:
                        myIntent = new Intent(Intent.ACTION_WEB_SEARCH);
                        startActivity(myIntent);
                        return true;
                    case R.id.menu_profile:
                        myIntent  = new Intent(getActivity(), Configuration.class);
                        startActivity(myIntent);
                        return true;
                    default:
                        return false;
                }
            }
        });
        botonQr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(getActivity(), CameraActivity.class);
                startActivity(myIntent);
            }
        });
    }
}