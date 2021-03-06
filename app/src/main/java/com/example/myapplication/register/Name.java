package com.example.myapplication.register;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myapplication.R;
import com.example.myapplication.SingIn;
import com.example.myapplication.modelo.User;
import com.example.myapplication.utils.Tools;
import com.google.android.material.textfield.TextInputLayout;

public class Name extends AppCompatActivity {
    private TextInputLayout inputName;
    private Tools tools;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_name);
        inputName = (TextInputLayout) findViewById(R.id.inputName);
        tools = new Tools();

        Button bottonReturn = findViewById(R.id.buttonReturnName);
        bottonReturn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                Intent myIntent = new Intent(Name.this, SingIn.class);
                startActivity(myIntent);

            }
        });

        Button bottonNext = findViewById(R.id.buttonNextName);
        bottonNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                String name = inputName.getEditText().getText().toString();
                if(validateName(name)){
                    User user = new User();
                    user.setName(name);
                    Intent myIntent = new Intent(Name.this, LastName.class);
                    myIntent.putExtra("user", user);
                    startActivity(myIntent);
                }
            }
        });
    }

    private boolean validateName(String name){
        if(!tools.isNameOrLastNameCorrect(name)){
            inputName.setError("Nombre invalido");
            return false;
        }
        inputName.setError(null);
        inputName.setErrorEnabled(false);
        return true;
    }


}