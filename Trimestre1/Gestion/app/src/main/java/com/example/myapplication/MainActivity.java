package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
        listaCoches.add(new Coches("Toyota Celica V", "Coche 1.6 cabrio", R.drawable.toyota_celica, true, 4.5f, "https://Toyota.com", "2023-07-15"));
        listaCoches.add(new Coches("RAM TRX", "Pick up v8 6.2", R.drawable.ram_trx, false, 3.5f, "https://Ram.com", "2024-02-28"));
        listaCoches.add(new Coches("Audi Sport Quattro", "Coche 2.0 traccion cuatro", R.drawable.audi_sport_quattro, false, 4.0f, "https://Audi.com", "2025-12-05"));

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
            Intent intent = new Intent(this, EditCocheActivity.class);
            intent.putExtra("coche", listaCoches.get(position)); // Pasa el coche
            intent.putExtra("position", position); // Pasa la posición
            startActivityForResult(intent, 1); // Inicia la actividad de edición
            return true;
        } else if (item.getItemId() == R.id.borrar) {
            // Mostrar un diálogo de confirmación antes de eliminar
            new AlertDialog.Builder(this)
                    .setTitle("Eliminar coche")
                    .setMessage("¿Estás seguro de que deseas eliminar este coche?")
                    .setPositiveButton("Sí", (dialog, which) -> {
                        eliminarCoche(position); // Llama a tu método para eliminar el coche
                        showCustomToast("Coche eliminado con éxito");
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

    // Método para mostrar el Toast personalizado
    private void showCustomToast(String message) {
        // Inflar el layout personalizado
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast, null); // Usamos null como contenedor

        // Configurar el texto del Toast
        TextView text = layout.findViewById(R.id.toast_message);
        text.setText(message);

        // Crear el Toast personalizado
        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 1) { // Asegúrate de que el requestCode es el que usaste al iniciar la actividad
            // Recuperar el coche editado y la posición
            Coches cocheEditado = (Coches) data.getSerializableExtra("coche");
            int position = data.getIntExtra("position", -1); // Recuperar la posición

            if (position != -1) {
                // Actualizar el coche en la lista
                listaCoches.set(position, cocheEditado); // Reemplazar el coche en la lista en la posición correcta

                // Notificar al adaptador que los datos han cambiado
                adapter.notifyDataSetChanged();
            }
        }
    }
}
