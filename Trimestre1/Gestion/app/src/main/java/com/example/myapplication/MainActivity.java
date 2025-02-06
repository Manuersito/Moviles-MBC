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

import androidx.annotation.Nullable;
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
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        dbHelper = new DBHelper(this);

        // Cargar los coches de la base de datos y asignar el adaptador
        listaCoches = dbHelper.getAllCoches();
        adapter = new CocheAdapter(this, listaCoches);
        listView.setAdapter(adapter);

        registerForContextMenu(listView);

        Button btn_add = findViewById(R.id.btn_add);
        if (btn_add != null) {
            btn_add.setOnClickListener(v -> btnAddCoche());
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu_contextual, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        if (info == null || info.position < 0 || info.position >= listaCoches.size()) {
            return false;
        }
        int position = info.position;

        if (item.getItemId() == R.id.editar) {
            Intent intent = new Intent(this, EditCocheActivity.class);
            intent.putExtra("coche_id", listaCoches.get(position).getId());
            startActivityForResult(intent, 1);
            return true;
        } else if (item.getItemId() == R.id.borrar) {
            new AlertDialog.Builder(this)
                    .setTitle("Eliminar coche")
                    .setMessage("¿Estás seguro de que deseas eliminar este coche?")
                    .setPositiveButton("Sí", (dialog, which) -> eliminarCoche(position))
                    .setNegativeButton("No", null)
                    .show();
            return true;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_ordenar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.ordenarNombre) {
            ordenarPorNombre();
            return true;
        } else if (item.getItemId() == R.id.ordenarValoracion) {
            ordenarPorValoracion();
            return true;
        } else if (item.getItemId() == R.id.menu_info) {
            showInfoDialog(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void btnAddCoche() {
        startActivityForResult(new Intent(this, AddCocheActivity.class), 1);
    }

    private void ordenarPorNombre() {
        Collections.sort(listaCoches, (coche1, coche2) -> coche1.getNombre().compareToIgnoreCase(coche2.getNombre()));
        adapter.notifyDataSetChanged();
    }

    private void ordenarPorValoracion() {
        Collections.sort(listaCoches, (coche1, coche2) -> Float.compare(coche2.getValoracion(), coche1.getValoracion()));
        adapter.notifyDataSetChanged();
    }

    private void eliminarCoche(int position) {
        Coches cocheAEliminar = listaCoches.get(position);
        dbHelper.deleteCoche(cocheAEliminar.getId());
        listaCoches.remove(position);
        adapter.notifyDataSetChanged();
        showCustomToast("Coche eliminado con éxito");
    }

    private void showCustomToast(String message) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast, findViewById(R.id.toast_message));
        TextView text = layout.findViewById(R.id.toast_message);
        text.setText(message);

        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

    private String readTextFile(int resourceId) {
        StringBuilder stringBuilder = new StringBuilder();
        try (InputStream inputStream = getResources().openRawResource(resourceId);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    private void showInfoDialog(Context context) {
        String infoText = readTextFile(R.raw.info_uso);
        new AlertDialog.Builder(context)
                .setTitle("Información de la App")
                .setMessage(infoText)
                .setPositiveButton("Cerrar", null)
                .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 1 && data != null) {
            Coches coche = (Coches) data.getSerializableExtra("coche");
            int position = data.getIntExtra("position", -1);

            if (coche != null) {
                if (position == -1) {
                    // Si el coche no tiene posición (es un nuevo coche)
                    if (!cocheExiste(coche)) { // Verificar si ya existe en la lista
                        dbHelper.addCoche(coche); // Agregar a la base de datos
                        listaCoches.add(coche);    // Agregar a la lista
                        showCustomToast("Coche agregado correctamente");
                    } else {
                        showCustomToast("El coche ya existe en la lista.");
                    }
                } else {
                    // Si se está editando un coche existente
                    dbHelper.updateCoche(coche);   // Actualizar en la base de datos
                    listaCoches.set(position, coche);  // Actualizar en la lista
                    showCustomToast("Coche editado correctamente");
                }
                adapter.notifyDataSetChanged(); // Notificar al adaptador para actualizar la vista
            }
        }
    }


    private boolean cocheExiste(Coches coche) {
        // Verificar si el coche ya existe en la lista para evitar duplicados
        for (Coches c : listaCoches) {
            if (c.getId() == coche.getId()) {
                return true;
            }
        }
        return false;
    }
}
