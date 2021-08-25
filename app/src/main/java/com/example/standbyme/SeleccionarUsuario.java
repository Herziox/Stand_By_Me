package com.example.standbyme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SeleccionarUsuario extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccionar_usuario);
    }

    //Seleccion Persona Encargada

    public void seleccionPersonaEncarda(View view){
        Intent siguiente = new Intent(this, RegistroPersonaEncargada.class);
        startActivity(siguiente);
    }

    public void seleccionAdultoMayor(View view){
        Intent siguiente = new Intent(this, RegistroAdultoMayor.class);
        startActivity(siguiente);
    }
}