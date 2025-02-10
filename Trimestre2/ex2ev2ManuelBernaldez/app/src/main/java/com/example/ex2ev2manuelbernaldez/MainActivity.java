package com.example.ex2ev2manuelbernaldez;

import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        // Establecer la vista personalizada como contenido de la actividad
        setContentView(new CanvasExMBC(this));
    }
}
