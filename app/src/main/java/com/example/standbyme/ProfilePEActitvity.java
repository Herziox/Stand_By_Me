package com.example.standbyme;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class ProfilePEActitvity extends AppCompatActivity {

    private Button mButtonSignOut,mButtonAdministrarAM, mMonitorearAM, mButtonAdministrarCuenta;
    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_actitvity);

        mFirebaseAuth = FirebaseAuth.getInstance();

        mButtonSignOut = (Button) findViewById(R.id.btnSignout);
        mButtonAdministrarAM = (Button) findViewById(R.id.btnAdministrarAdultoMayor);
        mMonitorearAM = (Button) findViewById(R.id.btnMonitorearAdultoMayor);
        mButtonAdministrarCuenta = (Button) findViewById(R.id.btnAdministrarCuenta);


        mButtonSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFirebaseAuth.signOut();
                startActivity(new Intent(ProfilePEActitvity.this, MainActivity.class));
                finish();
            }
        });

         mMonitorearAM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfilePEActitvity.this, MapsActivity.class));
            }
        });


        mButtonAdministrarAM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfilePEActitvity.this, RegistroAdultoMayor.class));
            }
        });

        mButtonAdministrarCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfilePEActitvity.this, AdminPE.class));
                finish();
            }
        });

    }

}