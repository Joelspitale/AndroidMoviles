package com.example.myapplication.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.example.myapplication.R;
import com.example.myapplication.database.repository.UserRepository;
import com.example.myapplication.excepciones.NegocioException;
import com.example.myapplication.excepciones.NoEncontradoException;
import com.example.myapplication.modelo.User;
import com.example.myapplication.utils.Preference;
import com.example.myapplication.utils.SendMailAsynTask;
import com.example.myapplication.utils.Tools;
import com.google.android.material.textfield.TextInputLayout;

public class Sugerencia extends Fragment {
    private Preference preference = new Preference();
    private Tools tools;

    public Sugerencia() {
        // Required empty public constructor
    }

    public static Sugerencia newInstance() {
        Sugerencia fragment = new Sugerencia();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sugerencia, container, false);
        tools = new Tools();
        preference.initPreference(getActivity().getBaseContext());
        User user = userExistInBd(preference.getEmailSharedPreferences());

        TextInputLayout sugerencia = view.findViewById(R.id.sugerencia);

        Button buttonSendSuggest = view.findViewById(R.id.enviarSuggest);
        buttonSendSuggest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SendMailAsynTask(getActivity(),"majoarrispi9@gmail.com",
                        "Sugerencia de " + user.getName() + user.getLastname() ,
                        sugerencia.getEditText().getText().toString()).execute();
                new SendMailAsynTask(getActivity(),user.getEmail(), "Sugerencia" ,
                        "Agradecemos su sugerencia. En la brevedad nuestro servicio se comunicara con usted")
                        .execute();
            }
        });

        Button volverSuggest = view.findViewById(R.id.buttonReturnSugerencia);
        volverSuggest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
        return view;
    }

    private User userExistInBd(String email){
        UserRepository userRepository = tools.getRepositoryUser(this.getActivity());
        User userAux = new User();
        userAux.setEmail("No existe el usuario");
        userAux.setPassword("No existe usuario");

        try {
            userAux =  userRepository.findUserByEmail(email);
        }catch (NoEncontradoException e){
            e.printStackTrace();
            Log.i("Sugerencia.class","no se encontro el usuario en la base de datos");
        } catch (NegocioException e) {
            e.printStackTrace();
            Log.i("Sugerencia.class","Problemas con la bd");
        }
        return userAux;
    }
}