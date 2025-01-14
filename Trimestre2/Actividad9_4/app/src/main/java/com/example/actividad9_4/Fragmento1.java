package com.example.actividad9_4;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;

public class Fragmento1 extends ListFragment {

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Configuración del adaptador
        setListAdapter(new Adaptador(requireActivity(), R.layout.layout_listado, Contenido.ENT_LISTA) {
            @Override
            public void onEntrada(Object entrada, View view) {
                // Configuramos el texto
                TextView texto_superior_entrada = view.findViewById(R.id.textotitulo);
                texto_superior_entrada.setText(((Contenido.Lista_entrada) entrada).textoEncima);

                // Configuramos la imagen
                ImageView imagen_entrada = view.findViewById(R.id.imagenlista);
                imagen_entrada.setImageResource(((Contenido.Lista_entrada) entrada).idImagen);
            }
        });
    }
}
