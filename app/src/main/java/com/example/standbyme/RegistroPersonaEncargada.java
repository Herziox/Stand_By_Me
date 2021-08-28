package com.example.standbyme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.standbyme.model.PersonaEncargada;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RegistroPersonaEncargada extends AppCompatActivity {
    private EditText etxNombre, etxApellido, etxNumCel, etxObservaciones, etxCorreoElec, etxContra, etxConContra, etxcedula;
    private Button mButtonRegister;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_persona_encargada);
        //Captura de datos de la interfaz
        etxNombre = (EditText) findViewById(R.id.txtNombrePE);
        etxApellido = (EditText) findViewById(R.id.txtApellidoPE);
        etxNumCel = (EditText) findViewById(R.id.txtNumCelPE);
        etxcedula = (EditText) findViewById(R.id.txcedulaPE);
        etxCorreoElec = (EditText) findViewById(R.id.txtCorreoElectronicoPE);
        etxContra = (EditText) findViewById(R.id.txtContraPE);
        etxConContra = (EditText) findViewById(R.id.txtConContraPE);

        mButtonRegister = (Button) findViewById(R.id.btnRegistrase);

        inicializarFirebase();

        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etxNombre.getText().toString();
                String email = etxCorreoElec.getText().toString();
                String password = etxContra.getText().toString();
                String repassword = etxConContra.getText().toString();
                String numeCelular = etxNumCel.getText().toString();
                String numCedula = etxcedula.getText().toString();
                String apellido = etxApellido.getText().toString();

                if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty() && !repassword.isEmpty()
                        && !numeCelular.isEmpty() && !numCedula.isEmpty() && !apellido.isEmpty() ) {
                    if (password.length() >= 6) {
                        if (password.equals(repassword)) {
                            registrar();
                        }else{
                            etxConContra.setError("Las contraseñas no coincieden");
                        }
                    } else {
                        Toast.makeText(RegistroPersonaEncargada.this, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RegistroPersonaEncargada.this, "Debe completar los campos", Toast.LENGTH_SHORT).show();
                }


            }
        });



    }
    public void inicializarFirebase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void registrar() {
        String name = etxNombre.getText().toString();
        String email = etxCorreoElec.getText().toString();
        String password = etxContra.getText().toString();
        String repassword = etxConContra.getText().toString();
        String numeCelular = etxNumCel.getText().toString();
        String numCedula = etxcedula.getText().toString();
        String apellido = etxApellido.getText().toString();
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    PersonaEncargada pe = new PersonaEncargada();
                    pe.setUid(numCedula);
                    pe.setNombre(name);
                    pe.setApellido(apellido);
                    pe.setContraseña(password);
                    pe.setNumeroCelular(numeCelular);
                    pe.setCorreoElectronico(email);
                    pe.setCedula(numCedula);
                    databaseReference.child("PersonaEncargada").child(pe.getUid()).setValue(pe).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if (task2.isSuccessful()) {
                                startActivity(new Intent(RegistroPersonaEncargada.this, ProfileActitvity.class));
                                finish();
                            } else {
                                Toast.makeText(RegistroPersonaEncargada.this, "No se pudo registrar los datos correctamente", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(RegistroPersonaEncargada.this, "No se pudo registrar el usuario", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}




