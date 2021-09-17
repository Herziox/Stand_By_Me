package com.example.standbyme;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.standbyme.databinding.ActivityMapsBinding;
import com.example.standbyme.model.CoordenadasGPS;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private LocationManager locationManager ;
    private Button confirmarButton;

    private FirebaseAuth mFirebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION
            },1000);
        }
        locationManager =(LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location loc = locationManager .getLastKnownLocation(LocationManager.GPS_PROVIDER);

        mMap.setMyLocationEnabled(true);

        // Add a marker in Sydney and move the camera
        if(locationManager != null){
            LatLng sydney = new LatLng(loc.getLatitude(), loc.getLongitude());
            mMap.addMarker(new MarkerOptions().position(sydney).title("Ubicación Actual"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        }
/*
        confirmarButton = (Button)findViewById(R.id.btnConfirmar);
        inicializarFirebase();
        confirmarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CoordenadasGPS gps = new CoordenadasGPS();
                gps.setLatitud(String.valueOf(loc.getLatitude()));
                gps.setLonguitud(String.valueOf((loc.getLongitude())));
                databaseReference.child("CoordenadasGPS").setValue(gps);
                finish();
            }
        });*/
    }
    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        mFirebaseAuth = FirebaseAuth.getInstance();
    }
}