package com.example.standbyme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.standbyme.model.AdultoMayor;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
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

    private EditText nomAM, appAM, cedulaAM, numtelfAM,  fechaNacimientoAM,psswordAM,rePpasswordAM, observacionesAM, rangoAM, latitudaAM, longitudAM ;
    private ListView listV_AdultoMayor;

    private FirebaseAuth mFirebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    AdultoMayor adultoMayorSelected;

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
        observacionesAM = (EditText) findViewById(R.id.textFielObservacionesAM);
        rangoAM = (EditText) findViewById(R.id.txtMetros);
        latitudaAM = (EditText) findViewById(R.id.textViewLatitud);
        longitudAM = (EditText) findViewById(R.id.textViewLongitud);

        listV_AdultoMayor =  findViewById(R.id.lv_datosAbuelitos);

        inicializarFirebase();

        listarDatos();

        fechaNacimientoAM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.textFieldFechaDeNacimientoAM:
                        showDatePickerDialog();
                        break;
                }
            }
        });

        listV_AdultoMayor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adultoMayorSelected = (AdultoMayor) parent.getItemAtPosition(position);
                nomAM.setText(adultoMayorSelected.getNombre());
                appAM.setText(adultoMayorSelected.getApellido());
                cedulaAM.setText(adultoMayorSelected.getCedula());
                numtelfAM.setText(adultoMayorSelected.getTelefono());
                fechaNacimientoAM.setText(adultoMayorSelected.getFechaNacimiento());
                psswordAM.setText(adultoMayorSelected.getContraseña());
                rePpasswordAM.setText(adultoMayorSelected.getContraseña());
                observacionesAM.setText(adultoMayorSelected.getObservaciones());
                rangoAM.setText(adultoMayorSelected.getRangoDeCirculacion());
                latitudaAM.setText(adultoMayorSelected.getLatitud());
                longitudAM.setText(adultoMayorSelected.getLongitud());
            }
        });


    }

    private void showDatePickerDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                final String selectedDate = twoDigits(day) + "/" + twoDigits(month+1) + "/" + year;
                fechaNacimientoAM.setText(selectedDate);
            }
        });
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
    private String twoDigits(int n) {
        return (n<=9) ? ("0"+n) : String.valueOf(n);
    }

    private void listarDatos() {

        databaseReference.child("AdultoMayor1").addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listAdultoMayor.clear();
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    AdultoMayor am = childSnapshot.getValue(AdultoMayor.class);
                    listAdultoMayor.add(am);

                    arrayAdapterAbuelitos = new ArrayAdapter<>(RegistroAdultoMayor.this, android.R.layout.simple_list_item_1, listAdultoMayor);
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
        mFirebaseAuth = FirebaseAuth.getInstance();

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
        String observaciones = observacionesAM.getText().toString();
        String rango = rangoAM.getText().toString();
        String latitud = latitudaAM.getText().toString();
        String longitud = longitudAM.getText().toString();

        switch (item.getItemId()){
            case R.id.icon_add:{

                if (nombre.isEmpty() || apellido.isEmpty() || numCedula.isEmpty() || numTelefono.isEmpty()
                        || fechaNacimiento.isEmpty() || password.isEmpty() || rePassword.isEmpty()){
                    validacionVacios();
                }
                else{
                    if (validacion()){
                        String idPE = mFirebaseAuth.getCurrentUser().getUid();
                        AdultoMayor am = new AdultoMayor();
                        am.setUid(numCedula);
                        am.setNombre(nombre);
                        am.setApellido(apellido);
                        am.setCedula(numCedula);
                        am.setTelefono(numTelefono);
                        am.setFechaNacimiento(fechaNacimiento);
                        am.setContraseña(password);
                        am.setObservaciones(observaciones);
                        am.setPkIDPersonaEncargada(idPE);
                        am.setRangoDeCirculacion(rango);
                        am.setLongitud(longitud);
                        am.setLatitud(latitud);
                        databaseReference.child("AdultoMayor1").child(am.getUid()).setValue(am);
                        Toast.makeText(RegistroAdultoMayor.this, "Agregado", Toast.LENGTH_SHORT).show();
                        limpiarCajas();
                    }
                }

                break;
            }
            case R.id.icon_save:{
                if (listAdultoMayor.isEmpty()){
                    startActivity(new Intent( this, RegistroAdultoMayor.class));
                }else{
                    if (validacion()){
                        String idPE = mFirebaseAuth.getCurrentUser().getUid();
                        AdultoMayor am = new AdultoMayor();
                        am.setUid(adultoMayorSelected.getUid());
                        am.setNombre(nombre);
                        am.setApellido(apellido);
                        am.setTelefono(numTelefono);
                        am.setCedula(numCedula);
                        am.setFechaNacimiento(fechaNacimiento);
                        am.setContraseña(password);
                        am.setObservaciones(observaciones);
                        am.setPkIDPersonaEncargada(idPE);
                        am.setRangoDeCirculacion(rango);
                        am.setLongitud(longitud);
                        am.setLatitud(latitud);
                        databaseReference.child("AdultoMayor1").child(am.getUid()).setValue(am);
                        Toast.makeText(this, "Actualizado", Toast.LENGTH_SHORT).show();
                        limpiarCajas();
                    }
                }
                break;
            }
            case R.id.icon_delete:{
                if (listAdultoMayor.isEmpty()){
                    startActivity(new Intent( this, RegistroAdultoMayor.class));
                }else{
                    AdultoMayor am = new AdultoMayor();
                    am.setUid(adultoMayorSelected.getUid());
                    databaseReference.child("AdultoMayor1").child(am.getUid()).removeValue();
                    Toast.makeText(this, "Eliminado", Toast.LENGTH_SHORT).show();
                }

                break;
            }
            case R.id.icon_residencia:{
                startActivity(new Intent( this, ProfileActitvity.class));
                break;
            }
            default:break;
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
        observacionesAM.setText("");
    }

    public void cargarResidencia(View view){
        Intent siguiente = new Intent(this, MapsActivity.class);
        startActivity(siguiente);
    }

    private void validacionVacios() {
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
    private boolean validacion(){

        String nombre = nomAM.getText().toString();
        String apellido = appAM.getText().toString();
        String numCedula = cedulaAM.getText().toString();
        String numTelefono = numtelfAM.getText().toString();
        String fechaNacimiento = fechaNacimientoAM.getText().toString();
        String password = psswordAM.getText().toString();
        String rePassword = rePpasswordAM.getText().toString();
        if (isEcuadorianDocumentValid(numCedula) == false){
            cedulaAM.setError("El número de cédula es incorrecto");
            return false;
        }
        if (numTelefono.length() < 10 ){
            numtelfAM.setError("El número celular es incorrecto");
            return false;
        }
        if (password.length() >= 6){
            if (!password.equals(rePassword)){
                rePpasswordAM.setError("La contraseña no coincide");
                return false;
            }
        }else{
            psswordAM.setError("La contraseña debe ser mayor de 6 digitos");
            return false;
        }
        return true;
    }
    private boolean isEcuadorianDocumentValid(String document) {
        byte sum = 0;
        try {
            if (document.trim().length() != 10)
                return false;
            String[] data = document.split("");
            byte verifier = Byte.parseByte(data[0] + data[1]);
            if (verifier < 1 || verifier > 24)
                return false;
            byte[] digits = new byte[data.length];
            for (byte i = 0; i < digits.length; i++)
                digits[i] = Byte.parseByte(data[i]);
            if (digits[2] > 6)
                return false;
            for (byte i = 0; i < digits.length - 1; i++) {
                if (i % 2 == 0) {
                    verifier = (byte) (digits[i] * 2);
                    if (verifier > 9)
                        verifier = (byte) (verifier - 9);
                } else
                    verifier = (byte) (digits[i] * 1);
                sum = (byte) (sum + verifier);
            }
            if ((sum - (sum % 10) + 10 - sum) == digits[9])
                return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}