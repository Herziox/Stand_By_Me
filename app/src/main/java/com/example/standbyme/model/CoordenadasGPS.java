package com.example.standbyme.model;

public class CoordenadasGPS {
    private String Uid;
    private String latitud;
    private String longuitud;

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLonguitud() {
        return longuitud;
    }

    public void setLonguitud(String longuitud) {
        this.longuitud = longuitud;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    @Override
    public String toString() {
        return "CoordenadasGPS = " +
                "latitud='" + latitud + '\'' +
                ", longuitud='" + longuitud + '\'';
    }
}
