package com.example.myapplication.register;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.SingIn;

public class Name extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_name);

        Button bottonReturn = findViewById(R.id.buttonReturnName);
        bottonReturn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                Intent myIntent = new Intent(Name.this, SingIn.class);
                startActivity(myIntent);

            }
        });

        Button bottonNext = findViewById(R.id.buttonNextName);
        bottonNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                Intent myIntent = new Intent(Name.this, LastName.class);
                startActivity(myIntent);
            }
        });
    }


}