package com.example.manuelbernaldezcarrascoprueba4;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar Spinner y WebView
        Spinner spinner = findViewById(R.id.spinner);
        WebView webView = findViewById(R.id.webview);

        // Configurar WebView
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);

        // Lista de pintores
        ArrayList<Pintores> pintoresList = new ArrayList<>();
        pintoresList.add(new Pintores(R.drawable.caravaggio, "Caravaggio", "Pintor italiano entre los años de 1593 y 1610. Es considerado como el primer gran exponente de la pintura del Barroco.", "http://es.wikipedia.org/wiki/Caravaggio"));
        pintoresList.add(new Pintores(R.drawable.rafael, "Rafael Sanzio", "Pintor y arquitecto italiano del Renacimiento, realizó importantes aportes en la arquitectura y, como inspector de antigüedades.", "http://es.wikipedia.org/wiki/Rafael_Sanzio"));
        pintoresList.add(new Pintores(R.drawable.velazquez, "Velázquez", "Pintor Barroco nacido en Sevilla en 1599, es considerado uno de los máximos exponentes de la pintura española y maestro de la pintura universal.", "http://es.wikipedia.org/wiki/Diego_Vel%C3%A1zquez"));
        pintoresList.add(new Pintores(R.drawable.miguelangel, "Miguel Ángel", "Arquitecto, escultor y pintor italiano renacentista, considerado uno de los más grandes artistas de la historia.", "http://es.wikipedia.org/wiki/Miguel_%C3%81ngel"));
        pintoresList.add(new Pintores(R.drawable.rembrant, "Rembrandt", "Pintor y grabador holandés. La historia del arte le considera uno de los mayores maestros barrocos de la pintura y el grabado.", "http://es.wikipedia.org/wiki/Rembrandt"));
        pintoresList.add(new Pintores(R.drawable.boticelli, "Boticelli", "Apodado Sandro Botticelli, fue un pintor cuatrocentista italiano. Su obra se ha considerado representativa de la pintura del primer Renacimiento.", "http://es.wikipedia.org/wiki/Sandro_Botticelli"));
        pintoresList.add(new Pintores(R.drawable.leonardo, "Leonardo da Vinci", "Notable polímata del Renacimiento italiano (a la vez anatomista, arquitecto, artista, botánico, científico, escritor, escultor, filósofo, ingeniero...)", "http://es.wikipedia.org/wiki/Leonardo_da_Vinci"));
        pintoresList.add(new Pintores(R.drawable.renoir, "Renoir", "Pintor francés impresionista, interesado por la pintura de cuerpos femeninos en paisajes, inspirados a menudo en pinturas clásicas renacentistas y barrocas.", "http://es.wikipedia.org/wiki/Pierre-Auguste_Renoir"));

        // Configurar adaptador
        PintoresAdapter adapter = new PintoresAdapter(this, pintoresList);
        spinner.setAdapter(adapter);

        // Manejar selección
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Pintores selectedPainter = pintoresList.get(position);
                webView.loadUrl(selectedPainter.getWikipedia());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // No hacer nada
            }
        });

    }
}
