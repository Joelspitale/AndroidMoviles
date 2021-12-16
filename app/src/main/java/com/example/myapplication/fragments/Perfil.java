package com.example.myapplication.fragments;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.myapplication.R;
import com.example.myapplication.database.AppDatabase;
import com.example.myapplication.database.business.UserBusinnes;
import com.example.myapplication.database.dao.UserDAO;
import com.example.myapplication.database.repository.UserRepository;
import com.example.myapplication.excepciones.NegocioException;
import com.example.myapplication.excepciones.NoEncontradoException;
import com.example.myapplication.modelo.User;
import com.example.myapplication.utils.Preference;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Perfil extends Fragment {
    private Preference preference = new Preference();
    private ImageView imageProfile;
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
        imageProfile = view.findViewById(R.id.image_profile);
        loadImageProfileUI(preference.getUriImage(getActivity().getBaseContext()));
        FloatingActionButton cambiarFoto = view.findViewById(R.id.flotingBottonEdit);


        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });


        cambiarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadImagen();
            }
        });

        //se los coloca como fondo
        modificarMail.setText(user.getEmail());
        modificarNombre.setText(user.getName());
        modificarApellido.setText(user.getLastname());

        modificarPass.setText(user.getPassword());

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
                        userAux.setEmail(String.valueOf(modificarMail.getText()));
                    //password
                        userAux.setPassword(String.valueOf(modificarPass.getText()));
                    //name
                        userAux.setName(String.valueOf(modificarNombre.getText()));
                    //lastname
                        userAux.setLastname(String.valueOf(modificarApellido.getText()));

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
        } catch (NegocioException e) {
            e.printStackTrace();
        } catch (NoEncontradoException e) {
            e.printStackTrace();
        }
    }
    private void loadImagen(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        intent.setType("image/");
        System.out.println("ingreso a buscar la imagen");
        startActivityForResult(intent.createChooser(intent, "Seleccione la imagen"),10);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        System.out.println("ingreso a buscar la imagen2");
        if(resultCode == RESULT_OK){
            System.out.println("actualiza imagen");
            Uri path = data.getData();
            loadImageProfileUI(path);              //CARGO FOTO A LA ITEM VIEW DE LA UI
            preference.setUriImage(path);           //lo guardo en shared preference
        }
    }

    private void loadImageProfileUI(Uri uri) {
        imageProfile.setImageURI(uri);
    }
}