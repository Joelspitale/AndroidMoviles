package com.example.myapplication.register;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class Email extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_email);

        Button bottonReturn = findViewById(R.id.buttonReturnEmail);
        bottonReturn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                Intent myIntent = new Intent(Email.this, LastName.class);
                startActivity(myIntent);
            }
        });

        Button bottonNext = findViewById(R.id.buttonNextEmail);
        bottonNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                Intent myIntent = new Intent(Email.this, Password.class);
                startActivity(myIntent);
            }
        });
    }


}