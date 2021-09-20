package com.example.standbyme;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class SeleccionarUsuario extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccionar_usuario);




    }

    //Seleccion Persona Encargada

    public void seleccionPersonaEncarda(View view){
        Intent siguiente = new Intent(this, LoginPEActivity.class);
        startActivity(siguiente);
    }

    public void seleccionAdultoMayor(View view){
        Intent siguiente = new Intent(this, LoginAM.class);
        startActivity(siguiente);
    }
}