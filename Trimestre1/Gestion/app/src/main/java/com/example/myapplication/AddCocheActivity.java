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
import java.util.Calendar;
import java.util.Date;

public class AddCocheActivity extends Activity {

    private EditText etNombreCoche, etDescripcionCoche;
    private RatingBar ratingBar;
    private DatePicker datePicker;
    private ImageView imgCoche;
    private Button btnAddCoche;
    private DBHelper dbHelper; // Agregamos el DBHelper para manejar la base de datos

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

        // Inicializamos DBHelper
        dbHelper = new DBHelper(this);

        // En AddCocheActivity
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

            // Insertar el coche en la base de datos
            long id = dbHelper.addCoche(nuevoCoche);

            if (id != -1) {
                // Coche insertado con éxito, mostrar mensaje y regresar
                Toast.makeText(AddCocheActivity.this, "Coche agregado con éxito", Toast.LENGTH_SHORT).show();

                // Pasar el coche agregado a la actividad principal
                Intent resultIntent = new Intent();
                resultIntent.putExtra("coche", nuevoCoche);  // Pasar el coche recién creado
                setResult(RESULT_OK, resultIntent);
                finish();
            } else {
                // Error al insertar el coche
                Toast.makeText(AddCocheActivity.this, "Error al agregar coche", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
