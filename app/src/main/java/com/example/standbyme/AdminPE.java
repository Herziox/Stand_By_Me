package com.example.standbyme;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.standbyme.model.PersonaEncargada;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminPE extends AppCompatActivity {

    private EditText mNombre, mApellido, mcorreo, mCedula,mNumCelular, mPassword;
    private Button mButtonActualizar, mButtonCambiarContraseña, mButtonSalir;
    private FirebaseAuth mFirebaseAuth;
    private  DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_pe);
        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        mNombre = (EditText) findViewById(R.id.textFieldNombreAPE);
        mApellido = (EditText) findViewById(R.id.textFieldApellidoAPE);
        mcorreo = (EditText) findViewById(R.id.textFieldEmailAPE);
        mCedula = (EditText) findViewById(R.id.textFieldNumCedulaAPE);
        mNumCelular = (EditText) findViewById(R.id.textFieldNumCelularAPE);

        mButtonActualizar = (Button) findViewById(R.id.btnActualizarPE);
        mButtonCambiarContraseña = (Button) findViewById(R.id.btnSalirPE);
        mButtonSalir = (Button) findViewById(R.id.btnRestablecerPasswordPE);

        obtenerInfo();

        mButtonSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminPE.this, ProfilePEActitvity.class));
                finish();
            }
        });
        mButtonSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminPE.this, ResetPassword.class));
            }
        });
        mButtonActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = mFirebaseAuth.getCurrentUser().getUid();
                String nombre = mNombre.getText().toString();
                String apellido = mApellido.getText().toString();
                String numCedula = mCedula.getText().toString();
                String numTelefono = mNumCelular.getText().toString();
                String correo = mcorreo.getText().toString();
                PersonaEncargada pe = new PersonaEncargada();
                pe.setUid(id);
                pe.setNombre(nombre);
                pe.setApellido(apellido);
                pe.setCedula(numCedula);
                pe.setNumeroCelular(numTelefono);
                pe.setCorreoElectronico(correo);
                pe.setContraseña(pe.getContraseña());
                mDatabaseReference.child("PersonaEncargada").child(id).setValue(pe);
                Toast.makeText(AdminPE.this, "Actualizado", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void obtenerInfo(){
        String id = mFirebaseAuth.getCurrentUser().getUid();
        mDatabaseReference.child("PersonaEncargada").child(id).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){

                    String name = snapshot.child("nombre").getValue().toString();
                    String apellido = snapshot.child("apellido").getValue().toString();
                    String numCedula = snapshot.child("cedula").getValue().toString();
                    String numCelular = snapshot.child("numeroCelular").getValue().toString();
                    String CorreoElectronico = snapshot.child("correoElectronico").getValue().toString();

                    mNombre.setText(name);
                    mApellido.setText(apellido);
                    mcorreo.setText(CorreoElectronico);
                    mCedula.setText(numCedula);
                    mNumCelular.setText(numCelular);

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}