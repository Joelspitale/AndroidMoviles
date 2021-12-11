package com.example.myapplication.register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.modelo.User;
import com.google.android.material.textfield.TextInputLayout;

public class LastName extends AppCompatActivity {

    private TextInputLayout inputLastName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_lastname);
        inputLastName = (TextInputLayout) findViewById(R.id.inputLastName);

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
                }else
                    Toast.makeText(LastName.this, "El Apellido no puede estar vacio", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validateLastName(String lastName){
        String lastNameAux = lastName.replace(" ","");
        if(lastNameAux.length()==0)
            return false;
        return true;
    }
}