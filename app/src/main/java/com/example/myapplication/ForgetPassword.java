package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.example.myapplication.database.AppDatabase;
import com.example.myapplication.database.business.UserBusinnes;
import com.example.myapplication.database.dao.UserDAO;
import com.example.myapplication.database.repository.UserRepository;
import com.example.myapplication.excepciones.EncontradoException;
import com.example.myapplication.excepciones.NegocioException;
import com.example.myapplication.excepciones.NoEncontradoException;
import com.example.myapplication.modelo.User;
import com.example.myapplication.register.PasswordVerificed;
import com.example.myapplication.utils.SendMailAsynTask;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Random;

public class ForgetPassword extends AppCompatActivity {
    private TextInputLayout inputEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        inputEmail= (TextInputLayout) findViewById(R.id.sentCorreoUser);

        Button botonVolver = findViewById(R.id.buttonReturnForgetPass);
        botonVolver.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                Intent myIntent = new Intent(ForgetPassword.this, SingIn.class);
                startActivity(myIntent);

            }
        });

        Button botonSentCorreo = findViewById(R.id.sentCorreoForgenPassword);
        botonSentCorreo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                String emailValidate = inputEmail.getEditText().getText().toString();
                User user = findUserByEmail(emailValidate);
                if(null != user){
                    String passwordNew = createPassword();
                    user.setPassword(passwordNew);
                    updateUser(user);
                    new SendMailAsynTask(ForgetPassword.this,emailValidate, "Recuperar Contraseña", "su contraseña es :" + user.getPassword()).execute();
                    Intent myIntent = new Intent(ForgetPassword.this, SingIn.class);
                    startActivity(myIntent);
                }


            }
        });

    }


    private User findUserByEmail(String email) {
        AppDatabase db = AppDatabase.getInstance(getBaseContext());
        UserDAO userDAO = db.userDAO();
        UserRepository userRepository = new UserBusinnes(userDAO);
        try {
            return userRepository.findUserByEmail(email);
        } catch (NegocioException e) {
            Toast.makeText(this, "Hubo un problema con la bd", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } catch (NoEncontradoException e) {
            Toast.makeText(this, "No existe el mail ingresado", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    return null;
    }

    private void updateUser(User user) {
        AppDatabase db = AppDatabase.getInstance(getBaseContext());
        UserDAO userDAO = db.userDAO();
        UserRepository userRepository = new UserBusinnes(userDAO);
        try {
            userRepository.update(user);
        } catch (NegocioException e) {
            Toast.makeText(this, "Hubo un problema con la bd", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } catch (NoEncontradoException e) {
            Toast.makeText(this, "No existe el mail ingresado", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private String createPassword(){
        String bankWord = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        String password ="";
        Random random = new Random();
        for(int i = 0; i<10;i++){
            int ramdomIndex = random.nextInt(bankWord.length())+1;
            char characterRamdom = bankWord.charAt(ramdomIndex);
            password += characterRamdom;
        }
    return password;
    }
}