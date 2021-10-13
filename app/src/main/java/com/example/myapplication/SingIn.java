package com.example.myapplication;


import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.INTERNET;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication.Register.Name;
import com.google.android.material.textfield.TextInputLayout;
import com.journeyapps.barcodescanner.camera.CameraParametersCallback;

public class SingIn extends AppCompatActivity {
    static final int REQUEST = 1;   // lo seteo siempre que uso el activityForResult
    public static final String MY_INTENT_ACTIVITY_VALUE = "Nombre de mi variable que le paso como clave a la activity que llamo";
    private TextInputLayout inputEmail;
    private TextInputLayout inputPassword;
    private int MIN_SIZE_PASSWORD = 8;
    private int MAX_SIZE_PASSWORD = 20;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        setTitle(R.string.miTitulo);
        verifyPermision(READ_EXTERNAL_STORAGE);
        verifyPermision(CAMERA);
        verifyPermision(INTERNET);


        inputEmail = (TextInputLayout) findViewById(R.id.inputTextEmail);
        inputPassword = (TextInputLayout) findViewById(R.id.inputTextPassword);

        Button botonIngresar = findViewById(R.id.buttonIngresar);
        botonIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validarDatos())
                    nextActivity();
                /*
                Intent myIntent = new Intent(SingIn.this, MainActivity2.class);
                //se aplica para pasar objetos, variables, etc entre activitys
                //myIntent.putExtra(MY_INTENT_ACTIVITY_VALUE, "Mi nombre es Joel"); //le paso un dato a la activity a la que voy, por medio de clave-valor
                //startActivity(myIntent);  //voy a la siguiente actitivy sin que me devuelva un resultado
                startActivityForResult(myIntent, REQUEST);*/
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

    private boolean validarDatos() {
        String email = inputEmail.getEditText().getText().toString();
        String password = inputPassword.getEditText().getText().toString();
        if (isEmailValidate(email) && isPasswordValidate(password)) {
            return true;
        }
        return false;
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
        Intent myIntent = new Intent(SingIn.this, ListExhibits.class);
        myIntent.putExtra(MY_INTENT_ACTIVITY_VALUE, "Mi nombre es Joel"); //le paso un dato a la activity a la que voy, por medio de clave-valor
        startActivity(myIntent);
    }

    private boolean verifyPermision(String permision) {
        if (checkSelfPermission(permision) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
            if (shouldShowRequestPermissionRationale(permision)) {
            cargarDialogoRecomendacion(permision);
        } else {
            requestPermissions(new String[]{permision}, 100);
        }
        return false;
    }


    private void cargarDialogoRecomendacion(String permision) {
        AlertDialog.Builder dialogo = new AlertDialog.Builder(SingIn.this);
        dialogo.setTitle("Permisos desactivados");
        dialogo.setMessage("Debe aceptar los permisos para el correcto funcionamiento de la app");
        dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                requestPermissions(new String[]{permision}, 100);
            }
        });
        dialogo.show();
    }


    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this, R.string.toas, Toast.LENGTH_SHORT).show();
    }


}