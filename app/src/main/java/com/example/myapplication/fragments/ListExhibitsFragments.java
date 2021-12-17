package com.example.myapplication.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.myapplication.NoInternetConnection;
import com.example.myapplication.R;
import com.example.myapplication.fragments.interfaceFragments.IComunicationsFragment;
import com.example.myapplication.fragments.interfaceFragments.OnFragmentInteractionListener;
import com.example.myapplication.modelo.ItemMuseo;
import com.example.myapplication.network.RetrofitClientInstance;
import com.example.myapplication.network.ServiceExhibits;

import java.util.List;

import com.example.myapplication.recyclerView.ListAdapter;
import com.example.myapplication.utils.Tools;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListExhibitsFragments extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ProgressBar progressBar;
    private ScrollView scrollView;

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    List<ItemMuseo> itemMuseoList;
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
        Tools tools = new Tools();

        if (tools.verifyConnection(getActivity())) { //Realiza verificacion si esta conectado a internet
            scrollView = view.findViewById(R.id.scrollListExhibits);

            progressBar = view.findViewById(R.id.progress_bar_listExhibits);
            ServiceExhibits serviceExhibits = RetrofitClientInstance.getRetrofit().create(ServiceExhibits.class);
            System.out.println("por buscar todos los itemsMUseo");

            Call<List<ItemMuseo>> call = serviceExhibits.getAllItemsMuseo(); //hago la llamada
            call.enqueue(new Callback<List<ItemMuseo>>() {
                @Override
                public void onResponse(Call<List<ItemMuseo>> call, Response<List<ItemMuseo>> response) {
                    if (!response.isSuccessful()) {
                        System.out.println("Codigo: " + response.code());
                    }
                    System.out.println("Se obtuvo el json correctamente");
                    loadExhibitsList(response.body(), view);
                    progressBar.setVisibility(View.INVISIBLE);
                    scrollView.setVisibility(View.VISIBLE);
                }

                @Override
                public void onFailure(Call<List<ItemMuseo>> call, Throwable t) {
                    System.out.println("Hubo un error inesperado" + t.getMessage());
                }
            });
        /*
        MiTareaAsincrona tarea2 = new MiTareaAsincrona(view,scrollView,progressBar,serviceExhibits);
        tarea2.execute();*/
        } else {
            Intent myIntent = new Intent(getActivity(), NoInternetConnection.class);
            startActivity(myIntent);
        }
        return view;

    }


    private void loadExhibitsList(List<ItemMuseo> itemMuseoList, View view) {
        //direcciono mi recycler view
        recyclerExhibits = view.findViewById(R.id.recyclerId);
        recyclerExhibits.setLayoutManager(new LinearLayoutManager(getContext()));
        ListAdapter listAdapterExhibits = new ListAdapter(getActivity().getBaseContext(), itemMuseoList);
        //le coloco el adapter que ya hemos hecho
        recyclerExhibits.setAdapter(listAdapterExhibits);
        listAdapterExhibits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ItemMuseo itemMuseoSelected = itemMuseoList.get(recyclerExhibits.getChildAdapterPosition(view));

                Log.i("Prueba", "5");
                Toast.makeText(getContext(), "Selecciono :" + itemMuseoSelected.getItemTitle(), Toast.LENGTH_SHORT).show();
                interfaceComunicaFragments.sentItemMuseo(itemMuseoSelected);
            }
        });
    }

    //establezo la comunicacion entre la lista y el detalle de un elemento de la lista
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            this.activity = (Activity) context;
            interfaceComunicaFragments = (IComunicationsFragment) this.activity;
        }
    }

}

