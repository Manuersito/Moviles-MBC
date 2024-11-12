package com.example.myapplication;

public class Coches {
    public String nombre;
    public String descripcion;
    public int portadaResId; // ID del recurso de la imagen (en lugar de una URL o Image)
    public boolean comprado;
    public float valoracion;
    public String web;
    public String telefono;

    public Coches(String nombre, String descripcion, int portadaResId, boolean comprado, float valoracion, String web, String telefono) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.portadaResId = portadaResId;
        this.comprado = comprado;
        this.valoracion = valoracion;
        this.web = web;
        this.telefono = telefono;
    }

    // Getters y setters
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public int getPortadaResId() { return portadaResId; }
    public boolean getComprado() { return comprado; }
    public float getValoracion() { return valoracion; }
    public String getWeb() { return web; }
    public String getTelefono() { return telefono; }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setPortadaResId(int portadaResId) {
        this.portadaResId = portadaResId;
    }

    public void setComprado(boolean comprado) {
        this.comprado = comprado;
    }

    public void setValoracion(float valoracion) {
        this.valoracion = valoracion;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
