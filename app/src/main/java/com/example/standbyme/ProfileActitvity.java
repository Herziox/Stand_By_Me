package com.example.standbyme;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class ProfileActitvity extends AppCompatActivity {

    private Button mButtonSignOut,mButtonAdministrarAM, mMonitorearAM, mButtonAdministrarCuenta;
    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_actitvity);

        mFirebaseAuth = FirebaseAuth.getInstance();

        mButtonSignOut = (Button) findViewById(R.id.btnsignout);
        mButtonAdministrarAM = (Button) findViewById(R.id.btnAdministrarAdultoMayor);
        mMonitorearAM = (Button) findViewById(R.id.btnMonitorearAdultoMayor);
        mButtonAdministrarCuenta = (Button) findViewById(R.id.btnAdministrarCuenta);


        mButtonSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFirebaseAuth.signOut();
                startActivity(new Intent(ProfileActitvity.this, MainActivity.class));
                finish();
            }
        });

         mMonitorearAM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActitvity .this, registro_Localizacion.class));
            }
        });


        mButtonAdministrarAM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileActitvity.this, RegistroAdultoMayor.class));
            }
        });

        mButtonAdministrarCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileActitvity.this, AdminPE.class));
                finish();
            }
        });

    }

}