package com.example.actividad9_2;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Cargar el Fragmento 1
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer1, new Fragment1())
                .commit();

        // Cargar el Fragmento 2
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer2, new Fragment2())
                .commit();
    }
}
