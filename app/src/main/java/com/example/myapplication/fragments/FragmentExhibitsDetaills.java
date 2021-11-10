package com.example.myapplication.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.Principal;
import com.example.myapplication.R;
import com.example.myapplication.fragments.interfaceFragments.OnFragmentInteractionListener;

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

        Button bottomVolverExhibits = view.findViewById(R.id.buttonReturnAtractionDetaills);
        bottomVolverExhibits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentContainer, new ListExhibitsFragments()).commit();
                transaction.addToBackStack(null);
            }
        });

        CheckBox bottomFavorites = view.findViewById(R.id.icon);
        bottomFavorites.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

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
}