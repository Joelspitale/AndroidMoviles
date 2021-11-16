package com.example.myapplication.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.myapplication.Principal;
import com.example.myapplication.R;
import com.example.myapplication.SingIn;

public class Configuracion extends Fragment {
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public Configuracion() {
        // Required empty public constructor
    }

    public static Configuracion newInstance(String param1, String param2) {
        Configuracion fragment = new Configuracion();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        initPreference();
        View view = inflater.inflate(R.layout.fragment_configuracion, container, false);
        ImageView imageProfile = view.findViewById(R.id.image_profile);
        imageProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                myIntent.setType("image/");
                startActivity(myIntent.createChooser(myIntent, "Seleccione la Aplicacion"));
            }
        });

        Button buttonCloseSession = view.findViewById(R.id.buttonCloseSession);
        buttonCloseSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSessionActive(false);
                boolean b =getSessionActive();
                Intent myIntent = new Intent(getActivity(), SingIn.class);
                startActivity(myIntent);
            }
        });
        // Inflate the layout for this fragment
        return view;
    }


    public void initPreference(){
        preferences = getActivity().getSharedPreferences("SHARED_PREFEF", Context.MODE_PRIVATE);
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