package com.example.myapplication.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.R;
import com.example.myapplication.database.AppDatabase;
import com.example.myapplication.database.business.UserBusinnes;
import com.example.myapplication.database.dao.UserDAO;
import com.example.myapplication.database.repository.UserRepository;
import com.example.myapplication.excepciones.NegocioException;
import com.example.myapplication.excepciones.NoEncontradoException;
import com.example.myapplication.modelo.User;
import com.example.myapplication.utils.Preference;

public class Perfil extends Fragment {
    private Preference preference = new Preference();

    public Perfil() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);
        preference.initPreference(getActivity().getBaseContext());
        System.out.println("el mail del usuario logueado actualmente es :" + preference.getEmailSharedPreferences());
        User user = userExistInBd(preference.getEmailSharedPreferences());

        Button btnModificar = view.findViewById(R.id.btnModificar);
        Button btnAtras = view.findViewById(R.id.buttonReturnPerfil);
        EditText modificarMail = view.findViewById(R.id.mailModificar);
        EditText modificarNombre = view.findViewById(R.id.nombreModificar);
        EditText modificarApellido = view.findViewById(R.id.apellidoModificar);
        EditText modificarPass = view.findViewById(R.id.passModificar);

        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        //se los coloca como fondo
        modificarMail.setHint(user.getEmail());
        modificarNombre.setHint(user.getName());
        modificarApellido.setHint(user.getLastname());
        String pass = "";
        for (int i = 0; i < user.getPassword().length(); i++){
            pass = pass + "*";
        }
        modificarPass.setHint(pass);

        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btnModificar.getText().equals("MODIFICAR")) {
                    modificarMail.setEnabled(true);
                    modificarNombre.setEnabled(true);
                    modificarApellido.setEnabled(true);
                    modificarPass.setEnabled(true);
                    btnModificar.setText(R.string.Confirmar);
                } else {
                    User userAux = new User();
                    //id
                    userAux.setId(user.getId());
                    //mail
                    if(modificarMail.getText().length() > 0)
                        userAux.setEmail(String.valueOf(modificarMail.getText()));
                    else
                        userAux.setEmail(String.valueOf(modificarMail.getHint()));
                    //password
                    if(modificarPass.getText().length() > 0)
                        userAux.setPassword(String.valueOf(modificarPass.getText()));
                    else
                        userAux.setPassword(String.valueOf(modificarPass.getHint()));
                    //name
                    if(modificarNombre.getText().length() > 0)
                        userAux.setName(String.valueOf(modificarNombre.getText()));
                    else
                        userAux.setName(String.valueOf(modificarNombre.getHint()));
                    //lastname
                    if(modificarApellido.getText().length() > 0)
                        userAux.setLastname(String.valueOf(modificarApellido.getText()));
                    else
                        userAux.setLastname(String.valueOf(modificarApellido.getHint()));

                    System.out.println("antes de la actualizacion"+userAux.toString());
                    actualizacion(userAux);
                    preference.savePreference(userAux.getEmail());

                    modificarMail.setEnabled(false);
                    modificarNombre.setEnabled(false);
                    modificarApellido.setEnabled(false);
                    modificarPass.setEnabled(false);
                    btnModificar.setText(R.string.btnModificar);
                }
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

    public void actualizacion(User user) {
        AppDatabase db = AppDatabase.getInstance(getActivity().getBaseContext());
        UserDAO userDAO = db.userDAO();
        UserRepository userRepository = new UserBusinnes(userDAO);
        try {
            userRepository.update(user.getName(),user.getLastname(),user.getEmail(),user.getPassword(),user.getId());
            //userRepository.update(user);
        } catch (NegocioException e) {
            e.printStackTrace();
        } catch (NoEncontradoException e) {
            e.printStackTrace();
        }
    }
}