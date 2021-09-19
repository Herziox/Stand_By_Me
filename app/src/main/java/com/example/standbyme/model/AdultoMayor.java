package com.example.standbyme.model;

public class AdultoMayor {

    private String uid;
    private String nombre;
    private String apellido;
    private String telefono;
    private String cedula;
    private String fechaNacimiento;
    private String contraseña;
    private String observaciones;
    private String pkIDPersonaEncargada;
    private String rangoDeCirculacion;
    private String latitud;
    private String longitud;

    public AdultoMayor() {
    }

    public AdultoMayor(String id, String nombre) {
        this.uid = id;
        this.nombre = nombre;
    }

    public String getPkIDPersonaEncargada() {
        return pkIDPersonaEncargada;
    }

    public void setPkIDPersonaEncargada(String pkIDPersonaEncargada) {
        this.pkIDPersonaEncargada = pkIDPersonaEncargada;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getRangoDeCirculacion() {
        return rangoDeCirculacion;
    }

    public void setRangoDeCirculacion(String rangoDeCirculacion) {
        this.rangoDeCirculacion = rangoDeCirculacion;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    @Override
    public String toString() {
        return nombre+" "+apellido+" - "+cedula;
    }
}
