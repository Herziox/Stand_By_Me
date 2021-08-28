package com.example.standbyme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class ProfileActitvity extends AppCompatActivity {

    private Button mButtonSignOut;
    private FirebaseAuth mAu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_actitvity);

        mButtonSignOut = (Button) findViewById(R.id.btnsignout);
        mButtonSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAu.signOut();
                startActivity(new Intent(ProfileActitvity.this, MainActivity.class));
                finish();
            }
        });
    }

}