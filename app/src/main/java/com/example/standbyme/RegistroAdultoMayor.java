package com.example.standbyme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.standbyme.model.AdultoMayor;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RegistroAdultoMayor extends AppCompatActivity {

    private List<AdultoMayor> listAdultoMayor = new ArrayList<AdultoMayor>();
    ArrayAdapter<AdultoMayor> arrayAdapterAbuelitos;

    private EditText nomAM, appAM, cedulaAM, numtelfAM,  fechaNacimientoAM,psswordAM,rePpasswordAM, observacionesAMa;
    private ListView listV_AdultoMayor;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_adulto_mayor);

        nomAM = (EditText) findViewById(R.id.textFieldNombreAM);
        appAM = (EditText) findViewById(R.id.textFieldApellidoAM);
        cedulaAM = (EditText) findViewById(R.id.textFieldNumeroCedulaAM);
        numtelfAM = (EditText) findViewById(R.id.textFieldNumeroTelefonoAM);
        fechaNacimientoAM = (EditText) findViewById(R.id.textFieldFechaDeNacimientoAM);
        psswordAM = (EditText) findViewById(R.id.textFieldPasswordAM);
        rePpasswordAM = (EditText) findViewById(R.id.textFieldRePasswordAM);
        observacionesAMa = (EditText) findViewById(R.id.textFielObservacionesAM);

        listV_AdultoMayor =  findViewById(R.id.lv_datosAbuelitos);

        inicializarFirebase();

        listarDatos();


    }

    private void listarDatos() {
        databaseReference.child("AdultoMayor");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listAdultoMayor.clear();
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    AdultoMayor am = childSnapshot.getValue(AdultoMayor.class);
                    listAdultoMayor.add(am);

                    arrayAdapterAbuelitos = new ArrayAdapter<AdultoMayor>(RegistroAdultoMayor.this, android.R.layout.simple_list_item_1, listAdultoMayor);
                    listV_AdultoMayor.setAdapter(arrayAdapterAbuelitos);
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

        String nombre = nomAM.getText().toString();
        String apellido = appAM.getText().toString();
        String numCedula = cedulaAM.getText().toString();
        String numTelefono = numtelfAM.getText().toString();
        String fechaNacimiento = fechaNacimientoAM.getText().toString();
        String password = psswordAM.getText().toString();
        String rePassword = rePpasswordAM.getText().toString();
        String observaciones = observacionesAMa.getText().toString();

        switch (item.getItemId()){
            case R.id.icon_add:{

                if (nombre.isEmpty() || apellido.isEmpty() || numCedula.isEmpty() || numTelefono.isEmpty()
                        || fechaNacimiento.isEmpty() || password.isEmpty() || rePassword.isEmpty()){
                    validacion();
                }
                else{
                    if (password.equals(rePassword)){
                        AdultoMayor am = new AdultoMayor();
                        am.setUid(numCedula);
                        am.setNombre(nombre);
                        am.setApellido(apellido);
                        am.setCedula(numCedula);
                        am.setTelefono(numTelefono);
                        am.setFechaNacimiento(fechaNacimiento);
                        am.setContraseña(password);
                        am.setObservaciones(observaciones);
                        databaseReference.child("AdultoMayor").child(am.getUid()).setValue(am);
                        Toast.makeText(this, "Agregado", Toast.LENGTH_SHORT).show();

                    }else{
                        rePpasswordAM.setError("Las contraseñas no coinciden");
                    }
                }

                break;
            }
            case R.id.icon_save:{
                Toast.makeText(this, "Guardado", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.icon_delete:{
                Toast.makeText(this, "Eliminado", Toast.LENGTH_SHORT).show();
                break;
            }
        }
        return true;
    }
    private void limpiarCajas(){
        nomAM.setText("");
        appAM.setText("");
        cedulaAM.setText("");
        numtelfAM.setText("");
        fechaNacimientoAM.setText("");
        psswordAM.setText("");
        rePpasswordAM.setText("");
        observacionesAMa.setText("");
    }

    private void validacion() {
        String nombre = nomAM.getText().toString();
        String apellido = appAM.getText().toString();
        String numCedula = cedulaAM.getText().toString();
        String numTelefono = numtelfAM.getText().toString();
        String fechaNacimiento = fechaNacimientoAM.getText().toString();
        String password = psswordAM.getText().toString();
        String rePassword = rePpasswordAM.getText().toString();

        if (nombre.isEmpty()){
            nomAM.setError("Requerido");
        }
        if (apellido.isEmpty()){
            appAM.setError("Requerido");
        }
        if (numCedula.isEmpty()){
            cedulaAM.setError("Requerido");
        }
        if (numTelefono.isEmpty()){
            numtelfAM.setError("Requerido");
        }
        if (fechaNacimiento.isEmpty()){
            fechaNacimientoAM.setError("Requerido");
        }
        if (password.isEmpty()){
            psswordAM.setError("Requerido");
        }
        if (rePassword.isEmpty()){
            rePpasswordAM.setError("Requerido");
        }

    }

}