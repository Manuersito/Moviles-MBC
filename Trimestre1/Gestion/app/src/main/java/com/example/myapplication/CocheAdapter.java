package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.BaseAdapter;

import java.util.List;

public class CocheAdapter extends BaseAdapter {

    private final Context context;
    private final List<Coches> coches;

    public CocheAdapter(Context context, List<Coches> coches) {
        this.context = context;
        this.coches = coches;
    }

    @Override
    public int getCount() {
        return coches.size();
    }

    @Override
    public Object getItem(int position) {
        return coches.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.coche_item, parent, false);
        }

        Coches coche = coches.get(position);

        ImageView imgPortada = convertView.findViewById(R.id.imgPortada);
        TextView tvNombre = convertView.findViewById(R.id.tvNombre);
        RatingBar ratingBar = convertView.findViewById(R.id.ratingBar);
        TextView tvDescripcion = convertView.findViewById(R.id.tvDescripcion);
        TextView tvTelefono = convertView.findViewById(R.id.tvTelefono);
        TextView tvWeb = convertView.findViewById(R.id.tvWeb);
        RadioButton rbEncontrado = convertView.findViewById(R.id.rbEncontrado);
        View linearLayout = convertView.findViewById(R.id.linearLayout);  // Cambié a linearLayout

        imgPortada.setImageResource(coche.getPortadaResId());
        tvNombre.setText(coche.getNombre());
        ratingBar.setRating(coche.getValoracion());
        tvDescripcion.setText(coche.getDescripcion());
        tvTelefono.setText(coche.getFechaEncontrado());
        tvWeb.setText(coche.getWeb());
        rbEncontrado.setChecked(coche.getEncontrado());

        rbEncontrado.setOnClickListener(v -> coche.setEncontrado(rbEncontrado.isChecked()));

        // Agregar el Listener para el clic largo en el LinearLayout
        linearLayout.setOnLongClickListener(v -> {
            // Aquí puedes invocar el menú contextual
            v.showContextMenu();
            return true; // Devuelve true para indicar que el clic largo ha sido manejado
        });


        return convertView;
    }
}
