package com.example.standbyme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPassword extends AppCompatActivity {

    private EditText mEditTextEmail;
    private Button mButtonRessetPassword;

    private String email= "";

    private FirebaseAuth mFirebaseAuth;

    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        mFirebaseAuth = FirebaseAuth.getInstance();

        mDialog = new ProgressDialog(this);

        mEditTextEmail = (EditText) findViewById(R.id.editTextEmail_ResetPassword);

        mButtonRessetPassword = (Button) findViewById(R.id.btnResetPassword);

        mButtonRessetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                email = mEditTextEmail.getText().toString();

                if (!email.isEmpty()){
                    mDialog.setMessage("Espera un momento...");
                    mDialog.setCanceledOnTouchOutside(false);
                    mDialog.show();
                    resetPassword();
                }else{
                    Toast.makeText(ResetPassword.this, "Debe ingresar el email", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void resetPassword() {
        mFirebaseAuth.setLanguageCode("es");
        mFirebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(ResetPassword.this, "No se pudo enviar el correo para restablecer la contraseña", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(ResetPassword.this, "No se pudo enviar el correo para restablecer la contraseña", Toast.LENGTH_SHORT).show();
                }
                mDialog.dismiss();
            }
        });

    }
}