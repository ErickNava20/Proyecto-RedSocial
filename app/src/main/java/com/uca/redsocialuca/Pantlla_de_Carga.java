package com.uca.redsocialuca;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class Pantlla_de_Carga extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_de_carga);
        //este método me permite cuanto tiempo demora la pantalla principal
        final int Duracion= 2500;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //esto es lo que se ejecutara para comenzar con la aplicación
                Intent intent =new Intent(Pantlla_de_Carga.this, MainActivity.class);
                startActivity(intent);
                //esto permite cambiar de una actividad a otra
            }
        },Duracion);
    }
}