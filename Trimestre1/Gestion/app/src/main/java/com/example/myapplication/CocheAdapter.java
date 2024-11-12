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

public class CocheAdapter extends RecyclerView.Adapter<CocheAdapter.VideojuegoViewHolder> {

    private List<Coches> coches;

    public CocheAdapter(List<Coches> coches) {
        this.coches = coches;
    }

    @NonNull
    @Override
    public VideojuegoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.coche_item, parent, false);
        return new VideojuegoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideojuegoViewHolder holder, int position) {
        Coches coches = this.coches.get(position);
        holder.tvNombre.setText(coches.getNombre());
        holder.tvDescripcion.setText(coches.getDescripcion());
        holder.ratingBar.setRating(coches.getValoracion());
        holder.imgPortada.setImageResource(coches.getPortadaResId());
        holder.tvTelefono.setText(coches.getTelefono());
        holder.tvWeb.setText(coches.getWeb());
        holder.tvComprado.setChecked(coches.getComprado());
        holder.tvComprado.setOnClickListener(v -> {
            boolean isChecked = holder.tvComprado.isChecked();
            coches.setComprado(isChecked);
        });
    }

    @Override
    public int getItemCount() {
        return coches.size();
    }

    static class VideojuegoViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre;
        TextView tvDescripcion;
        RatingBar ratingBar;
        ImageView imgPortada;
        TextView tvTelefono;
        TextView tvWeb;
        RadioButton tvComprado;

        VideojuegoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvDescripcion = itemView.findViewById(R.id.tvDescripcion);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            imgPortada = itemView.findViewById(R.id.imgPortada);
            tvTelefono = itemView.findViewById(R.id.tvTelefono);
            tvWeb = itemView.findViewById(R.id.tvWeb);
            tvComprado = itemView.findViewById(R.id.tvComprado);
        }
    }
}
