package com.example.standbyme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class registro_Localizacion extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener {
    private static final String TAG = "registro_Localizacion";
    private GoogleMap mMap;
    private LocationManager ubicacion;
    private GeofencingClient geofencingClient;
    private GeoferenceHelper geoferenceHelper;
    private int FINE_LOCATION_ACCES_REQUEST_CODE = 10001;
    private int BACKGROUND_LOCATION_ACCES_REQUEST_CODE = 10002;
    private float GEOFENCE_RADIO = 200;
    private String geofenceID = "SOME_GEOFENCE_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_localizacion);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        geofencingClient = LocationServices.getGeofencingClient(this);
        geoferenceHelper = new GeoferenceHelper(this);
    }

    private void enableUserLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                //Nosotros necesitamos mostrar al usuario un dialogo para desplegar porque el permiso es necesario
                //y después preguntar por el permiso
                ActivityCompat.requestPermissions(this, new String[]
                        {Manifest.permission.ACCESS_FINE_LOCATION}, FINE_LOCATION_ACCES_REQUEST_CODE);
            } else {
                ActivityCompat.requestPermissions(this, new String[]
                        {Manifest.permission.ACCESS_FINE_LOCATION}, FINE_LOCATION_ACCES_REQUEST_CODE);
            }

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @org.jetbrains.annotations.NotNull String[] permissions, @NonNull @org.jetbrains.annotations.NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == FINE_LOCATION_ACCES_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                mMap.setMyLocationEnabled(true);
            } else {

            }
        }
        if (requestCode == BACKGROUND_LOCATION_ACCES_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "You can add geofences...",Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Background location access is necessary for geofences" +
                        "to trigger", Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public void onMapReady(
            GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
        // Add a marker in Sydney and move the camera
        LatLng eiffel = new LatLng(48.8589, 2.29365);
        mMap.addMarker(new MarkerOptions()
                .position(eiffel));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(eiffel, 16));
        enableUserLocation();
        mMap.setOnMapLongClickListener(this);
    }

    @Override
    public void onMapLongClick(@NonNull @NotNull LatLng latLng) {
        if(Build.VERSION.SDK_INT >= 29){
            if(ContextCompat.checkSelfPermission(this, Manifest.permission
                    .ACCESS_BACKGROUND_LOCATION ) ==PackageManager.PERMISSION_GRANTED){
                handleMapLongClick(latLng);
            }else{
                if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.
                        permission.ACCESS_BACKGROUND_LOCATION)){
                    ActivityCompat.requestPermissions(this, new String[] {Manifest
                            .permission.ACCESS_BACKGROUND_LOCATION}, BACKGROUND_LOCATION_ACCES_REQUEST_CODE);}
                else{
                    ActivityCompat.requestPermissions(this, new String[] {Manifest
                            .permission.ACCESS_BACKGROUND_LOCATION}, BACKGROUND_LOCATION_ACCES_REQUEST_CODE);

                }
            }
        } else{
            handleMapLongClick(latLng);
        }
    }

    private void handleMapLongClick(LatLng latLng){
        mMap.clear();
        addMarker(latLng);
        addCircle(latLng, GEOFENCE_RADIO);
        addGeofence(latLng, GEOFENCE_RADIO);
    }

    private void addGeofence(LatLng latLng, float radius) {
        Geofence geofence = geoferenceHelper.getGeofence(geofenceID, latLng, radius, Geofence.GEOFENCE_TRANSITION_DWELL | Geofence.GEOFENCE_TRANSITION_ENTER
                | Geofence.GEOFENCE_TRANSITION_EXIT);
        GeofencingRequest geofencingRequest = geoferenceHelper.getGeofencingRequest(geofence);
        PendingIntent pendingIntent = geoferenceHelper.getPendingIntent();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
             return;
        }
        geofencingClient.addGeofences(geofencingRequest, pendingIntent).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d(TAG, "enSuccess: Geofence Added...");
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        String errorMessage = geoferenceHelper.getErrorString(e);
                        Log.d(TAG, "onFailure: " + errorMessage);
                    }
                });
    }


    private void addMarker(LatLng latLng){
        MarkerOptions markerOptions = new MarkerOptions().position(latLng);
        mMap.addMarker(markerOptions);
    }

    private void addCircle(LatLng latLng, float radius){
        CircleOptions circleOptions = new CircleOptions();
        circleOptions.center(latLng);
        circleOptions.radius(radius);
        circleOptions.strokeColor(Color.argb(225,255,0,0));
        circleOptions.fillColor(Color.argb(64,255,0,0));
        circleOptions.strokeWidth(4);
        mMap.addCircle(circleOptions);
    }

}