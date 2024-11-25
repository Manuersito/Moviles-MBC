package com.example.prueba3manuebernaldecarrasco;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);

        // Crear una lista de objetos Animales
        ArrayList<Animales> listaAnimales = new ArrayList<>();
        listaAnimales.add(new Animales("BUHO", "Búho es el nombre común de aves de la familia Strigidae, del orden de las estrigiformes o aves rapaces nocturnas. Habitualmente designa especies que, a diferencia de las lechuzas, tienen plumas alzadas que parecen orejas.", R.drawable.im_buho));
        listaAnimales.add(new Animales("COLIBRÍ", "Los troquilinos (Trochilinae) son una subfamilia de aves apodiformes de la familia Trochilidae, conocidas vulgarmente como colibríes...", R.drawable.im_colibri));
        listaAnimales.add(new Animales("CUERVO", "El cuervo común (Corvus corax) es una especie de ave paseriforme de la familia de los córvidos (Corvidae). Presente en todo el hemisferio septentrional...", R.drawable.im_cuervo));
        listaAnimales.add(new Animales("FLAMENCO", "Los fenicopteriformes (Phoenicopteriformes), los cuales reciben el nombre vulgar de flamencos, son un orden de aves neognatas...", R.drawable.im_flamenco));
        listaAnimales.add(new Animales("KIWI", "Los kiwis (Apterix, gr. 'sin alas') son un género de aves paleognatas compuesto por cinco especies endémicas de Nueva Zelanda...", R.drawable.im_kiwi));
        listaAnimales.add(new Animales("LORO", "Las Psitácidas (Psittacidae) son una familia de aves psitaciformes llamadas comúnmente loros o papagayos...", R.drawable.im_loro));
        listaAnimales.add(new Animales("PAVO", "Pavo es un género de aves galliformes de la familia Phasianidae, que incluye dos especies, el pavo real común...", R.drawable.im_pavo));
        listaAnimales.add(new Animales("PINGÜINO", "Los pingüinos (familia Spheniscidae, orden Sphenisciformes) son un grupo de aves marinas, no voladoras...", R.drawable.im_pinguino));

        // Obtener el ListView
        ListView listView = findViewById(R.id.listView);

        // Crear y configurar el adaptador
        AnimalesAdapter adapter = new AnimalesAdapter(this, R.layout.entrada, listaAnimales);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Obtener el animal que ha sido seleccionado
                Animales animalSeleccionado = listaAnimales.get(position);

                // Mostrar el Toast con la descripción
                Toast.makeText(getApplicationContext(), animalSeleccionado.getDescripcion(), Toast.LENGTH_SHORT).show();
            }
        });

        // Establecer el OnItemLongClickListener (Pulsación prolongada)
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // Obtener el animal que se ha seleccionado
                Animales animalSeleccionado = listaAnimales.get(position);

                // Eliminar el animal de la lista
                listaAnimales.remove(position);

                // Notificar al adaptador que la lista ha cambiado
                adapter.notifyDataSetChanged();

                return true;
            }
        });
    }
}
