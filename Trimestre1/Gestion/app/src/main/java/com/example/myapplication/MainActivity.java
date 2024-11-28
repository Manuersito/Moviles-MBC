package com.example.myapplication;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private CocheAdapter adapter;
    private List<Coches> listaCoches;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);

        // Crear lista de coches
        listaCoches = new ArrayList<>();
        listaCoches.add(new Coches("Toyota Celica V", "Coche 1.6 cabrio", R.drawable.toyota_celica, true, 4.5f, "https://Toyota.com", "123456789"));
        listaCoches.add(new Coches("RAM TRX", "Pick up v8 6.2", R.drawable.ram_trx, false, 3.5f, "https://Ram.com", "987654321"));
        listaCoches.add(new Coches("Audi Sport Quattro", "Coche 2.0 traccion cuatro", R.drawable.audi_sport_quattro, false, 4.0f, "https://Audi.com", "627162399"));

        // Configurar adaptador y asignarlo al ListView
        adapter = new CocheAdapter(this, listaCoches);
        listView.setAdapter(adapter);

        // Registrar el ListView para el menú contextual
        registerForContextMenu(listView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        if (v.getId() == R.id.listView) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_contextual, menu);
        }
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = info.position; // Obtén la posición del ítem seleccionado

        if (item.getItemId() == R.id.editar) {
            // Aquí puedes agregar lógica para editar el elemento
            return true;
        } else if (item.getItemId() == R.id.borrar) {
            // Mostrar un diálogo de confirmación antes de eliminar
            new AlertDialog.Builder(this)
                    .setTitle("Eliminar coche")
                    .setMessage("¿Estás seguro de que deseas eliminar este coche?")
                    .setPositiveButton("Sí", (dialog, which) -> {
                        eliminarCoche(position); // Llama a tu método para eliminar el coche
                    })
                    .setNegativeButton("No", null)
                    .show();
            return true;
        } else {
            return super.onContextItemSelected(item);
        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_ordenar, menu);  // Asegúrate de poner el nombre correcto del menú
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.ordenarNombre) {
            // Ordenar por nombre
            ordenarPorNombre();
            return true;
        } else if (item.getItemId() == R.id.ordenarValoracion) {
            // Ordenar por valoración
            ordenarPorValoracion();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }


    private void ordenarPorNombre() {
        Collections.sort(listaCoches, (coche1, coche2) -> coche1.getNombre().compareTo(coche2.getNombre()));
        adapter.notifyDataSetChanged(); // Notificar que los datos han cambiado para que se actualice el ListView o el Adapter
    }

    private void ordenarPorValoracion() {
        Collections.sort(listaCoches, (coche1, coche2) -> Float.compare(coche2.getValoracion(), coche1.getValoracion()));
        adapter.notifyDataSetChanged(); // Notificar que los datos han cambiado para que se actualice el ListView o el Adapter
    }


    private void eliminarCoche(int position) {
        listaCoches.remove(position);
        adapter.notifyDataSetChanged(); // Actualizar el adaptador
    }
}
