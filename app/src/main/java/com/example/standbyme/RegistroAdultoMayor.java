package com.example.standbyme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegistroAdultoMayor extends AppCompatActivity {

    private EditText etxNombre, etxApellido, etxNumCel, etxObservaciones, etxFechaNac, etxContra, etxConContra,etxcedula;

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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_adulto_mayor,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.icon_add:{
                Toast.makeText(this, "Agregar", Toast.LENGTH_LONG).show();
                break;
            }
            case R.id.icon_save:{
                Toast.makeText(this, "Guardado", Toast.LENGTH_LONG).show();
                break;
            }
            case R.id.icon_delete:{
                Toast.makeText(this, "Borrado", Toast.LENGTH_LONG).show();
                break;
            }

        }
        return true;
    }

    public void Registrar(View view){
        String nombreAM = etxNombre.getText().toString();
        String apellidoAM = etxApellido.getText().toString();
        String numCelAM = etxNumCel.getText().toString();
        String observacionesAM = etxObservaciones.getText().toString();
        String fechaNacAM = etxFechaNac.getText().toString();
        String contraAM = etxConContra.getText().toString();
        String conContraAM = etxConContra.getText().toString();
        boolean registroLleno = true;


        if (nombreAM.length() == 0){
            registroLleno = false;
        }
        if (apellidoAM.length() == 0){
            registroLleno = false;
        }
        if (numCelAM.length() == 0){
            registroLleno = false;
        }
        if (fechaNacAM.length() == 0){
            registroLleno = false;
        }
        if (contraAM.length() == 0){
            registroLleno = false;
        }
        if (conContraAM.length() == 0){
            registroLleno = false;
        }
        if (!conContraAM.equals(contraAM)){
            Toast.makeText(this, "La contraseña no coincide", Toast.LENGTH_SHORT).show();
            registroLleno = false;
        }
        if (registroLleno){
            Toast.makeText(this, "Registro lleno", Toast.LENGTH_SHORT).show();
            System.out.println("res: "+nombreAM+" "+apellidoAM+" "+numCelAM+" "+observacionesAM+" "+fechaNacAM+" "+contraAM+"\n");
        }else{
            Toast.makeText(this, "Por favor llene correctamente los campos", Toast.LENGTH_SHORT).show();
        }

    }
}