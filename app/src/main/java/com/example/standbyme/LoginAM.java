package com.example.standbyme;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class LoginAM extends AppCompatActivity {

    private EditText mEditTextEmail, mEditTextPassword;
    private Button mButtonLogin, mButtonOlvidadoPassword;

    private String email = "";
    private String password = "";
    FirebaseDatabase firebaseDatabase;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_am);


        mEditTextEmail = (EditText) findViewById(R.id.editTextEmail);
        mEditTextPassword = (EditText) findViewById(R.id.editTextPassword);
        mButtonLogin = (Button) findViewById(R.id.btnLogin);
        mButtonOlvidadoPassword = (Button) findViewById(R.id.btnOlvidarPassword);

        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = mEditTextEmail.getText().toString();
                password = mEditTextPassword.getText().toString();
                if (!email.isEmpty() && !password.isEmpty()){
                    loginUser();
                }else{
                    Toast.makeText(LoginAM.this, "Complete los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mButtonOlvidadoPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginAM.this, ResetPassword.class));
            }
        });
    }

    private void loginUser() {
        mDatabase=FirebaseDatabase.getInstance().getReference();
        mDatabase.child("AdultoMayor1").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String cedula= snapshot.child(email).getValue().toString();
                    String pass= snapshot.child(email).child("contraseña").getValue().toString();
                    if(pass.toString().equals(password)){
                        cambiar();
                    }else{
                        Toast.makeText(LoginAM.this, "Usuario o contraseña incorrecto", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(LoginAM.this, "Usuario o contraseña incorrecto", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }

    public void cambiar(){
        Intent siguiente = new Intent(this, ProfileAMActivity.class);
        siguiente.putExtra("cedula",mEditTextEmail.getText().toString());
        startActivity(siguiente);
    }


}