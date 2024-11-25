package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CocheAdapter extends RecyclerView.Adapter<CocheAdapter.CochesViewHolder> {

    private List<Coches> coches;

    public CocheAdapter(List<Coches> coches) {
        this.coches = coches;
    }

    @NonNull
    @Override
    public CochesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.coche_item, parent, false);
        return new CochesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CochesViewHolder holder, int position) {
        Coches coches = this.coches.get(position);
        holder.tvNombre.setText(coches.getNombre());
        holder.tvDescripcion.setText(coches.getDescripcion());
        holder.ratingBar.setRating(coches.getValoracion());
        holder.imgPortada.setImageResource(coches.getPortadaResId());
        holder.tvTelefono.setText(coches.getTelefono());
        holder.tvWeb.setText(coches.getWeb());
        holder.rbEncontrado.setChecked(coches.getEncontrado());
        holder.rbEncontrado.setOnClickListener(v -> {
            boolean isChecked = holder.rbEncontrado.isChecked();
            coches.setEncontrado(isChecked);
        });
    }

    @Override
    public int getItemCount() {
        return coches.size();
    }

    static class CochesViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre;
        TextView tvDescripcion;
        RatingBar ratingBar;
        ImageView imgPortada;
        TextView tvTelefono;
        TextView tvWeb;
        RadioButton rbEncontrado;

        CochesViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvDescripcion = itemView.findViewById(R.id.tvDescripcion);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            imgPortada = itemView.findViewById(R.id.imgPortada);
            tvTelefono = itemView.findViewById(R.id.tvTelefono);
            tvWeb = itemView.findViewById(R.id.tvWeb);
            rbEncontrado = itemView.findViewById(R.id.rbEncontrado);
            rbEncontrado.setEnabled(false);
        }
    }
}
