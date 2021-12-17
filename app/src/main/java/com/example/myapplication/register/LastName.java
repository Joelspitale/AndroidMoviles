package com.example.myapplication.register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.modelo.User;
import com.example.myapplication.utils.Tools;
import com.google.android.material.textfield.TextInputLayout;

public class LastName extends AppCompatActivity {
    private TextInputLayout inputLastName;
    private Tools tools;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_lastname);
        inputLastName = (TextInputLayout) findViewById(R.id.inputLastName);
        tools = new Tools();

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
                User user = (User) getIntent().getExtras().getSerializable("user");
                String lastName = inputLastName.getEditText().getText().toString();
                if(validateLastName(lastName)) {
                    user.setLastname(lastName);
                    Intent myIntent = new Intent(LastName.this, Email.class);
                    myIntent.putExtra("user", user);
                    startActivity(myIntent);
                }
            }
        });
    }

    private boolean validateLastName(String lastName){
        if(!tools.isNameOrLastNameCorrect(lastName)){
            inputLastName.setError("Apellido invalido");
            return false;
        }
        inputLastName.setError(null);
        inputLastName.setErrorEnabled(false);
        return true;
    }
}