package com.example.manuelbernaldezpruebatema9;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class Entrada extends Fragment {

    private OnTextSendListener etListener;

    public interface OnTextSendListener {
        void textoEnviado(String text);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnTextSendListener) {
            etListener = (OnTextSendListener) context;
        } else {
            throw new RuntimeException(context.toString());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_entrada, container, false);

        EditText editText = view.findViewById(R.id.editTextEntrada);
        Button button = view.findViewById(R.id.buttonEnviar);

        button.setOnClickListener(v -> {
            String inputText = editText.getText().toString();
            if (etListener != null) {
                etListener.textoEnviado(inputText);
            }
        });

        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        etListener = null;
    }
}
