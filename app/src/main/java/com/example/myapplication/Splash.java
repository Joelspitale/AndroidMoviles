package com.example.myapplication;

import static com.example.myapplication.utils.Constants.ADMIN;
import static com.example.myapplication.utils.Constants.EMAIL;
import static com.example.myapplication.utils.Constants.PASSWORD;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import com.example.myapplication.database.repository.UserRepository;
import com.example.myapplication.excepciones.EncontradoException;
import com.example.myapplication.excepciones.NegocioException;
import com.example.myapplication.modelo.User;
import com.example.myapplication.utils.Preference;
import com.example.myapplication.utils.Tools;

public class Splash extends AppCompatActivity {
    private boolean isRemember = false;
    private Tools tools;
    private Preference preferences = new Preference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        tools = new Tools();
        preferences.initPreference(getBaseContext());
        saveUserDatabaseDefault();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                isRemember = preferences.getSessionActive();
                nextActivity();
            }
        }, 2000);
    }

    private void nextActivity() {

            if (isRemember) {
                Intent myIntent = new Intent(Splash.this, Principal.class);
                startActivity(myIntent);
                finish();
            } else {
                Intent myIntent = new Intent(Splash.this, SingIn.class);
                startActivity(myIntent);
                finish();
            }

    }


    private void saveUserDatabaseDefault() {
        User user = new User();
        user.setName(ADMIN);
        user.setLastname(ADMIN);
        user.setEmail(EMAIL);
        user.setPassword(PASSWORD);
        UserRepository userRepository = tools.getRepositoryUser(this);
        try {
            userRepository.insert(user);
            Log.i("Splash", "Usuario creado correctamente");
        } catch (NegocioException e) {
            e.printStackTrace();
            Log.i("Splash", "Hubo un error con la bd");
        } catch (EncontradoException e) {
            e.printStackTrace();
        }
    }
}