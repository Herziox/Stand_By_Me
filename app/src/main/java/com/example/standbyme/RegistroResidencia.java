package com.example.standbyme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegistroResidencia extends AppCompatActivity {

    private EditText  etxMetros, etxLatitud, etxLongitud;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_residencia);

        //Captura de datos de la interfaz

        etxMetros = (EditText) findViewById(R.id.txtMetros);
        etxLatitud = (EditText) findViewById(R.id.txtLatitud);
        etxLongitud = (EditText) findViewById(R.id.txtLongitud);


    }
    //Carga de registro manual
    public void cargaManual(View view){
        Intent cargaMan = new Intent(this, registro_Localizacion.class);
        startActivity(cargaMan);
    }


    public void Registrar(View view) {
        String metros = etxMetros.getText().toString();
        String latitud = etxLatitud.getText().toString();
        String logitud = etxLongitud.getText().toString();
        boolean registroLleno = true;

        if (metros.length() == 0) {
            registroLleno = false;
        }
        if (latitud.length() == 0) {
            registroLleno = false;
        }
        if (logitud.length() == 0) {
            registroLleno = false;
        }

        if (registroLleno) {
            Toast.makeText(this, "Residencia registrada", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "Debe completar los campos", Toast.LENGTH_SHORT).show();
        }


    }

}