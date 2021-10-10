package com.example.myapplication;

import static com.example.myapplication.SingIn.MY_INTENT_ACTIVITY_VALUE;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //obtengo el valor que pase a esta activity ---> MY_INTENT_ACTIVITY_VALUE

        Toast.makeText(this, getIntent().getStringExtra(MY_INTENT_ACTIVITY_VALUE), Toast.LENGTH_SHORT).show();

        Button botonIngresar =  findViewById(R.id.buttonVolver);
        botonIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // el intent que creo aca le seteo su resultado y lo devuelvo a la activity que lo llamo
                Intent myIntent = new Intent();
                String resultado = "El resultado declarada en la activity destino";
                myIntent.setData(Uri.parse(resultado)); //con uri encapsulo un objeto de datos para pasar entre pantallas
                myIntent.putExtra("Resultado", "BIEN");   //se usa para cuando tengo que enviar varios datos u objetos y luego accedo a ellos por su clave, en este caso por Resultado
                //myIntent.putExtra("Resultado2", "OK2");
                setResult(RESULT_OK, myIntent);
                finish();
            }
        });
    }

    //en caso de que el usuario me la mande al fondo a la app se rompe mi app, para ello uso este metodo
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(RESULT_CANCELED);
    }
}