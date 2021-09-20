package com.example.standbyme;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginPEActivity extends AppCompatActivity {
    private EditText mEditTextEmail, mEditTextPassword;
    private Button mButtonLogin, mButtonOlvidadoPassword;

    private String email = "";
    private String password = "";

    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mFirebaseAuth = FirebaseAuth.getInstance();

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
                    Toast.makeText(LoginPEActivity.this, "Complete los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mButtonOlvidadoPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginPEActivity.this, ResetPassword.class));
            }
        });

    }

    private void loginUser() {
        mFirebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    startActivity(new Intent(LoginPEActivity.this,ProfilePEActitvity.class));
                    finish();
                }else{
                    Toast.makeText(LoginPEActivity.this, "No se pudo iniciar sesión. Por favor, verifique los datos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}