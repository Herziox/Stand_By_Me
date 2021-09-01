package com.example.standbyme;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;


public class MainActivity extends AppCompatActivity {

    FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFirebaseAuth = FirebaseAuth.getInstance();
    }

    //Botón Inicio Sesión
    public void InicioSesion(View view){
        Intent inicioSesion = new Intent(this, LoginActivity.class);
        startActivity(inicioSesion);
    }

    //Botón Registrarse
    public void Registrarse(View view){
        Intent registrarse = new Intent(this, RegistroPersonaEncargada.class);
        startActivity(registrarse);
    }

    //Para que no vuelva e esta pantalla cuando se inice session *IMPORTANTE*
    @Override
    protected void onStart() {
        super.onStart();
        if (mFirebaseAuth.getCurrentUser()!= null){
            startActivity(new Intent(MainActivity.this, ProfileActitvity.class));
            finish();
        }
    }
}