package com.example.myapplication;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AddCocheActivity extends Activity {

    private EditText etNombreCoche, etDescripcionCoche;
    private RatingBar ratingBar;
    private DatePicker datePicker;
    private ImageView imgCoche;
    private Button btnAddCoche;
    private ArrayList<Coches> listaCoches; // Esta lista viene de tu actividad principal

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_coche);

        // Inicializamos las vistas
        etNombreCoche = findViewById(R.id.etNombreCoche);
        etDescripcionCoche = findViewById(R.id.etDescripcionCoche);
        ratingBar = findViewById(R.id.ratingBar);
        datePicker = findViewById(R.id.datePicker);
        imgCoche = findViewById(R.id.imgCoche);
        btnAddCoche = findViewById(R.id.btnAddCoche);

        // Cargar la lista de coches (esto puede venir de tu actividad principal)
        listaCoches = new ArrayList<>(); // O obtenerla de tu actividad principal si es necesario.

        // Seteamos el comportamiento del botón
        btnAddCoche.setOnClickListener(v -> {
            // Obtener los datos del formulario
            String nombre = etNombreCoche.getText().toString();
            String descripcion = etDescripcionCoche.getText().toString();
            float valoracion = ratingBar.getRating();
            int year = datePicker.getYear();
            int month = datePicker.getMonth();
            int day = datePicker.getDayOfMonth();

            // Convertir la fecha a formato String
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, day);
            Date fecha = calendar.getTime();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String fechaString = sdf.format(fecha);

            // Crear el coche nuevo
            Coches nuevoCoche = new Coches(nombre, descripcion, R.drawable.bmw, false, valoracion, "https://example.com", fechaString);

            // Devolver el coche creado a la actividad principal
            // En AddCocheActivity, después de agregar el coche, devuelves el coche a MainActivity.
            Intent resultIntent = new Intent();
            resultIntent.putExtra("coche", nuevoCoche);  // cocheNuevo es el coche que se acaba de agregar
            setResult(RESULT_OK, resultIntent);
            finish();

        });
    }
}
