package com.example.manuelbernaldezcarrascoprueba4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class PintoresAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Pintores> pintoresList;

    public PintoresAdapter(Context context, ArrayList<Pintores> pintoresList) {
        this.context = context;
        this.pintoresList = pintoresList;
    }

    @Override
    public int getCount() {
        return pintoresList.size();
    }

    @Override
    public Object getItem(int position) {
        return pintoresList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.pintor_item, parent, false);
        }

        ImageView imageView = convertView.findViewById(R.id.imageView);
        TextView textViewName = convertView.findViewById(R.id.textViewName);
        TextView textViewDescription = convertView.findViewById(R.id.textViewDescription);

        Pintores pintor = pintoresList.get(position);
        imageView.setImageResource(pintor.getIdImagen());
        textViewName.setText(pintor.getNombre());
        textViewDescription.setText(pintor.getDescripcion());

        return convertView;
    }
}
