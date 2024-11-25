package com.example.myapplication;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CocheAdapter adapter;
    private List<Coches> listaCoches;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar RecyclerView y lista de coches
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Crear lista de videojuegos con imágenes
        listaCoches = new ArrayList<>();
        listaCoches.add(new Coches("Toyota Celica V", "Coche 1.6 cabrio", R.drawable.toyota_celica, true, 4.5f, "https://Toyota.com", "123456789"));
        listaCoches.add(new Coches("RAM TRX", "Pick up v8 6.2", R.drawable.ram_trx, false, 3.5f, "https://Ram.com", "987654321"));
        listaCoches.add(new Coches("Audi Sport Quattro", "Coche 2.0 traccion cuatro", R.drawable.audi_sport_quattro, false, 4.0f, "https://Audi.com", "627162399"));

        adapter = new CocheAdapter(listaCoches);
        recyclerView.setAdapter(adapter);
    }
}
