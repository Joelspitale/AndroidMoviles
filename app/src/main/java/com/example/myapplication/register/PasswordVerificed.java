package com.example.myapplication.register;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Principal;
import com.example.myapplication.R;
import com.example.myapplication.SingIn;
import com.example.myapplication.database.AppDatabase;
import com.example.myapplication.database.business.UserBusinnes;
import com.example.myapplication.database.dao.UserDAO;
import com.example.myapplication.database.repository.UserRepository;
import com.example.myapplication.excepciones.EncontradoException;
import com.example.myapplication.excepciones.NegocioException;
import com.example.myapplication.modelo.User;
import com.google.android.material.textfield.TextInputLayout;

public class PasswordVerificed extends AppCompatActivity {
    private TextInputLayout inputPasswordValidate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_passwordverificed);
        inputPasswordValidate= (TextInputLayout) findViewById(R.id.inputPasswordVerificed);

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
                String passwordValidate = inputPasswordValidate.getEditText().getText().toString();
                User user = (User) getIntent().getExtras().getSerializable("user");
                if(consistencyBetweenPassword(passwordValidate,user.getPassword() )){
                    Log.i("Verificando obtencion datos passwordVerificed","Nombre :" + user.getName()+ "apellido :" + user.getLastname()+ "email :" + user.getEmail());
                    saveUserDatabase(user);
                    Intent myIntent = new Intent(PasswordVerificed.this, SingIn.class);
                    startActivity(myIntent);
                }else
                    Toast.makeText(PasswordVerificed.this, "No coinciden las contraseñas,vuelva a intentarlo", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveUserDatabase(User user) {
        AppDatabase db = AppDatabase.getInstance(getBaseContext());
        UserDAO userDAO = db.userDAO();
        UserRepository userRepository = new UserBusinnes(userDAO);
        try {
            userRepository.insert(user);
            Toast.makeText(PasswordVerificed.this, "Usuario creado correctamente", Toast.LENGTH_SHORT).show();
        } catch (NegocioException e) {
            e.printStackTrace();
            Toast.makeText(PasswordVerificed.this, "Hubo un error con la bd", Toast.LENGTH_SHORT).show();
        } catch (EncontradoException e) {
            e.printStackTrace();
            Toast.makeText(PasswordVerificed.this, "El usuario con correo :"+ user.getEmail() +" ya existe, ingrese otro", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean consistencyBetweenPassword(String passwordValidate,String passwordInitial) {
        if(passwordValidate.equals(passwordInitial))
            return true;
        return false;
    }

}