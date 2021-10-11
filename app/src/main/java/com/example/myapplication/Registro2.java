package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Registro2 extends AppCompatActivity {

    static final int REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_2);

        Button botonVolverEmail = findViewById(R.id.buttonVolverEmail);
        botonVolverEmail.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                Intent myIntent = new Intent(Registro2.this, Registro1.class);
                startActivityForResult(myIntent, REQUEST);
            }
        });

        Button botonSiguienteEmail = findViewById(R.id.buttonSiguienteEmail);
        botonSiguienteEmail.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                Intent myIntent = new Intent(Registro2.this, Registro3.class);
                startActivityForResult(myIntent, REQUEST);
            }
        });
    }


}