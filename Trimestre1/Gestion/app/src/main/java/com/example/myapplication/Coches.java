package com.example.myapplication;

import java.io.Serializable;

public class Coches implements Serializable {
    private int id;
    public String nombre;
    public String descripcion;
    public int portadaResId;
    public boolean encontrado;
    public float valoracion;
    public String web;
    public String fechaEncontrado;

    // Constructor
    public Coches(int id, String nombre, String descripcion, int portadaResId, boolean encontrado, float valoracion, String web, String fechaEncontrado) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.portadaResId = portadaResId;
        this.encontrado = encontrado;
        this.valoracion = valoracion;
        this.web = web;
        this.fechaEncontrado = fechaEncontrado;
    }

    // Constructor sin id (para insertar en la base de datos)
    public Coches(String nombre, String descripcion, int portadaResId, boolean encontrado, float valoracion, String web, String fechaEncontrado) {
        this.id = -1;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.portadaResId = portadaResId;
        this.encontrado = encontrado;
        this.valoracion = valoracion;
        this.web = web;
        this.fechaEncontrado = fechaEncontrado;
    }

    public Coches() {

    }

    // Getters y setters
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getPortadaResId() {
        return portadaResId;
    }

    public void setPortadaResId(int portadaResId) {
        this.portadaResId = portadaResId;
    }

    public boolean getEncontrado() {
        return encontrado;
    }

    public void setEncontrado(boolean encontrado) {
        this.encontrado = encontrado;
    }

    public float getValoracion() {
        return valoracion;
    }

    public void setValoracion(float valoracion) {
        this.valoracion = valoracion;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getFechaEncontrado() {
        return fechaEncontrado;
    }

    public void setFechaEncontrado(String fechaEncontrado) {
        this.fechaEncontrado = fechaEncontrado;
    }
}
