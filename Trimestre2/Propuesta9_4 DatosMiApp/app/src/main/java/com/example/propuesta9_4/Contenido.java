package com.example.propuesta9_4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Contenido {

    public static ArrayList<Lista_entrada> ENT_LISTA = new ArrayList<Lista_entrada>();

    public static Map<String, Lista_entrada> ENT_LISTA_HASHMAP = new HashMap<String, Lista_entrada>();

    public static class Lista_entrada{
        public String id;
        public int idImagen;
        public String textoEncima;
        public String textoDebajo;

        public Lista_entrada(String id, int idImagen, String textoEncima, String textoDebajo) {
            this.id = id;
            this.idImagen = idImagen;
            this.textoEncima = textoEncima;
            this.textoDebajo = textoDebajo;
        }

    }

    static{
        ponerEntrada(new Lista_entrada("0", R.drawable.audi_sport_quattro, "Audi quattro", "El Audi Quattro es la versión tope de gama del Audi GT Coupe que a su vez era la variante deportiva del Audi 80 B2. Esta versión es un automóvil deportivo producido por el fabricante alemán Audi entre 1980 y 1991. Fue el primer automóvil en usar la tracción a las cuatro ruedas tras el Jensen FF de 1966 y uno de los automóviles de rally más exitosos de la historia."));

        ponerEntrada(new Lista_entrada("1", R.drawable.bmw, "BMW M3", "El BMW M3 es la versión deportiva del BMW Serie 3, producido por el fabricante de automóviles alemán BMW.\n" +
                "\n" +
                "El primer M3 fue basado en el Serie E30 que se comercializó en el año 1986 y hasta 2018; cada Serie 3 tuvo su versión M. Desde el E30, BMW ha ido aumentando caballos y cilindros, desde los 4 en línea hasta el E92 con un motor V8. BMW M GmbH anunció en el año 2013 el fin de la producción del M3 Coupé y afirmó que su sucesor será el BMW M4."));

        ponerEntrada(new Lista_entrada("2", R.drawable.ram_trx, "RAM TRX", "La Ram 1500 TRX Final Edition 2024 ofrece la mejor 702 caballos de fuerza y el mejor torque de su clase gracias al notable motor HEMI® V8 6.2L supercargado y una transmisión automática TorqueFlite® de ocho velocidades de alto torque."));

        ponerEntrada(new Lista_entrada("3", R.drawable.toyota_celica, "Toyota celica t18", "La generación siguiente del Celica, la quinta, fue introducida en 1989, para su primer modelo en 1990. Se revisó el diseño, se aumentó el tamaño de las ruedas y los neumáticos, y se le dotó de mayor potencia. Los motores de las versiones GT y GT-S se aumentaron a 2.0 litros, mientras que la versión ST se dotó de un motor de 1.6 litros DOHC 16 válvulas."));

    }

    private static void ponerEntrada(Lista_entrada entrada){
        ENT_LISTA.add(entrada);
        ENT_LISTA_HASHMAP.put(entrada.id, entrada);
    }
}
