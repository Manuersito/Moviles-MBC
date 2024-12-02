package com.example.myapplication;

import java.io.Serializable;

public class Coches implements Serializable{
    public String nombre;
    public String descripcion;
    public int portadaResId; // ID del recurso de la imagen
    public boolean encontrado;
    public float valoracion;
    public String web;
    public String fechaEncontrado;

    public Coches(String nombre, String descripcion, int portadaResId, boolean encontrado, float valoracion, String web, String fechaEncontrado) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.portadaResId = portadaResId;
        this.encontrado = encontrado;
        this.valoracion = valoracion;
        this.web = web;
        this.fechaEncontrado = fechaEncontrado;
    }

    // Getters y setters
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public int getPortadaResId() { return portadaResId; }
    public boolean getEncontrado() { return encontrado; }
    public float getValoracion() { return valoracion; }
    public String getWeb() { return web; }
    public String getFechaEncontrado() { return fechaEncontrado; }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setPortadaResId(int portadaResId) {
        this.portadaResId = portadaResId;
    }

    public void setEncontrado(boolean encontrado) {
        this.encontrado = encontrado;
    }

    public void setValoracion(float valoracion) {
        this.valoracion = valoracion;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public void setFechaEncontrado(String fechaEncontrado) {
        this.fechaEncontrado = fechaEncontrado;
    }
}
