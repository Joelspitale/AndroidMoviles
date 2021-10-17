package com.example.myapplication.fragments;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import com.example.myapplication.R;
import com.example.myapplication.fragments.interfaceFragments.IComunicationsFragment;
import com.example.myapplication.fragments.interfaceFragments.OnFragmentInteractionListener;

import recyclerView.Exhibits;

public class ActivityContentFragmentListExhibits extends AppCompatActivity implements
        OnFragmentInteractionListener, IComunicationsFragment {

    ListExhibitsFragments listExhibitsFragments;
    FragmentExhibitsDetaills fragmentExhibitsDetaills;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_fragment_list_exhibits);
        listExhibitsFragments = new ListExhibitsFragments();
        //cargamos en nuestro componente el fragment que tiene el recycler view
        getSupportFragmentManager().beginTransaction().
                replace(R.id.containerFragmentsExhibits,listExhibitsFragments).commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    //paso la info de la exhibicion que se selecciono al siguiente fragments
    @Override
    public void sentExhibits(Exhibits exhibits) {
        fragmentExhibitsDetaills = new FragmentExhibitsDetaills();
        //con la clase Bundle es como se transporta objetos entre fragments
        Bundle bundleSent = new Bundle();
        //envio el objeto cuya clave es "objeto"
        bundleSent.putSerializable("objeto", exhibits);
        fragmentExhibitsDetaills.setArguments(bundleSent);

        //cargo el fragments en la activity actual
        getSupportFragmentManager().beginTransaction().
                replace(R.id.containerFragmentsExhibits,fragmentExhibitsDetaills)
                .addToBackStack(null).commit();
    }
}