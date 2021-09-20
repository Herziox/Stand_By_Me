package com.example.standbyme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class PantallaAdutoMayor extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_aduto_mayor);
    }

    public void salir(View view){
        Intent siguiente = new Intent(this, MainActivity.class);
        startActivity(siguiente);
    }

}