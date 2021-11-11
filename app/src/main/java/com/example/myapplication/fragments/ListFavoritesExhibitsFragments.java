package com.example.myapplication.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.fragments.interfaceFragments.IComunicationsFragment;
import com.example.myapplication.fragments.interfaceFragments.OnFragmentInteractionListener;
import com.example.myapplication.modelo.Exhibits;
import com.example.myapplication.network.RetrofitClientInstance;
import com.example.myapplication.network.ServiceExhibits;

import java.util.List;

import com.example.myapplication.recyclerView.ListAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListFavoritesExhibitsFragments extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    List<Exhibits> exhibitsList;
    RecyclerView recyclerExhibits;

    Activity activity;
    IComunicationsFragment interfaceComunicaFragments;


    public ListFavoritesExhibitsFragments() {
        // Required empty public constructor
    }


    public static ListFavoritesExhibitsFragments newInstance(String param1, String param2) {
        ListFavoritesExhibitsFragments fragment = new ListFavoritesExhibitsFragments();
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
        View view = inflater.inflate(R.layout.fragment_list_favorites_exhibits_fragments, container, false);
        //instancio el service para hacer el get a la api
        ServiceExhibits serviceExhibits = RetrofitClientInstance.getRetrofit().create(ServiceExhibits.class);
        Call<List<Exhibits>> call = serviceExhibits.getAllExhibits(); //hago la llamada
        call.enqueue(new Callback<List<Exhibits>>() {
            @Override
            public void onResponse(Call<List<Exhibits>> call, Response<List<Exhibits>> response) {
                if(!response.isSuccessful()){
                    System.out.println("Codigo: " + response.code());
                }
                System.out.println("Se obtuvo el json correctamente");
                //cargo los items en mi lista
                loadExhibitsList(response.body(),view);
            }

            @Override
            public void onFailure(Call<List<Exhibits>> call, Throwable t) {
                System.out.println("Hubo un error inesperado" + t.getMessage());
            }
        });

        return view;
    }



    private void loadExhibitsList(List<Exhibits> exhibitsList, View view){
        //direcciono mi recycler view
        recyclerExhibits = view.findViewById(R.id.recyclerId);
        recyclerExhibits.setLayoutManager(new LinearLayoutManager(getContext()));
        ListAdapter listAdapterExhibits = new ListAdapter(getActivity().getBaseContext(),exhibitsList);
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

}