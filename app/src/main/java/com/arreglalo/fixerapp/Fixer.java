package com.arreglalo.fixerapp;

import java.io.Serializable;

public class Fixer implements Serializable {
    private int id;
    private String nombre;
    private String telefono;
    private String[] trabajos = new String[5];

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    private double calificacion;
    private String correo;

    public Fixer(int id, String nombre, String telefono, String[] trabajos) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.trabajos = trabajos;

    }
    public Fixer(){

    }
    public void aceptSer(){

    }
    public void desplazarse(){

    }
    public void finServicio(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String[] getTrabajos() {
        return trabajos;
    }

    public void setTrabajos(String[] trabajos) {
        this.trabajos = trabajos;
    }

    public double getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(double calificacion) {
        this.calificacion = calificacion;
    }
}
