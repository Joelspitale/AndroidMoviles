package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ForgetPassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        Button botonVolver = findViewById(R.id.buttonReturnForgetPass);
        botonVolver.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                Intent myIntent = new Intent(ForgetPassword.this, SingIn.class);
                startActivity(myIntent);

            }
        });

        Button botonSentCorreo = findViewById(R.id.sentCorreo);
        botonVolver.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                sendEmail();
                Intent myIntent = new Intent(ForgetPassword.this, SingIn.class);
                startActivity(myIntent);

            }
        });

    }

    protected void sendEmail() {
        String[] TO = {"majoarrispi9@gmail.com"};
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);

        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Recuperar Contraseña");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "contraseña");

        try {
            startActivity(Intent.createChooser(emailIntent, "Enviar email..."));
            finish();
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this,
                    "No tienes clientes de email instalados.", Toast.LENGTH_SHORT).show();
        }
    }
}