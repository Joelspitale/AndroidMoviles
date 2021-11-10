package com.example.myapplication.register;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Principal;
import com.example.myapplication.R;

public class PasswordVerificed extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_passwordverificed);

        Button bottonReturn = findViewById(R.id.buttonReturnPasswordVerificed);
        bottonReturn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                Intent myIntent = new Intent(PasswordVerificed.this, Password.class);
                startActivity(myIntent);
            }
        });

        Button bottonNext = findViewById(R.id.buttonNextPasswordVerificed);
        bottonNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                Intent myIntent = new Intent(PasswordVerificed.this, Principal.class);
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