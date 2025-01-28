package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private CocheAdapter adapter;
    private List<Coches> listaCoches;
    private DBHelper dbHelper;  // Base de datos helper

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);

        // Inicializar el helper de base de datos
        dbHelper = new DBHelper(this);

        // Obtener los coches desde la base de datos
        listaCoches = dbHelper.getAllCoches();  // Obtiene todos los coches desde la base de datos

        // Configurar adaptador y asignarlo al ListView
        adapter = new CocheAdapter(this, listaCoches);
        listView.setAdapter(adapter);

        // Registrar el ListView para el menú contextual
        registerForContextMenu(listView);

        // Configurar el botón de agregar coche
        Button btn_add = findViewById(R.id.btn_add);  // Asegúrate de que el id coincida con el del botón en el layout
        btn_add.setOnClickListener(v -> btnAddCoche());  // Llamar al método que inicia la actividad de agregar coche
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
        int position = info.position;  // Obtén la posición del ítem seleccionado

        if (item.getItemId() == R.id.editar) {
            // Dentro de MainActivity
            int cocheId = listaCoches.get(position).getId();
            Log.d("EditCoche", "Coche ID: " + cocheId);
            Intent intent = new Intent(this, EditCocheActivity.class);
            intent.putExtra("coche_id", listaCoches.get(position).getId());  // Pasa solo el ID
            startActivityForResult(intent, 1);

            return true;
        } else if (item.getItemId() == R.id.borrar) {
            // Mostrar un diálogo de confirmación antes de eliminar
            new AlertDialog.Builder(this)
                    .setTitle("Eliminar coche")
                    .setMessage("¿Estás seguro de que deseas eliminar este coche?")
                    .setPositiveButton("Sí", (dialog, which) -> {
                        eliminarCoche(position);  // Llama a tu método para eliminar el coche
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
        } else if (item.getItemId() == R.id.menu_info) {
            showInfoDialog(this);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    public void btnAddCoche() {
        Intent intent = new Intent(this, AddCocheActivity.class);
        startActivityForResult(intent, 1);  // Inicia la actividad de edición
    }

    private void ordenarPorNombre() {
        Collections.sort(listaCoches, (coche1, coche2) -> coche1.getNombre().compareTo(coche2.getNombre()));
        adapter.notifyDataSetChanged();  // Notificar que los datos han cambiado para que se actualice el ListView o el Adapter
    }

    private void ordenarPorValoracion() {
        Collections.sort(listaCoches, (coche1, coche2) -> Float.compare(coche2.getValoracion(), coche1.getValoracion()));
        adapter.notifyDataSetChanged();  // Notificar que los datos han cambiado para que se actualice el ListView o el Adapter
    }

    private void eliminarCoche(int position) {
        // Eliminar coche de la base de datos
        Coches cocheAEliminar = listaCoches.get(position);
        dbHelper.deleteCoche(cocheAEliminar.getId());  // Eliminar del SQLite
        listaCoches.remove(position);  // Eliminar de la lista en memoria
        adapter.notifyDataSetChanged();  // Actualizar el adaptador
    }

    // Método para mostrar el Toast personalizado
    private void showCustomToast(String message) {
        // Inflar el layout personalizado
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast, null);  // Usamos null como contenedor

        // Configurar el texto del Toast
        TextView text = layout.findViewById(R.id.toast_message);
        text.setText(message);

        // Crear el Toast personalizado
        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

    private String readTextFile(int resourceId, Context context) {
        InputStream inputStream = context.getResources().openRawResource(resourceId);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return stringBuilder.toString();
    }

    // info app
    private void showInfoDialog(Context context) {
        String infoText = readTextFile(R.raw.info_uso, context);

        new AlertDialog.Builder(context)
                .setTitle("Información de la App")
                .setMessage(infoText)
                .setPositiveButton("Cerrar", (dialog, which) -> dialog.dismiss())
                .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 1) {
            // Comprobar si es un coche nuevo o una edición
            if (data.hasExtra("coche") && !data.hasExtra("position")) {
                // Este bloque es para agregar un nuevo coche
                Coches cocheNuevo = (Coches) data.getSerializableExtra("coche");
                dbHelper.addCoche(cocheNuevo);  // Agregar el coche a la base de datos
                listaCoches.add(cocheNuevo);  // Agregar el coche a la lista en memoria
                adapter.notifyDataSetChanged();  // Notificar al adaptador que los datos han cambiado
                showCustomToast("Coche agregado correctamente");
            } else if (data.hasExtra("coche") && data.hasExtra("position")) {
                // Este bloque es para manejar la edición de coches
                Coches cocheEditado = (Coches) data.getSerializableExtra("coche");
                int position = data.getIntExtra("position", -1);
                if (position != -1) {
                    listaCoches.set(position, cocheEditado);  // Reemplazar el coche en la lista en la posición correcta
                    dbHelper.updateCoche(cocheEditado);  // Actualizar el coche en la base de datos
                    adapter.notifyDataSetChanged();  // Notificar al adaptador que los datos han cambiado
                    showCustomToast("Coche editado correctamente");
                }
            }
        }
    }
}
