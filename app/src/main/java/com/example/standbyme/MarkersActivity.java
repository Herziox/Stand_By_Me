package com.example.standbyme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MarkersActivity extends AppCompatActivity
        implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener{
    private Marker markerPais;

    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        return false;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        // Markers
        LatLng colombia = new LatLng(4.6,-74.08);
        markerPais = googleMap.addMarker(new MarkerOptions()
                .position(colombia)
                .title("Colombia")
        );

        // Cámara
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(colombia));

        // Eventos
        googleMap.setOnMarkerClickListener(this);
    }
}
