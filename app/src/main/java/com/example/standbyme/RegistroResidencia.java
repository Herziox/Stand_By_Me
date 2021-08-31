package com.example.standbyme;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegistroResidencia extends AppCompatActivity {

    private EditText etxProvincia, etxCiudad, etxParroquia, etxCallePrincipal, etxCalleSecundaria, etxNumeroCasa, etxLatitud, etxLongitud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_residencia);

        //Captura de datos de la interfaz

        etxProvincia = (EditText) findViewById(R.id.txtProvincia);
        etxCiudad = (EditText) findViewById(R.id.txtCiudad);
        etxParroquia = (EditText) findViewById(R.id.txtParroquia);
        etxCallePrincipal = (EditText) findViewById(R.id.txtCallePrincipal);
        etxCalleSecundaria = (EditText) findViewById(R.id.txtCalleSecundaria);
        etxNumeroCasa = (EditText) findViewById(R.id.txtNumeroCasa);
        etxLatitud = (EditText) findViewById(R.id.txtLatitud);
        etxLongitud = (EditText) findViewById(R.id.txtLongitud);
    }

    public void Registrar(View view) {
        String provincia = etxProvincia.getText().toString();
        String ciudad = etxCiudad.getText().toString();
        String parroquia = etxParroquia.getText().toString();
        String callePrincipal = etxCallePrincipal.getText().toString();
        String calleSecundaria = etxCalleSecundaria.getText().toString();
        String numeroCasa = etxNumeroCasa.getText().toString();
        String latitud = etxLatitud.getText().toString();
        String logitud = etxLongitud.getText().toString();
        boolean registroLleno = true;


        if (provincia.length() == 0) {
            registroLleno = false;
        }
        if (ciudad.length() == 0) {
            registroLleno = false;
        }
        if (parroquia.length() == 0) {
            registroLleno = false;
        }
        if (callePrincipal.length() == 0) {
            registroLleno = false;
        }
        if (calleSecundaria.length() == 0) {
            registroLleno = false;
        }
        if (numeroCasa.length() == 0) {
            registroLleno = false;
        }
        if (latitud.length() == 0) {
            registroLleno = false;
        }
        if (logitud.length() == 0) {
            registroLleno = false;
        }


    }

}