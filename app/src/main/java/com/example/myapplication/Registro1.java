package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Registro1 extends AppCompatActivity {

    static final int REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_1);

        Button botonVolverNombre = findViewById(R.id.buttonVolverNombre);
        botonVolverNombre.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                Intent myIntent = new Intent(Registro1.this, SingIn.class);
                startActivityForResult(myIntent, REQUEST);
            }
        });

        Button botonSiguienteNombre = findViewById(R.id.buttonSiguienteNombre);
        botonSiguienteNombre.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                Intent myIntent = new Intent(Registro1.this, Registro2.class);
                startActivityForResult(myIntent, REQUEST);
            }
        });
    }


}