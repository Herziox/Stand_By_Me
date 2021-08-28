package com.example.standbyme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.standbyme.model.AdultoMayor;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class RegistroAdultoMayor extends AppCompatActivity {

    private List<AdultoMayor> listAdultoMayor = new ArrayList<AdultoMayor>();
    ArrayAdapter<AdultoMayor> arrayAdapterAbuelitos;

    private EditText etxNombre, etxApellido, etxNumCel, etxObservaciones, etxFechaNac, etxContra, etxConContra,etxcedula;
    private ListView listaVAbuelitos;
    //Declaramos las variables
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    AdultoMayor adultoMayorSeleccionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_adulto_mayor);

        //Captura de datos de la interfaz
        etxNombre = (EditText)findViewById(R.id.txt_nombrePersonaAM);
        etxApellido = (EditText)findViewById(R.id.txt_apellidoPersonaAM);
        etxNumCel = (EditText)findViewById(R.id.txt_telefonoPersonaAM);
        etxObservaciones = (EditText)findViewById(R.id.txt_observacionesPersonaAM);
        etxFechaNac = (EditText)findViewById(R.id.txt_fechaNacimientoPersonaAM);
        etxContra = (EditText)findViewById(R.id.txt_passwordPersonaAM);
        etxConContra = (EditText)findViewById(R.id.txt_repasswordPersonaAM);
        etxcedula = (EditText)findViewById(R.id.txt_cedulaPersonaAM);
        //Captura de la lista
        listaVAbuelitos = findViewById(R.id.lv_datosAbuelitos);
        //Incialiazmos Firebase
        inicializarFirebase();

        listarDatosAbuelitos();

        listaVAbuelitos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adultoMayorSeleccionado = (AdultoMayor) parent.getItemAtPosition(position);
                etxNombre.setText(adultoMayorSeleccionado.getNombre());
                etxApellido.setText(adultoMayorSeleccionado.getApellido());
                etxNumCel.setText(adultoMayorSeleccionado.getTelefono());
                etxFechaNac.setText(adultoMayorSeleccionado.getFechaNacimiento());
                etxObservaciones.setText(adultoMayorSeleccionado.getObservaciones());
            }
        });
    }

    private void listarDatosAbuelitos() {
        databaseReference.child("AdultoMayor").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listAdultoMayor.clear();
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()){
                    AdultoMayor am = objSnapshot.getValue(AdultoMayor.class);
                    listAdultoMayor.add(am);

                    arrayAdapterAbuelitos = new ArrayAdapter<>(RegistroAdultoMayor.this, android.R.layout.simple_list_item_1,listAdultoMayor);
                    listaVAbuelitos.setAdapter(arrayAdapterAbuelitos);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_adulto_mayor,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        String nombreAM = etxNombre.getText().toString();
        String apellidoAM = etxApellido.getText().toString();
        String numCelAM = etxNumCel.getText().toString();
        String fechaNacAM = etxFechaNac.getText().toString();
        String contraAM = etxContra.getText().toString();
        String conContraAM = etxConContra.getText().toString();
        String cedulaAM = etxcedula.getText().toString();
        String observacionesAM = etxObservaciones.getText().toString();
        switch (item.getItemId()){
            case R.id.icon_add:{
                if(nombreAM.equals("") || apellidoAM.equals("") || contraAM.equals("") || cedulaAM.equals("")
                        || numCelAM.equals("") || fechaNacAM.equals("") || conContraAM.equals("") ){
                    validacion();
                }else{
                    AdultoMayor am = new AdultoMayor();
                    am.setUid(cedulaAM);
                    am.setNombre(nombreAM);
                    am.setApellido(apellidoAM);
                    am.setTelefono(numCelAM);
                    am.setCedula(cedulaAM);
                    am.setFechaNacimiento(fechaNacAM);
                    am.setContraseña(contraAM);
                    am.setObservaciones(observacionesAM);
                    databaseReference.child("AdultoMayor").child(am.getUid()).setValue(am).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                limpiarCajas();
                            } else {
                                Toast.makeText(RegistroAdultoMayor.this, "No se pudo registrar los datos correctamente", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    Toast.makeText(this, "Agregado", Toast.LENGTH_LONG).show();
                    limpiarCajas();
                }
                break;

            }
            case R.id.icon_save:{
                AdultoMayor am = new AdultoMayor();
                am.setUid(cedulaAM);
                am.setNombre(nombreAM);
                am.setApellido(apellidoAM);
                am.setTelefono(numCelAM);
                am.setFechaNacimiento(fechaNacAM);
                am.setContraseña(contraAM);
                am.setObservaciones(observacionesAM);
                databaseReference.child("AdultoMayor").child(am.getUid()).setValue(am).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            limpiarCajas();
                        } else {
                            Toast.makeText(RegistroAdultoMayor.this, "No se pudo registrar los datos correctamente", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                Toast.makeText(this, "Guardado", Toast.LENGTH_LONG).show();
                limpiarCajas();
                break;
            }
            case R.id.icon_delete:{
                AdultoMayor am = new AdultoMayor();
                am.setUid(adultoMayorSeleccionado.getUid());
                databaseReference.child("AdultoMayor").child(am.getUid()).removeValue();
                Toast.makeText(this, "Borrado", Toast.LENGTH_LONG).show();
                limpiarCajas();
                break;
            }

        }
        return true;
    }
    private void limpiarCajas(){
        etxNombre.setText("");
        etxApellido.setText("");
        etxNumCel.setText("");
        etxContra.setText("");
        etxObservaciones.setText("");
        etxConContra.setText("");
        etxcedula.setText("");
        etxFechaNac.setText("");
    }

    private boolean validacion() {
        String nombreAM = etxNombre.getText().toString();
        String apellidoAM = etxApellido.getText().toString();
        String numCelAM = etxNumCel.getText().toString();
        String cedulaAM = etxcedula.getText().toString();
        String fechaNacAM = etxFechaNac.getText().toString();
        String contraAM = etxContra.getText().toString();
        String conContraAM = etxConContra.getText().toString();

        boolean registroLleno = true;

        if (nombreAM.length() == 0){
            etxNombre.setError("Requerido");
            registroLleno = false;
        }
        if (apellidoAM.length() == 0){
            etxApellido.setError("Requerido");
            registroLleno = false;
        }
        if (numCelAM.length() == 0){
            etxNumCel.setError("Requerido");
            registroLleno = false;
        }
        if (cedulaAM.length() == 0){
            etxcedula.setError("Requerido");
            registroLleno = false;
        }
        if (fechaNacAM.length() == 0){
            etxFechaNac.setError("Requerido");
            registroLleno = false;
        }
        if (contraAM.length() == 0){
            etxContra.setError("Requerido");
            registroLleno = false;
        }
        if (conContraAM.length() == 0){
            etxConContra.setError("Requerido");
            registroLleno = false;
        }
        if (!conContraAM.equals(contraAM)){
            Toast.makeText(this, "La contraseña no coincide", Toast.LENGTH_SHORT).show();
            registroLleno = false;
        }
        return registroLleno;
    }



}

