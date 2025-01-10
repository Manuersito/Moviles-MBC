package com.example.actividad9_2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Fragment2 extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Crear el diseño dinámico del fragmento
        TextView textView = new TextView(getActivity());
        textView.setText("Fragmento 2");
        textView.setTextSize(24);
        textView.setBackgroundColor(0xFF0000FF); // Azul
        textView.setTextColor(0xFFFFFFFF); // Blanco
        textView.setGravity(View.TEXT_ALIGNMENT_CENTER);
        return textView;
    }
}
