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
    private int cocheId; // Usamos ID para obtener el coche de la base de datos
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_coche);

        // Inicializamos los componentes
        editNombre = findViewById(R.id.input_nombre);
        editDescripcion = findViewById(R.id.input_descripcion);
        editWeb = findViewById(R.id.input_web);
        datePicker = findViewById(R.id.input_fecha);
        ratingBar = findViewById(R.id.input_valoracion);
        btnGuardar = findViewById(R.id.btn_guardar);

        // Inicializamos DBHelper
        dbHelper = new DBHelper(this);

        cocheId = getIntent().getIntExtra("coche_id", -1);  // Recupera el ID del coche
        if (cocheId == -1) {
            Toast.makeText(EditCocheActivity.this, "ID de coche inválido", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }


        // Obtener el coche desde la base de datos usando el ID
        coche = dbHelper.getCocheById(cocheId);

        if (coche == null) {
            Toast.makeText(EditCocheActivity.this, "Coche no encontrado", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

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

        // Guardar los cambios en la base de datos
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

            // Actualizar el coche en la base de datos
            int success = dbHelper.updateCoche(coche);

            if (success == 1 ) {
                // Coche actualizado con éxito
                Toast.makeText(EditCocheActivity.this, "Coche actualizado con éxito", Toast.LENGTH_SHORT).show();
                Intent resultIntent = new Intent();
                resultIntent.putExtra("coche_id", cocheId); // Pasar el ID del coche actualizado
                setResult(RESULT_OK, resultIntent);
                finish(); // Cierra la actividad
            } else {
                // Error al actualizar el coche
                Toast.makeText(EditCocheActivity.this, "Error al actualizar coche", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
