package com.example.myapplication;


import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.INTERNET;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication.database.AppDatabase;
import com.example.myapplication.database.business.UserBusinnes;
import com.example.myapplication.database.dao.UserDAO;
import com.example.myapplication.database.repository.UserRepository;
import com.example.myapplication.excepciones.NegocioException;
import com.example.myapplication.excepciones.NoEncontradoException;
import com.example.myapplication.modelo.User;
import com.example.myapplication.register.Name;
import com.google.android.material.textfield.TextInputLayout;

public class SingIn extends AppCompatActivity {
    static final int REQUEST = 1;   // lo seteo siempre que uso el activityForResult
    public static final String MY_INTENT_ACTIVITY_VALUE = "Nombre de mi variable que le paso como clave a la activity que llamo";
    private TextInputLayout inputEmail;
    private TextInputLayout inputPassword;
    static final private int MIN_SIZE_PASSWORD = 8;
    static final private int MAX_SIZE_PASSWORD = 20;
    static final String EMAIL = "admin@admin.com";
    static final String PASSWORD = "administrador";

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        setTitle(R.string.miTitulo);
        initPreference();
        String[] permisos= {READ_EXTERNAL_STORAGE, CAMERA, INTERNET};
        verifyPermision(permisos);


        inputEmail = (TextInputLayout) findViewById(R.id.inputTextEmail);
        inputPassword = (TextInputLayout) findViewById(R.id.inputTextPassword);

        Button botonIngresar = findViewById(R.id.buttonIngresar);
        botonIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validarDatos()) {
                    nextActivity();
                    setSessionActive(true);
                }
                else
                    Toast.makeText(SingIn.this, "Error al iniciar Sesion revise sus credenciales", Toast.LENGTH_SHORT).show();
            }
        });

        Button botonRegistrar = findViewById(R.id.buttonRegister);
        botonRegistrar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(SingIn.this, Name.class);
                startActivityForResult(myIntent, REQUEST);
            }
        });

        Button botonOlvidar = findViewById(R.id.buttonForgetPass);
        botonOlvidar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(SingIn.this, ForgetPassword.class);
                startActivityForResult(myIntent, REQUEST);
            }
        });


    }
    private void initPreference(){
        preferences = getSharedPreferences("SHARED_PREFEF", Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    private boolean validarDatos() {
        String email = inputEmail.getEditText().getText().toString();
        String password = inputPassword.getEditText().getText().toString();
        if (isEmailValidate(email) && isPasswordValidate(password) && isConcidentCredentials(email,password)) {
            return true;
        }
        return false;
    }

    private boolean isConcidentCredentials(String email, String password) {
        if(email.equals(EMAIL)){
            String emailAux =  preferences.getString("email","No existe ningun email registrado");
            String passwordAux =  preferences.getString("password","No existe ninguna password registrado");
            if(email.equals(emailAux) && password.equals(passwordAux))
                return true;
            return false;
        }else{
            User userAux = userExistInBd(email);
            if(userAux.getEmail().equals(email) && password.equals(userAux.getPassword()))
                return true;
        }
        return false;
    }

    private User userExistInBd(String email){
        AppDatabase db = AppDatabase.getInstance(getBaseContext());
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

    private boolean isPasswordValidate(String password) {
        if (password.length() >= MIN_SIZE_PASSWORD && password.length() <= MAX_SIZE_PASSWORD)
            return true;
        return false;
    }

    private boolean isEmailValidate(String correo) {
        return Patterns.EMAIL_ADDRESS.matcher(correo).matches();
    }

    private void nextActivity() {
        Intent myIntent = new Intent(SingIn.this, Principal.class);
        startActivity(myIntent);
    }

    private boolean verifyPermision(String[] permision) {
        if (checkSelfPermission(permision[0]) == PackageManager.PERMISSION_GRANTED &&
                checkSelfPermission(permision[1]) == PackageManager.PERMISSION_GRANTED &&
                checkSelfPermission(permision[2]) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        else {
            requestPermissions(permision, 100);
        }
        return false;
    }

    private void setSessionActive(boolean b){
        editor.putBoolean("sessionActive", b);
        editor.commit();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


}