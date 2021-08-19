package com.example.standbyme;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int var1 = 10, var2= 8, var3= 5, prom=0;
        prom = (var1+var2+var3)/3;

        if (prom >= 7){
            Toast.makeText(this, "Aprobado", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Reprobado", Toast.LENGTH_SHORT).show();
        }


    }


}