package com.example.myapplication;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.example.myapplication.fragments.interfaceFragments.IComunicationsFragment;
import com.example.myapplication.modelo.Exhibits;
import com.google.zxing.Result;

public class CameraActivity extends AppCompatActivity {

    private CodeScanner mCodeScanner;
    static final int REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        CodeScannerView scannerView = findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(this, scannerView);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String codigo = result.getText();
                        Toast.makeText(CameraActivity.this, codigo, Toast.LENGTH_SHORT).show();
                        Intent myIntent = new Intent(CameraActivity.this, CamaraDetails.class);
                        //Esta harcodeado pero aca hay que probar como mandar el objeto
                        myIntent.putExtra("codigoQR",codigo);
                        startActivity(myIntent);
                    }
                });
            }
        });
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();
            }
        });

        Button botonVolverQR = findViewById(R.id.buttonVolverQR);
        botonVolverQR.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                Intent myIntent = new Intent( CameraActivity.this, Principal.class);
                startActivityForResult(myIntent, REQUEST);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    protected void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }


}