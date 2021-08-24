package com.example.standbyme;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private EditText et1, et2;
    private TextView tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //La clase R es una clase de comunicacion entre la parte logica y grafica de la app
        et1 = (EditText) findViewById(R.id.txt_num1);
        et2 = (EditText) findViewById(R.id.txt_num2);
        tv1 = (TextView) findViewById(R.id.txt_resultado);
    }

    //Metodo de suma
    public void sumar(View view){
        String val1 = et1.getText().toString();
        String val2 = et2.getText().toString();
        int num1 = Integer.parseInt(val1);
        int num2 = Integer.parseInt(val2);
        int sum = num1 + num2;
        String result = String.valueOf(sum);
        tv1.setText(result);
    }


}