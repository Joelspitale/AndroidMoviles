package com.example.myapplication;

import static com.example.myapplication.utils.Constants.EMAIL;
import static com.example.myapplication.utils.Constants.PASSWORD;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
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
import com.example.myapplication.utils.Preference;

import java.security.PrivateKey;

public class Splash extends AppCompatActivity {
    private boolean isRemember =  false;
    private Preference preferences = new Preference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        preferences.initPreference(getBaseContext());
        saveUserDatabaseDefault();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                isRemember = preferences.getSessionActive();
                nextActivity();
            }
        },2000);
    }

    private void nextActivity(){
        if(isRemember){
            Intent myIntent = new Intent(Splash.this, Principal.class);
            startActivity(myIntent);
            finish();
        }else {
            Intent myIntent = new Intent(Splash.this, SingIn.class);
            startActivity(myIntent);
            finish();
        }
    }


    private void saveUserDatabaseDefault() {
        User user = new User();
        user.setEmail(EMAIL);
        user.setPassword(PASSWORD);
        AppDatabase db = AppDatabase.getInstance(getBaseContext());
        UserDAO userDAO = db.userDAO();
        UserRepository userRepository = new UserBusinnes(userDAO);
        try {
            userRepository.insert(user);
            Toast.makeText(this, "Usuario creado correctamente", Toast.LENGTH_SHORT).show();
        } catch (NegocioException e) {
            e.printStackTrace();
            Toast.makeText(this, "Hubo un error con la bd", Toast.LENGTH_SHORT).show();
        } catch (EncontradoException e) {
            e.printStackTrace();
        }
    }
}