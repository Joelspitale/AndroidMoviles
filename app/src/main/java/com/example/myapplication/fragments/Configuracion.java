package com.example.myapplication.fragments;


import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.SingIn;
import com.example.myapplication.database.AppDatabase;
import com.example.myapplication.database.business.ExhibitsBusiness;
import com.example.myapplication.database.business.UserBusinnes;
import com.example.myapplication.database.dao.ExhibitsDAO;
import com.example.myapplication.database.dao.UserDAO;
import com.example.myapplication.database.repository.ExhibitsRepository;
import com.example.myapplication.database.repository.UserRepository;
import com.example.myapplication.excepciones.NegocioException;
import com.example.myapplication.excepciones.NoEncontradoException;
import com.example.myapplication.modelo.ItemMuseo;
import com.example.myapplication.modelo.User;
import com.example.myapplication.utils.Preference;
import java.util.List;

public class Configuracion extends Fragment {
    private Preference preference = new Preference();
    Fragment fragmentProfile;
    Fragment fragmentSugerencia;

    public Configuracion() {
    }

    public static Configuracion newInstance(String param1, String param2) {
        Configuracion fragment = new Configuracion();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentProfile = new Perfil();
        fragmentSugerencia = new Sugerencia();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        preference.initPreference(getActivity().getBaseContext());
        User user = userExistInBd(preference.getEmailSharedPreferences());
        View view = inflater.inflate(R.layout.fragment_configuracion, container, false);
        System.out.println("el mail del usuario logueado actualmente es :" + preference.getEmailSharedPreferences());

        TextView text_name = view.findViewById(R.id.text_name);
        text_name.setHint(user.getName());


        Button buttonSugerencia = view.findViewById(R.id.buttonSugerencia);
        buttonSugerencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentContainer,fragmentSugerencia).commit();
                transaction.addToBackStack(null);
            }
        });

        Button buttonCloseSession = view.findViewById(R.id.buttonCloseSession);
        buttonCloseSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preference.setSessionActive(false);
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
                transaction.replace(R.id.fragmentContainer,fragmentProfile).commit();
                transaction.addToBackStack(null);
                System.out.println("voy a editar");
            }
        });
        return view;
    }

    private User userExistInBd(String email){
        AppDatabase db = AppDatabase.getInstance(getActivity().getBaseContext());
        UserDAO userDAO = db.userDAO();
        UserRepository userRepository = new UserBusinnes(userDAO);
        User userAux = new User();
        userAux.setEmail("No existe el usuario");
        userAux.setPassword("No existe usuario");

        try {
            userAux =  userRepository.findUserByEmail(email);
        }catch (NoEncontradoException e){
            e.printStackTrace();
            System.out.println("no se encontro el usuario en la base de datos");
        } catch (NegocioException e) {
            e.printStackTrace();
            System.out.println("Problemas con la bd");
        }
        return userAux;
    }

    private void deleteAllFavorites(){
        AppDatabase db = AppDatabase.getInstance(getActivity().getBaseContext());
        ExhibitsDAO exhibitsDAO = db.exhibitsDAO();
        ExhibitsRepository exhibitsRepository = new ExhibitsBusiness(exhibitsDAO);

        try {
            System.out.println("Eliminando todos los favoritos de usuario ");
            List<ItemMuseo> itemMuseoList = exhibitsRepository.getAllFavorites();
            for(int i = 0; i< itemMuseoList.size(); i++) {
                exhibitsRepository.delete(itemMuseoList.get(i));
            }

        } catch (NegocioException e) {
            e.printStackTrace();
        } catch (NoEncontradoException e) {
            e.printStackTrace();
        }
    }
}