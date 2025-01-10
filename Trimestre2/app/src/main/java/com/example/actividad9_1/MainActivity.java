package com.example.actividad9_1;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.actividad9_1.R;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Cargar Fragmento 1
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer1, new Fragment1())
                .commit();

        // Cargar Fragmento 2
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer2, new Fragment2())
                .commit();
    }
}
