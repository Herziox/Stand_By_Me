package com.example.standbyme.model;

import com.google.firebase.database.FirebaseDatabase;

public class MyFirebaseApp extends android.app.Application{
    @Override
    public void onCreate() {
        super.onCreate();
        //Añadido la persistencia de manera global
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
