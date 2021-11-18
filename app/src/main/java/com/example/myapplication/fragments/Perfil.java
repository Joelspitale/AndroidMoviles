package com.example.myapplication.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.myapplication.R;
import com.google.android.material.textfield.TextInputLayout;

public class Perfil extends Fragment {

    public Perfil() {
        // Required empty public constructor
    }
    public static Perfil newInstance(String param1, String param2) {
        Perfil fragment = new Perfil();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);
        Button btnModificar = view.findViewById(R.id.btnModificar);
        TextInputLayout modificarMail = view.findViewById(R.id.mailModificar);
        TextInputLayout modificarNombre = view.findViewById(R.id.nombreModificar);
        TextInputLayout modificarPass = view.findViewById(R.id.passModificar);

        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modificarMail.setEnabled(true);
                modificarNombre.setEnabled(true);
                modificarPass.setEnabled(true);
                btnModificar.setText(R.string.Confirmar);
            }
        });
        // Inflate the layout for this fragment
        return view;
    }
}