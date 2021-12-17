package com.example.myapplication.fragments;

import static android.app.Activity.RESULT_OK;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.myapplication.R;
import com.example.myapplication.database.repository.UserRepository;
import com.example.myapplication.excepciones.NegocioException;
import com.example.myapplication.excepciones.NoEncontradoException;
import com.example.myapplication.modelo.User;
import com.example.myapplication.utils.Preference;
import com.example.myapplication.utils.Tools;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Perfil extends Fragment {
    private Preference preference = new Preference();
    private ImageView imageProfile;
    private Tools tools;
    private UserRepository userRepository;
    private EditText modificarMail;
    private EditText modificarNombre;
    private EditText modificarApellido ;
    private EditText modificarPass;
    private FloatingActionButton cambiarFoto;
    private User user;

    public Perfil() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);
        preference.initPreference(getActivity().getBaseContext());
        Log.i("Perfil.class","el mail del usuario logueado actualmente es :" + preference.getEmailSharedPreferences());

        //direccionamientos UI
        Button btnModificar = view.findViewById(R.id.btnModificar);
        Button btnAtras = view.findViewById(R.id.buttonReturnPerfil);
        modificarMail = view.findViewById(R.id.mailModificar);
        modificarNombre = view.findViewById(R.id.nombreModificar);
        modificarApellido = view.findViewById(R.id.apellidoModificar);
        modificarPass = view.findViewById(R.id.passModificar);
        cambiarFoto = view.findViewById(R.id.flotingBottonEdit);
        //Instancias
        imageProfile = view.findViewById(R.id.image_profile);
        loadImageProfileUI(preference.getUriImage(getActivity().getBaseContext()));
        tools = new Tools();
        userRepository = tools.getRepositoryUser(getActivity());
        user = userExistInBd(preference.getEmailSharedPreferences());

        loadEdiText(user);

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

        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btnModificar.getText().equals("MODIFICAR")) {
                    changeStateEditEdiText(true);
                    btnModificar.setText(R.string.Confirmar);
                } else {
                    User userAux = new User();
                    userAux.setId(user.getId());
                    userAux.setEmail(String.valueOf(modificarMail.getText()));
                    userAux.setPassword(String.valueOf(modificarPass.getText()));
                    userAux.setName(String.valueOf(modificarNombre.getText()));
                    userAux.setLastname(String.valueOf(modificarApellido.getText()));

                    if(validateDataUser(userAux))
                        actualizacion(userAux);
                    else{
                        Toast.makeText(getContext(), "No se actualizaron los datos debido al ingreso de datos invalidos", Toast.LENGTH_SHORT).show();
                        loadEdiText(user);
                    }
                    preference.savePreference(userAux.getEmail());
                    changeStateEditEdiText(false);
                    btnModificar.setText(R.string.btnModificar);
                }
            }
        });

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        loadEdiText(user);
    }

    private User userExistInBd(String email){
        User userAux = new User();
        userAux.setEmail("No existe el usuario");
        userAux.setPassword("No existe usuario");
        try {
            userAux =  userRepository.findUserByEmail(email);
        }catch (NoEncontradoException e){
            e.printStackTrace();
            Log.i("Perfil.class","no se encontro el usuario en la base de datos");
        } catch (NegocioException e) {
            e.printStackTrace();
            Log.i("Perfil.class","Problemas con la bd");
        }
        return userAux;
    }

    public void actualizacion(User user) {
        try {
            userRepository.update(user.getName(),user.getLastname(),user.getEmail(),user.getPassword(),user.getId());
        } catch (NegocioException e) {
            e.printStackTrace();
        } catch (NoEncontradoException e) {
            e.printStackTrace();
        }
    }
    private boolean validateDataUser(User user){
        if(tools.isPasswordValidate(user.getPassword()) && tools.isNameOrLastNameCorrect(user.getName())&&
                tools.isNameOrLastNameCorrect(user.getLastname()) && tools.isEmailValidate(user.getEmail()))
            return true;
        return false;
    }

    private void loadImagen(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent, "Seleccione la imagen"),10);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode == RESULT_OK){
            Uri path = data.getData();
            loadImageProfileUI(path);
            preference.setUriImage(path);
        }
    }

    private void loadImageProfileUI(Uri uri) {
        imageProfile.setImageURI(uri);
    }

    private void loadEdiText(User user){
        modificarMail.setText(user.getEmail());
        modificarNombre.setText(user.getName());
        modificarApellido.setText(user.getLastname());
        modificarPass.setText(user.getPassword());
    }

    private void changeStateEditEdiText(boolean estado){
        modificarMail.setEnabled(estado);
        modificarNombre.setEnabled(estado);
        modificarApellido.setEnabled(estado);
        modificarPass.setEnabled(estado);
    }
}