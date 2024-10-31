package com.example.prueba2tema5manuelbernaldez;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ej3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ej3);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        String[] versiones = {"APPLE PIE","BANANA BREAD","CUPCAKE","DONUT","ECLAIR","FROYO","GINGERBREAD","HONEYCOMB","ICE CREAM SANDWICH","JELLY BEAN","KITKAT","LOLLIPOP","MARSHMALLOW","NOUGAT","OREO","PIE","ANDROID 10"};
        AutoCompleteTextView texto = (AutoCompleteTextView) findViewById(R.id.editTextText);
        ArrayAdapter<String> adaptador = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, versiones);
        texto.setAdapter(adaptador);
    }


}