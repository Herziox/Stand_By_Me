package com.example.standbyme;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.standbyme.databinding.ActivityMapsBinding;
import com.example.standbyme.model.AdultoMayor;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private static final int LOCATION_REQUEST_CODE = 1;
    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private LocationManager locationManager ;
    private Spinner mSpinnerAbuelitos;
    private Button confirmarButton;
    private Marker markerPosition;
    private Circle circle;
    private double longitudSeleccionda = -0.210146, latitudSeleccionda = -78.490165, latitudSelecciondaReal, longuitudSelecciondaReal;
    private String uidSeleccionado;
    private int radioSeleccionda = 40;
    private AdultoMayor adultoMayorSelected;


    private ArrayList<Marker> tmpRealTimeMarker= new ArrayList<>();
    private ArrayList<Marker> realTimeMarker= new ArrayList<>();

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
        inicializarFirebase();
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

        // Controles UI
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Mostrar diálogo explicativo
            } else {
                // Solicitar permiso
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        LOCATION_REQUEST_CODE);
            }
        }
        // Marcador
        LatLng position = new LatLng(latitudSeleccionda, longitudSeleccionda);
        markerPosition = googleMap.addMarker(new MarkerOptions()
                .position(position)
                .title("Posición")
                .draggable(true)
        );
        LatLng center = new LatLng( latitudSeleccionda, longitudSeleccionda);
        int radius = radioSeleccionda;
        CircleOptions circleOptions = new CircleOptions()
                .center(center)
                .radius(radius)
                .strokeColor(Color.parseColor("#0D47A1"))
                .strokeWidth(4)
                .fillColor(Color.argb(32, 33, 150, 243));
        // Añadir círculo
        circle = mMap.addCircle(circleOptions);
        //Añadir el spiner para seleccionar el adulto mayor
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mSpinnerAbuelitos = findViewById(R.id.spinnerAbuelitos);
        final List<AdultoMayor> abuelitos = new ArrayList<>();
        databaseReference.child("AdultoMayor1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                abuelitos.clear();
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                        AdultoMayor am = dataSnapshot.getValue(AdultoMayor.class);
                        abuelitos.add(am);
                    }
                    ArrayAdapter<AdultoMayor> arrayAdapter = new ArrayAdapter<>(MapsActivity.this, android.R.layout.simple_dropdown_item_1line, abuelitos);
                    mSpinnerAbuelitos.setAdapter(arrayAdapter);
                    mSpinnerAbuelitos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            adultoMayorSelected = (AdultoMayor) adapterView.getItemAtPosition(i);
                            latitudSeleccionda = Double.parseDouble(adultoMayorSelected.getLatitud());
                            longitudSeleccionda = Double.parseDouble(adultoMayorSelected.getLongitud());
                            radioSeleccionda = Integer.parseInt(adultoMayorSelected.getRangoDeCirculacion());
                            latitudSelecciondaReal = Double.parseDouble(adultoMayorSelected.getLatitudReal());
                            longuitudSelecciondaReal = Double.parseDouble(adultoMayorSelected.getLongitudReal());

                            LatLng nuevaPosicionReal = new LatLng(latitudSelecciondaReal, longuitudSelecciondaReal);
                            markerPosition.setPosition(nuevaPosicionReal);
                            // Cámara
                            googleMap.moveCamera(CameraUpdateFactory.newLatLng(position));
                            //Añadir circulo
                            LatLng center = new LatLng( latitudSeleccionda, longitudSeleccionda);
                            circle.setCenter(center);
                            //(Opcional) Actualiza el objetivo de la cámara:
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(center, 17));
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        mFirebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == LOCATION_REQUEST_CODE) {
            // ¿Permisos asignados?
            if (permissions.length > 0 &&
                    permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION) &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
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
                mMap.setMyLocationEnabled(true);
            } else {
                Toast.makeText(this, "Error de permisos", Toast.LENGTH_LONG).show();
            }

        }
    }

}