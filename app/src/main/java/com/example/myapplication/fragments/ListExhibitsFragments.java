package com.example.myapplication.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.myapplication.CameraActivity;
import com.example.myapplication.Configuration;
import com.example.myapplication.ListFavorites;
import com.example.myapplication.R;
import com.example.myapplication.fragments.interfaceFragments.IComunicationsFragment;
import com.example.myapplication.fragments.interfaceFragments.OnFragmentInteractionListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import recyclerView.Exhibits;
import recyclerView.ListAdapter;

public class ListExhibitsFragments extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    List<Exhibits> exhibitsList;
    RecyclerView recyclerExhibits;

    Activity activity;
    IComunicationsFragment interfaceComunicaFragments;

    public ListExhibitsFragments() {
        // Required empty public constructor
    }


    public static ListExhibitsFragments newInstance(String param1, String param2) {
        ListExhibitsFragments fragment = new ListExhibitsFragments();
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
        //inflo el vista
        View view = inflater.inflate(R.layout.fragment_list_exhibits_fragments, container, false);
        exhibitsList = new ArrayList<>();
        //cargo los items en mi lista
        loadExhibitsList();

        //direcciono mi recycler view
        recyclerExhibits = view.findViewById(R.id.recyclerId);
        recyclerExhibits.setLayoutManager(new LinearLayoutManager(getContext()));

        //direcciono mis botones de mi menu
        BottomNavigationView bottomNavigationView = view.findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.menu_home);
        FloatingActionButton botonQr = view.findViewById(R.id.floating_action_button_qr);
        headClickMenuIcon(bottomNavigationView,botonQr);

        ListAdapter listAdapterExhibits = new ListAdapter((exhibitsList));
        //le coloco el adapter que ya hemos hecho
        recyclerExhibits.setAdapter(listAdapterExhibits);

        listAdapterExhibits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Exhibits exhibitsSelected = exhibitsList.get(recyclerExhibits.getChildAdapterPosition(view));

                Toast.makeText(getContext(), "Selecciono :"+  exhibitsSelected.getTitle(), Toast.LENGTH_SHORT).show();
                interfaceComunicaFragments.sentExhibits(exhibitsSelected);
            }
        });

        return view;
    }



    private void loadExhibitsList(){
        exhibitsList.add(new Exhibits("Titulo1","Introduccion 1", "Groot es un superhéroe ficticio que aparece en los cómics estadounidenses publicados por Marvel Comics. Creado por Stan Lee, Larry Lieber y Jack Kirby, el personaje apareció por primera vez en Tales to Astonish # 13 (noviembre de 1960). Una criatura extraterrestre, similar a un árbol sensible, el Groot original apareció por primera vez como un invasor que pretendía capturar humanos para la experimentación. Groot es un superhéroe ficticio que aparece en los cómics estadounidenses publicados por Marvel Comics. Creado por Stan Lee, Larry Lieber y Jack Kirby, el personaje apareció por primera vez en Tales to Astonish # 13 (noviembre de 1960). Una criatura extraterrestre, similar a un árbol sensible, el Groot original apareció por primera vez como un invasor que pretendía capturar humanos para la experimentación.",R.drawable.groot,R.drawable.groot_details));
        exhibitsList.add(new Exhibits("Titulo2","Introduccion 2", "Contenido 2",R.drawable.mamut,R.drawable.mamut2));
        exhibitsList.add(new Exhibits("Titulo2","Introduccion 2", "Contenido 2",R.drawable.oso_polar,R.drawable.oso_polar2));
        exhibitsList.add(new Exhibits("Titulo2","Introduccion 2", "Contenido 2",R.drawable.tigre_dientes,R.drawable.tigre_dientes2));
        exhibitsList.add(new Exhibits("Titulo2","Introduccion 2", "Contenido 2",R.drawable.tiranosaurio,R.drawable.tiranosaurio2));
        exhibitsList.add(new Exhibits("Titulo1","Introduccion 1", "Contenido 1",R.drawable.groot,R.drawable.groot_details));
        exhibitsList.add(new Exhibits("Titulo2","Introduccion 2", "Contenido 2",R.drawable.mamut,R.drawable.mamut2));
        exhibitsList.add(new Exhibits("Titulo2","Introduccion 2", "Contenido 2",R.drawable.oso_polar,R.drawable.oso_polar2));
        exhibitsList.add(new Exhibits("Titulo2","Introduccion 2", "Contenido 2",R.drawable.tigre_dientes,R.drawable.tigre_dientes2));
        exhibitsList.add(new Exhibits("Titulo2","Introduccion 2", "Contenido 2",R.drawable.tiranosaurio,R.drawable.tiranosaurio2));


    }

    //establezo la comunicacion entre la lista y el detalle de un elemento de la lista
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof Activity){
            this.activity = (Activity) context;
            interfaceComunicaFragments = (IComunicationsFragment) this.activity;
        }
    }

    private void headClickMenuIcon(BottomNavigationView bottomNavigationView, FloatingActionButton botonQr) {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent myIntent;
                switch (item.getItemId()) {
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