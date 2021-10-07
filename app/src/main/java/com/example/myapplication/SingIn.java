package com.example.myapplication;


import static android.Manifest.permission.CAMERA;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SingIn extends AppCompatActivity{
    static final int REQUEST = 1;   // lo seteo siempre que uso el activityForResult
    public  static final String MY_INTENT_ACTIVITY_VALUE = "Nombre de mi variable que le paso como clave a la activity que llamo";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        setTitle(R.string.miTitulo);
        Toast.makeText(this, R.string.toas, Toast.LENGTH_SHORT).show();
        verifyCamara();



        Button botonIngresar = findViewById(R.id.buttonIngresar);
        botonIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(SingIn.this, MainActivity2.class);
                //se aplica para pasar objetos, variables, etc entre activitys
                myIntent.putExtra(MY_INTENT_ACTIVITY_VALUE, "Mi nombre es Joel"); //le paso un dato a la activity a la que voy, por medio de clave-valor
                //startActivity(myIntent);  //voy a la siguiente actitivy sin que me devuelva un resultado
                startActivityForResult(myIntent, REQUEST);
            }


        });

        Button botonRegistrar = findViewById(R.id.buttonRegister);
        botonRegistrar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                Intent myIntent = new Intent(SingIn.this, Registro1.class);
            startActivityForResult(myIntent, REQUEST);
            }
        });

        Button botonOlvidar = findViewById(R.id.buttonForgetPass);
        botonOlvidar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                Intent myIntent = new Intent(SingIn.this, ForgetPassword.class);
                startActivityForResult(myIntent, REQUEST);
            }
        });
    }
    // este metodo recien se ejecuta cuando se retorna a esta activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //primero tengo que ver si el REQUEST que recibo es igual al que mande
        if (requestCode == REQUEST) {
            //verifico que el usuario no haya cancelado ni se haya producido un error en la otra pantalla cuando el usuario la estaba usando
            if (resultCode == RESULT_OK) {
                data.getStringExtra("Resultado");//obtengo el resultado
                data.getStringExtra("Resultado2");//obtengo el resultado2
                Toast.makeText(this, data.getDataString(), Toast.LENGTH_SHORT).show();

                // el argumento data contiene información del contacto seleccionado

            }
        }
    }

    private boolean verifyCamara() {
        if(checkSelfPermission(CAMERA) == PackageManager.PERMISSION_GRANTED){
            return true;
        }
        if (shouldShowRequestPermissionRationale(CAMERA)) {
            cargarDialogoRecomendacion();
        } else {
            requestPermissions(new String[]{CAMERA}, 100);
        }
        return false;
    }

    private void cargarDialogoRecomendacion() {
        AlertDialog.Builder dialogo = new AlertDialog.Builder(SingIn.this);
        dialogo.setTitle("Permisos desactivados");
        dialogo.setMessage("Debe aceptar los permisos para el correcto funcionamiento de la app");
        dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                requestPermissions(new String[]{CAMERA}, 100);
            }
        });
        dialogo.show();
    }
/*
    @Override
    protected  void onPause() {
        super.onPause();
        Toast.makeText(this, R.string.toas, Toast.LENGTH_SHORT).show();
    }*/


}