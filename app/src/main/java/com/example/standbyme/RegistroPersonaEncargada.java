package com.example.standbyme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegistroPersonaEncargada extends AppCompatActivity {
    private EditText etxNombre, etxApellido, etxNumCel, etxObservaciones, etxFechaNac, etxContra, etxConContra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_persona_encargada);
        //Captura de datos de la interfaz
        etxNombre = (EditText) findViewById(R.id.txtNombre);
        etxApellido = (EditText) findViewById(R.id.txtApellido);
        etxNumCel = (EditText) findViewById(R.id.txtNumCel);
        etxFechaNac = (EditText) findViewById(R.id.txtCorreoElectronicoPE);
        etxContra = (EditText) findViewById(R.id.txtContra);
        etxConContra = (EditText) findViewById(R.id.txtConContra);
    }

    public void Registrar(View view) {
        String nombre = etxNombre.getText().toString();
        String apellido = etxApellido.getText().toString();
        String numCelAM = etxNumCel.getText().toString();
        String fechaNac = etxFechaNac.getText().toString();
        String contra = etxConContra.getText().toString();
        String conContra = etxConContra.getText().toString();
        boolean registroLleno = true;


        if (nombre.length() == 0) {
            registroLleno = false;
        }
        if (apellido.length() == 0) {
            registroLleno = false;
        }
        if (numCelAM.length() == 0) {
            registroLleno = false;
        }
        if (fechaNac.length() == 0) {
            registroLleno = false;
        }
        if (contra.length() == 0) {
            registroLleno = false;
        }
        if (conContra.length() == 0) {
            registroLleno = false;
        }
        if (!conContra.equals(contra)) {
            Toast.makeText(this, "La contraseña no coincide", Toast.LENGTH_SHORT).show();
            registroLleno = false;
        }
        if (registroLleno) {
            Toast.makeText(this, "Registro lleno", Toast.LENGTH_SHORT).show();
            System.out.println("res: " + nombre + " " + apellido + " " + numCelAM  + " " + fechaNac + " " + contra + "\n");
            Intent siguiente = new Intent(this, RegistroAdultoMayor.class);
            startActivity(siguiente);
        } else {
            Toast.makeText(this, "Por favor llene correctamente los campos", Toast.LENGTH_SHORT).show();
        }
    }


}
