package com.example.prueba3manuebernaldecarrasco;

public class Animales {
    private String nombre;
    private String descripcion;
    public int portadaResId;


    // Constructor con parámetros
    public Animales(String nombre, String descripcion,int portadaResId) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.portadaResId = portadaResId;
    }

    // Métodos getters y setters
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

    // Método toString para representar el objeto como una cadena
    @Override
    public String toString() {
        return nombre;
    }
}
