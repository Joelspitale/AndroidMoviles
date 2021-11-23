package com.example.myapplication.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.database.AppDatabase;
import com.example.myapplication.database.business.ExhibitsBusiness;
import com.example.myapplication.database.business.UserBusinnes;
import com.example.myapplication.database.dao.ExhibitsDAO;
import com.example.myapplication.database.dao.UserDAO;
import com.example.myapplication.database.repository.ExhibitsRepository;
import com.example.myapplication.database.repository.UserRepository;
import com.example.myapplication.excepciones.NegocioException;
import com.example.myapplication.excepciones.NoEncontradoException;
import com.example.myapplication.modelo.User;
import com.example.myapplication.register.PasswordVerificed;
import com.google.android.material.textfield.TextInputLayout;

public class Perfil extends Fragment {

    AppDatabase db;
    UserDAO userDAO;
    UserRepository userRepository;

    public Perfil() {
        // Required empty public constructor
    }

    public static Perfil newInstance(String param1, String param2) {
        Perfil fragment = new Perfil();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        final User[] user = {null};
        final User[] userAux = {null};

        db = AppDatabase.getInstance(getActivity().getBaseContext());
        userDAO = db.userDAO();
        userRepository = new UserBusinnes(userDAO);

        Button btnModificar = view.findViewById(R.id.btnModificar);
        EditText modificarMail = view.findViewById(R.id.mailModificar);
        EditText modificarNombre = view.findViewById(R.id.nombreModificar);
        EditText modificarApellido = view.findViewById(R.id.apellidoModificar);
        EditText modificarPass = view.findViewById(R.id.passModificar);

        try {
            user[0] = userRepository.findUserByEmail("arrietematias@gmail.com");
        } catch (NegocioException e) {
            e.printStackTrace();
        } catch (NoEncontradoException e) {
            e.printStackTrace();
        }

        modificarMail.setHint(user[0].getEmail());
        modificarNombre.setHint(user[0].getName());
        modificarApellido.setHint(user[0].getLastname());
        modificarPass.setHint(user[0].getPassword());

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
                    userAux[0] = new User();

                    if(modificarMail.getText().length() > 0) {userAux[0].setEmail(String.valueOf(modificarMail.getText()));}
                    else {userAux[0].setEmail(String.valueOf(modificarMail.getHint()));}

                    if(modificarPass.getText().length() > 0) {userAux[0].setPassword(String.valueOf(modificarPass.getText()));}
                    else {userAux[0].setPassword(String.valueOf(modificarPass.getHint()));}

                    if(modificarNombre.getText().length() > 0) {userAux[0].setName(String.valueOf(modificarNombre.getText()));}
                    else {userAux[0].setName(String.valueOf(modificarNombre.getHint()));}

                    if(modificarApellido.getText().length() > 0) {userAux[0].setLastname(String.valueOf(modificarApellido.getText()));}
                    else {userAux[0].setLastname(String.valueOf(modificarApellido.getHint()));}

                    if (userAux[0].getEmail() != user[0].getEmail()) {
                        actualizacion(userAux[0]);
                    } else {
                        if (userAux[0].getName() != user[0].getName()) {
                            actualizacion(userAux[0]);
                        } else {
                            if (userAux[0].getLastname() != user[0].getLastname()) {
                                actualizacion(userAux[0]);
                            } else {
                                if (userAux[0].getPassword() != user[0].getPassword()) {
                                    actualizacion(userAux[0]);
                                } else {
                                  Toast.makeText(getActivity().getBaseContext(), "No se actualizo", Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    }
                    Toast.makeText(getActivity().getBaseContext(), userAux[0].getEmail(), Toast.LENGTH_LONG).show();
                    Toast.makeText(getActivity().getBaseContext(), userAux[0].getPassword(), Toast.LENGTH_LONG).show();
                    Toast.makeText(getActivity().getBaseContext(), userAux[0].getName(), Toast.LENGTH_LONG).show();
                    Toast.makeText(getActivity().getBaseContext(), userAux[0].getLastname(), Toast.LENGTH_LONG).show();
                    modificarMail.setEnabled(false);
                    modificarNombre.setEnabled(false);
                    modificarApellido.setEnabled(false);
                    modificarPass.setEnabled(false);
                    btnModificar.setText(R.string.btnModificar);
                }
            }
        });


        // Inflate the layout for this fragment
        return view;
    }

    public void actualizacion(User user) {
        try {
            userRepository = new UserBusinnes(userDAO);
            userRepository.update(user);
        } catch (NegocioException e) {
            e.printStackTrace();
        } catch (NoEncontradoException e) {
            e.printStackTrace();
        }
    }
}