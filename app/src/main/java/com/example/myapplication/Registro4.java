package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Registro4 extends AppCompatActivity {

    static final int REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_register_4);

        Button botonVolverContra = findViewById(R.id.buttonVolverVerificarContra);
        botonVolverContra.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                Intent myIntent = new Intent(Registro4.this, Registro3.class);
                startActivityForResult(myIntent, REQUEST);
            }
        });

//        Button botonSiguiente = findViewById(R.id.buttonSiguiente);
//        botonVolver.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view){
//                Intent myIntent = new Intent(Registro3.this, Registro3.class);
//                startActivityForResult(myIntent, REQUEST);
//            }
//        });
    }


}