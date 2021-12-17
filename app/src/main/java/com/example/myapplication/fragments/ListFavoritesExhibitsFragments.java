package com.example.myapplication.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.database.AppDatabase;
import com.example.myapplication.database.business.ExhibitsBusiness;
import com.example.myapplication.database.dao.ExhibitsDAO;
import com.example.myapplication.database.repository.ExhibitsRepository;
import com.example.myapplication.excepciones.NegocioException;
import com.example.myapplication.fragments.interfaceFragments.IComunicationsFragment;
import com.example.myapplication.fragments.interfaceFragments.OnFragmentInteractionListener;
import com.example.myapplication.modelo.ItemMuseo;

import java.util.List;

import com.example.myapplication.recyclerView.ListAdapter;
import com.example.myapplication.utils.Tools;

public class ListFavoritesExhibitsFragments extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private Tools tools;
    RecyclerView recyclerExhibits;
    Activity activity;
    IComunicationsFragment interfaceComunicaFragments;


    public ListFavoritesExhibitsFragments() {}


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

        View view = inflater.inflate(R.layout.fragment_list_favorites_exhibits_fragments, container, false);
        LinearLayout linearLayout = view.findViewById(R.id.faltaFavs);
        tools = new Tools();
        ExhibitsRepository exhibitsRepository = tools.getRepositoryItemUseo(this.getActivity());
        try {
            if(exhibitsRepository.getAllFavorites().size() !=0)
                loadExhibitsList(exhibitsRepository.getAllFavorites(),view);
            else{
                linearLayout.setVisibility(View.VISIBLE);
            }
        } catch (NegocioException e) {
            Toast.makeText(getContext(),"Hubo un error al acceder a la base de datos, por favor intente mas tarde", Toast.LENGTH_SHORT).show();
        }
        return view;
    }



    private void loadExhibitsList(List<ItemMuseo> itemMuseoList, View view){
        recyclerExhibits = view.findViewById(R.id.recyclerId);
        recyclerExhibits.setLayoutManager(new LinearLayoutManager(getContext()));
        ListAdapter listAdapterExhibits = new ListAdapter(getActivity().getBaseContext(), itemMuseoList);
        recyclerExhibits.setAdapter(listAdapterExhibits);

        listAdapterExhibits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ItemMuseo itemMuseoSelected = itemMuseoList.get(recyclerExhibits.getChildAdapterPosition(view));
                Log.i(this.getClass().getName(), "Selecciono :"+  itemMuseoSelected.getItemTitle());
                interfaceComunicaFragments.sentItemMuseo(itemMuseoSelected);
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