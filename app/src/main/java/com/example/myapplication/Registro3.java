package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Registro3 extends AppCompatActivity {

    static final int REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_3);

        Button botonVolverContra = findViewById(R.id.buttonVolverContra);
        botonVolverContra.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                Intent myIntent = new Intent(Registro3.this, Registro2.class);
                startActivityForResult(myIntent, REQUEST);
            }
        });

        Button botonSiguienteContra = findViewById(R.id.buttonSiguienteContra);
        botonSiguienteContra.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                Intent myIntent = new Intent(Registro3.this, Registro4.class);
                startActivityForResult(myIntent, REQUEST);
            }
        });
    }


}