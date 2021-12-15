package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.utils.VerifyConnection;

public class NoInternetConnection extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_internet_connection);

        Button button = findViewById(R.id.buttonReintentar);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VerifyConnection verifyConnection = new VerifyConnection();
                if(verifyConnection.verifyConnection(NoInternetConnection.this)){
                    finish();
                }
            }
        });
    }
}