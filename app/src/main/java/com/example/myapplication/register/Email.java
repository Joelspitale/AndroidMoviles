package com.example.myapplication.register;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.SingIn;
import com.example.myapplication.database.AppDatabase;
import com.example.myapplication.database.business.UserBusinnes;
import com.example.myapplication.database.dao.UserDAO;
import com.example.myapplication.database.repository.ExhibitsRepository;
import com.example.myapplication.database.repository.UserRepository;
import com.example.myapplication.excepciones.EncontradoException;
import com.example.myapplication.excepciones.NegocioException;
import com.example.myapplication.excepciones.NoEncontradoException;
import com.example.myapplication.modelo.User;
import com.example.myapplication.utils.Tools;
import com.google.android.material.textfield.TextInputLayout;

public class Email extends AppCompatActivity {
    private TextInputLayout inputEmail;
    private Tools tools;
    private UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_email);
        inputEmail = (TextInputLayout) findViewById(R.id.editCorreo);
        tools = new Tools();
        userRepository = tools.getRepositoryUser(this);

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
                String email = inputEmail.getEditText().getText().toString();
                if(isEmailValidate(email) && emailNotExist(email)){
                    User user = (User) getIntent().getExtras().getSerializable("user");
                    user.setEmail(email);
                    Intent myIntent = new Intent(Email.this, Password.class);
                    myIntent.putExtra("user", user);
                    startActivity(myIntent);
                }
            }
        });
    }

    private boolean isEmailValidate(String correo) {
        if(Patterns.EMAIL_ADDRESS.matcher(correo).matches()){
            inputEmail.setError(null);
            inputEmail.setErrorEnabled(false);
            return true;
        }
        inputEmail.setError("Mail invalido");
        return false;
    }

    private boolean emailNotExist(String email) {
        try {
            userRepository.findUserByEmail(email);
            inputEmail.setError("Ya existe un usuario con ese correo");
            return false;
        } catch (NegocioException e) {
            inputEmail.setError("Ya existe un usuario con ese correo");
            e.printStackTrace();
            return false;
        } catch (NoEncontradoException e) {
            inputEmail.setError(null);
            inputEmail.setErrorEnabled(false);
            return true;
        }
    }

}