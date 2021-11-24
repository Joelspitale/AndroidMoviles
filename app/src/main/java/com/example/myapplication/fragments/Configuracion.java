package com.example.myapplication.fragments;


import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import com.example.myapplication.R;
import com.example.myapplication.SingIn;
import com.example.myapplication.database.AppDatabase;
import com.example.myapplication.database.business.ExhibitsBusiness;
import com.example.myapplication.database.dao.ExhibitsDAO;
import com.example.myapplication.database.repository.ExhibitsRepository;
import com.example.myapplication.excepciones.NegocioException;
import com.example.myapplication.excepciones.NoEncontradoException;
import com.example.myapplication.modelo.Exhibits;
import com.example.myapplication.utils.Preference;
import java.util.List;

public class Configuracion extends Fragment {
    private Preference preference1 = new Preference();

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

        preference1.initPreference(getActivity().getBaseContext());
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
                preference1.setSessionActive(false);
                boolean b =preference1.getSessionActive();
                deleteAllFavorites();
                Intent myIntent = new Intent(getActivity(), SingIn.class);
                startActivity(myIntent);
            }
        });

        Button buttonProfile = view.findViewById(R.id.buttonEdit);
        buttonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentContainer, new Perfil()).commit();
                transaction.addToBackStack(null);
            }
        });
        return view;
    }

    private void deleteAllFavorites(){
        AppDatabase db = AppDatabase.getInstance(getActivity().getBaseContext());
        ExhibitsDAO exhibitsDAO = db.exhibitsDAO();
        ExhibitsRepository exhibitsRepository = new ExhibitsBusiness(exhibitsDAO);

        try {
            System.out.println("Eliminando todos los favoritos de usuario ");
            List<Exhibits> exhibitsList = exhibitsRepository.getAllFavorites();
            for(int i= 0 ; i<exhibitsList.size();i++) {
                exhibitsRepository.delete(exhibitsList.get(i));
            }

        } catch (NegocioException e) {
            e.printStackTrace();
        } catch (NoEncontradoException e) {
            e.printStackTrace();
        }
    }

}