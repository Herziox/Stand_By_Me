package com.example.standbyme;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileAMActivity extends AppCompatActivity {

    private TextView text1;
    private String cedula;
    private FusedLocationProviderClient mFusedLocationClient;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_amactivity);
        cedula = getIntent().getStringExtra("cedula");
        text1= (TextView) findViewById(R.id.text1);
        text1.setText(cedula);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mDatabase= FirebaseDatabase.getInstance().getReference();
        subirLatLon();
    }

    private void subirLatLon() {


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        //mDatabase.child("AdultoMayor1").child(cedula).child("long_real").setValue(location.getLongitude());
                        //mDatabase.child("AdultoMayor1").child(cedula).child("lat_real").setValue(location.getLatitude());
                        if (location != null) {
                            Log.e("Latitud",+location.getLatitude()+"Longitud:"+location.getLongitude());
                            text1.setText("Ubicación enviada");
                            mDatabase.child("AdultoMayor1").child(cedula).child("long_real").setValue(location.getLongitude());
                            mDatabase.child("AdultoMayor1").child(cedula).child("lat_real").setValue(location.getLatitude());
                        }
                    }
                });
    }
}