package com.example.standbyme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    //Botón Inicio Sesión
    public void InicioSesion(View view){
        Intent inicioSesion = new Intent(this, SeleccionarUsuario.class);
        startActivity(inicioSesion);
    }

    //Botón Registrarse
    public void Registrarse(View view){
        Intent registrarse = new Intent(this, SeleccionarUsuario.class);
        startActivity(registrarse);
    }




}