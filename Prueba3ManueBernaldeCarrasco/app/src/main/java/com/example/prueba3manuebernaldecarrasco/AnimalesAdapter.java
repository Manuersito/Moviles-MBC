package com.example.prueba3manuebernaldecarrasco;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class AnimalesAdapter extends ArrayAdapter<Animales> {

    private int resourceLayout;
    private Context mContext;

    public AnimalesAdapter(Context context, int resource, List<Animales> items) {
        super(context, resource, items);
        this.resourceLayout = resource;
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        // Inflar la vista si es nula
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            view = inflater.inflate(resourceLayout, parent, false);
        }

        // Obtener el objeto Animal en la posición actual
        Animales animal = getItem(position);

        if (animal != null) {
            // Encontrar las vistas en entrada.xml
            ImageView imageView = view.findViewById(R.id.imageView4);
            TextView nombreTextView = view.findViewById(R.id.textView);
            TextView descripcionTextView = view.findViewById(R.id.textView2);

            // Asignar los valores a las vistas
            nombreTextView.setText(animal.getNombre());
            descripcionTextView.setText(animal.getDescripcion());

            // Asignar la imagen al ImageView
            imageView.setImageResource(animal.portadaResId);
        }

        return view;
    }
}
