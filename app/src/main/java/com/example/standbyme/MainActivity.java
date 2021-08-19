package com.example.standbyme;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLDataException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public Connection connectionclass(){
        StrictMode.ThreadPolicy policy= new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
        String ConnectionURL =null;
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            ConnectionURL="jdbc:jtds:sqlserver://serverappocalizacion.database.windows.net:1433;DatabaseName=AppLocalizacion;user=administrador@serverappocalizacion;password=app2021A;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
            connection = DriverManager.getConnection(ConnectionURL);
        }
        catch (SQLDataException se){
            Log.e("error here 1: ",se.getMessage());
        }
        catch(ClassNotFoundException e){
            Log.e("error here2: ",e.getMessage());
        }
        catch(Exception e){
            Log.e("error here 3:",e.getMessage());
        }
        return connection;
    }

}