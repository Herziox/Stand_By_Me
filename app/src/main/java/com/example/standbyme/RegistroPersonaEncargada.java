package com.example.standbyme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.standbyme.model.PersonaEncargada;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class RegistroPersonaEncargada extends AppCompatActivity {
    private EditText etxNombre, etxApellido, etxNumCel, etxObservaciones, etxCorreoElec, etxContra, etxConContra;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_persona_encargada);
        //Captura de datos de la interfaz
        etxNombre = (EditText) findViewById(R.id.txtNombrePE);
        etxApellido = (EditText) findViewById(R.id.txtApellidoPE);
        etxNumCel = (EditText) findViewById(R.id.txtNumCelPE);
        etxCorreoElec = (EditText) findViewById(R.id.txtCorreoElectronicoPE);
        etxContra = (EditText) findViewById(R.id.txtContraPE);
        etxConContra = (EditText) findViewById(R.id.txtConContraPE);

        inicializarFirebase();



    }
    public void inicializarFirebase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    public void Registrar(View view) {
        String nombre = etxNombre.getText().toString();
        String apellido = etxApellido.getText().toString();
        String numCelAM = etxNumCel.getText().toString();
        String correoElectronico = etxCorreoElec.getText().toString();
        String contra = etxContra.getText().toString();
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
        if (correoElectronico.length() == 0) {
            registroLleno = false;
        }
        if (contra.length() == 0) {
            registroLleno = false;
        }
        if (conContra.length() == 0) {
            registroLleno = false;
        }else{
        if (!contra.equals(conContra)) {
            Toast.makeText(this, "La contraseña no coincide", Toast.LENGTH_SHORT).show();
            registroLleno = false;
        }}
        if (registroLleno) {
            PersonaEncargada pe = new PersonaEncargada();
            pe.setUid(UUID.randomUUID().toString());
            pe.setNombre(nombre);
            pe.setApellido(apellido);
            pe.setNumeroCelular(numCelAM);
            pe.setCorreoElectronico(correoElectronico);
            pe.setContraseña(contra);
            databaseReference.child("PersonaEncargada").child(pe.getUid()).setValue(pe);
            Toast.makeText(this, "Registrado", Toast.LENGTH_SHORT).show();
            System.out.println("res: " + nombre + " " + apellido + " " + numCelAM  + " " + correoElectronico + " " + contra + "\n");

            Intent siguiente = new Intent(this, RegistroAdultoMayor.class);
            startActivity(siguiente);

        } else {
            Toast.makeText(this, "Por favor llene correctamente los campos", Toast.LENGTH_SHORT).show();
        }
    }


}
