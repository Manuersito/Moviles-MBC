package com.moviles.calculadoracheckboxeventos;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText editTextNumber1 = findViewById(R.id.editTextNumber1);
        EditText editTextNumber2 = findViewById(R.id.editTextNumber2);
        CheckBox checkBoxSum = findViewById(R.id.checkBoxSum);
        CheckBox checkBoxSubtract = findViewById(R.id.checkBoxSubtract);
        TextView textViewResult = findViewById(R.id.textViewResult);

        checkBoxSum.setOnClickListener(view -> {
            if (checkBoxSum.isChecked()) {
                int number1 = Integer.parseInt(editTextNumber1.getText().toString());
                int number2 = Integer.parseInt(editTextNumber2.getText().toString());
                int result = number1 + number2;
                textViewResult.setText("Suma: " + result);
            }
        });

        checkBoxSubtract.setOnClickListener(view -> {
            if (checkBoxSubtract.isChecked()) {
                int number1 = Integer.parseInt(editTextNumber1.getText().toString());
                int number2 = Integer.parseInt(editTextNumber2.getText().toString());
                int result = number1 - number2;
                textViewResult.setText("Resta: " + result);
            }
        });
    }
}
