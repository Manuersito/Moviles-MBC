package com.example.manuelbernaldezpruebatema9;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements Entrada.OnTextSendListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void textoEnviado(String text) {
        //Encuentra el fragmento verde (DisplayFragment)
        Salida displayFragment = (Salida) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_salida);

        //Actualiza el texto del DisplayFragment
        if (displayFragment != null) {
            displayFragment.updateText(text);
        }
    }
}