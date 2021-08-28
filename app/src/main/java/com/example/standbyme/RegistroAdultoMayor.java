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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class RegistroAdultoMayor extends AppCompatActivity {

    private List<AdultoMayor> listPerson = new ArrayList<AdultoMayor>();
    ArrayAdapter<AdultoMayor> arrayAdapterPersona;

    EditText nomAM, appAM, cedulaAM, numtelfAM, passwordAM,rePpasswordAM, observacionesAM;
    ListView listV_personas;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_adulto_mayor);

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
                Toast.makeText(this, "Agregado", Toast.LENGTH_SHORT).show();
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
}