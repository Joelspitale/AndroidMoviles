package com.example.myapplication.Register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;

public class LastName extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_lastname);

        Button bottonReturn = findViewById(R.id.buttonReturnLastName);
        bottonReturn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                Intent myIntent = new Intent(LastName.this, Name.class);
                startActivity(myIntent);

            }
        });

        Button bottonNext = findViewById(R.id.buttonNextLastName);
        bottonNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                Intent myIntent = new Intent(LastName.this, Email.class);
                startActivity(myIntent);
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