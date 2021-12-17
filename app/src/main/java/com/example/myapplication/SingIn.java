package com.example.myapplication;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.example.myapplication.database.repository.UserRepository;
import com.example.myapplication.excepciones.NegocioException;
import com.example.myapplication.excepciones.NoEncontradoException;
import com.example.myapplication.modelo.User;
import com.example.myapplication.register.Name;
import com.example.myapplication.utils.Preference;
import com.example.myapplication.utils.Tools;
import com.google.android.material.textfield.TextInputLayout;

public class SingIn extends AppCompatActivity {
    static final int REQUEST = 1;
    private TextInputLayout inputEmail;
    private TextInputLayout inputPassword;
    private Preference preferences = new Preference();
    private String email;
    private Tools tools;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_signin);
        preferences.initPreference(getBaseContext());
        tools = new Tools();
        tools.verifyPermision(this);

        tools.nextActivityNotConnection(this);
        inputEmail = (TextInputLayout) findViewById(R.id.inputTextEmail);
        inputPassword = (TextInputLayout) findViewById(R.id.inputTextPassword);

        Button botonIngresar = findViewById(R.id.buttonIngresar);
        botonIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validarDatos()) {
                    preferences.savePreference(email);
                    preferences.setSessionActive(true);
                    nextActivity();
                } else
                    Toast.makeText(SingIn.this, "Error al iniciar Sesion revise sus credenciales", Toast.LENGTH_SHORT).show();
            }
        });

        Button botonRegistrar = findViewById(R.id.buttonRegister);
        botonRegistrar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(SingIn.this, Name.class);
                startActivityForResult(myIntent, REQUEST);
            }
        });

        Button botonOlvidar = findViewById(R.id.buttonForgetPass);
        botonOlvidar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(SingIn.this, ForgetPassword.class);
                startActivityForResult(myIntent, REQUEST);
            }
        });
    }

    private boolean validarDatos() {
        email = inputEmail.getEditText().getText().toString();
        String password = inputPassword.getEditText().getText().toString();
        if (isConcidentCredentials(email, password))
            return true;
        return false;
    }

    private boolean isConcidentCredentials(String email, String password) {
        User userAux = userExistInBd(email);
        if (userAux.getEmail().equals(email) && password.equals(userAux.getPassword()))
            return true;
        return false;
    }

    private User userExistInBd(String email) {
        UserRepository userRepository = tools.getRepositoryUser(this);
        User userAux = new User();
        userAux.setEmail("No existe el usuario");
        userAux.setPassword("No existe usuario");

        try {
            userAux = userRepository.findUserByEmail(email);
        } catch (NoEncontradoException e) {
            e.printStackTrace();
            Log.i(this.getClass().getName(),"no se encontro el usuario en la base de datos");
        } catch (NegocioException e) {
            e.printStackTrace();
            Log.i(this.getClass().getName(),"Problemas con la bd");
        }
        return userAux;
    }

    private void nextActivity() {
        Intent myIntent = new Intent(SingIn.this, Principal.class);
        startActivity(myIntent);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


}