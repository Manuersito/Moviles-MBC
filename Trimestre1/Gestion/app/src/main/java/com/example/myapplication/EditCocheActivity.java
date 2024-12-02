package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;
import android.widget.DatePicker;

import androidx.appcompat.app.AppCompatActivity;

public class EditCocheActivity extends AppCompatActivity {

    private EditText editNombre, editDescripcion, editWeb;
    private RatingBar ratingBar;
    private DatePicker datePicker;
    private Button btnGuardar;
    private Coches coche;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_coche);

        // Inicializa los componentes
        editNombre = findViewById(R.id.input_nombre);
        editDescripcion = findViewById(R.id.input_descripcion);
        editWeb = findViewById(R.id.input_web);
        datePicker = findViewById(R.id.input_fecha);
        ratingBar = findViewById(R.id.input_valoracion);
        btnGuardar = findViewById(R.id.btn_guardar);

        // Recuperar el coche y la posición desde el Intent
        coche = (Coches) getIntent().getSerializableExtra("coche");
        position = getIntent().getIntExtra("position", -1);

        // Rellenar los campos con los datos del coche
        editNombre.setText(coche.getNombre());
        editDescripcion.setText(coche.getDescripcion());
        editWeb.setText(coche.getWeb());

        // Fecha de encontrado: muestra la fecha del coche
        String[] dateParts = coche.getFechaEncontrado().split("-");
        int year = Integer.parseInt(dateParts[0]);
        int month = Integer.parseInt(dateParts[1]) - 1; // Los meses son 0-indexed
        int day = Integer.parseInt(dateParts[2]);
        datePicker.updateDate(year, month, day);

        ratingBar.setRating(coche.getValoracion());

        // Guardar los cambios
        btnGuardar.setOnClickListener(v -> {
            coche.setNombre(editNombre.getText().toString());
            coche.setDescripcion(editDescripcion.getText().toString());
            coche.setWeb(editWeb.getText().toString());

            // Obtener la fecha seleccionada en el DatePicker
            int selectedDay = datePicker.getDayOfMonth();
            int selectedMonth = datePicker.getMonth() + 1; // Meses en DatePicker son 0-indexed
            int selectedYear = datePicker.getYear();
            coche.setFechaEncontrado(selectedYear + "-" + selectedMonth + "-" + selectedDay);

            coche.setValoracion(ratingBar.getRating());

            // Crear el Intent de resultado
            Intent resultIntent = new Intent();
            resultIntent.putExtra("coche", coche); // Pasa el coche editado
            resultIntent.putExtra("position", position); // Pasa la posición
            setResult(RESULT_OK, resultIntent); // Establece el resultado de la actividad
            finish(); // Cierra la actividad
        });
    }
}
