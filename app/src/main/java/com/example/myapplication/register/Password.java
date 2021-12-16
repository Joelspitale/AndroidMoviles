package com.example.myapplication.register;

import static com.example.myapplication.utils.Constants.MAX_SIZE_PASSWORD;
import static com.example.myapplication.utils.Constants.MIN_SIZE_PASSWORD;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.modelo.User;
import com.google.android.material.textfield.TextInputLayout;

public class Password extends AppCompatActivity {
    private TextInputLayout inputPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_password);
        inputPassword = (TextInputLayout) findViewById(R.id.inputPassword);

        Button bottonReturn = findViewById(R.id.buttonReturnPassword);
        bottonReturn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                Intent myIntent = new Intent(Password.this, Email.class);
                startActivity(myIntent);
            }
        });

        Button bottonNext = findViewById(R.id.buttonNextPassword);
        bottonNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                String password = inputPassword.getEditText().getText().toString();
                if(isPasswordValidate(password)){
                    User user = (User) getIntent().getExtras().getSerializable("user");
                    user.setPassword(password);
                    Intent myIntent = new Intent(Password.this, PasswordVerificed.class);
                    myIntent.putExtra("user", user);
                    startActivity(myIntent);
                }
            }
        });
    }

    private boolean isPasswordValidate(String password) {
        if (password.length() >= MIN_SIZE_PASSWORD && password.length() <= MAX_SIZE_PASSWORD){
            inputPassword.setError(null);
            inputPassword.setErrorEnabled(false);
            return true;
        }
        inputPassword.setError("Ingrese un password que tenga una longitud de entre 8 a 20 caracteres");
        return false;
    }

}