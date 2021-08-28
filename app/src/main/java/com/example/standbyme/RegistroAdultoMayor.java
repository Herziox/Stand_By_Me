package com.example.standbyme;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

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
}