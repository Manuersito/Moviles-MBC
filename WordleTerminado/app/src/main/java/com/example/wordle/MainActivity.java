package com.example.wordle;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView[] textView = new TextView[30];
    private Button[] nombreBotones = new Button[26];
    private String palabraSecreta;
    private int intentos = 0; // Número de intentos, hasta 6 filas
    private int letrasIngresadas = 0; // Contador para las letras en la fila actual

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iniciarTextView();
        iniciarBotonesLetras();
        Button borrar = findViewById(R.id.botonBorrar);
        borrar.setOnClickListener(this);
        Button intro = findViewById(R.id.Intro);
        intro.setOnClickListener(this);

        palabraSecreta = elegirPalabraSecreta();
    }

    private void iniciarTextView() {
        int[] textViewID = {
                R.id.f1c1, R.id.f1c2, R.id.f1c3, R.id.f1c4, R.id.f1c5,
                R.id.f2c1, R.id.f2c2, R.id.f2c3, R.id.f2c4, R.id.f2c5,
                R.id.f3c1, R.id.f3c2, R.id.f3c3, R.id.f3c4, R.id.f3c5,
                R.id.f4c1, R.id.f4c2, R.id.f4c3, R.id.f4c4, R.id.f4c5,
                R.id.f5c1, R.id.f5c2, R.id.f5c3, R.id.f5c4, R.id.f5c5,
                R.id.f6c1, R.id.f6c2, R.id.f6c3, R.id.f6c4, R.id.f6c5
        };

        for (int i = 0; i < textViewID.length; i++) {
            textView[i] = findViewById(textViewID[i]);
        }
    }

    private void iniciarBotonesLetras() {
        int[] botonesID = {
                R.id.A, R.id.B, R.id.C, R.id.D, R.id.E,
                R.id.F, R.id.G, R.id.H, R.id.I, R.id.J,
                R.id.K, R.id.L, R.id.M, R.id.N, R.id.O,
                R.id.P, R.id.Q, R.id.R, R.id.S, R.id.T,
                R.id.U, R.id.V, R.id.W, R.id.X, R.id.Y, R.id.Z
        };
        for (int i = 0; i < botonesID.length; i++) {
            nombreBotones[i] = findViewById(botonesID[i]);
            nombreBotones[i].setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.botonBorrar) {
            borrarUltimaLetra();
        } else if (view.getId() == R.id.Intro) {
            evaluarIntento();
        } else {
            // Captura de letras para los botones
            for (int i = 0; i < nombreBotones.length; i++) {
                if (view.getId() == nombreBotones[i].getId()) {
                    agregarLetraFilaActual(nombreBotones[i].getText().toString());
                    break;
                }
            }
        }
    }

    // Método para agregar letra en la posición vacía de la fila actual
    private void agregarLetraFilaActual(String letra) {
        if (letrasIngresadas < 5) {
            int indice = (intentos * 5) + letrasIngresadas;
            textView[indice].setText(letra);
            letrasIngresadas++;
        }
    }

    // Método para borrar la última letra ingresada en la fila actual
    private void borrarUltimaLetra() {
        if (letrasIngresadas > 0) {
            letrasIngresadas--;
            int indice = (intentos * 5) + letrasIngresadas;
            textView[indice].setText("");
        }
    }

    // Método para evaluar el intento completo al presionar el botón Intro
    @SuppressLint("ResourceAsColor")
    public void evaluarIntento() {
        if (letrasIngresadas == 5) {
            String intento = "";
            for (int col = 0; col < 5; col++) {
                TextView textViewActual = textView[intentos * 5 + col];
                intento += textViewActual.getText().toString();
            }

            // Evalúa el intento
            for (int col = 0; col < 5; col++) {
                TextView textViewActual = textView[intentos * 5 + col];
                char letra = intento.charAt(col);

                if (letra == palabraSecreta.charAt(col)) {
                    textViewActual.setBackgroundColor(Color.GREEN);
                } else if (palabraSecreta.contains(String.valueOf(letra))) {
                    textViewActual.setBackgroundColor(Color.YELLOW);
                } else {
                    textViewActual.setBackgroundColor(R.color.dark_gray);
                    cambiarColorBoton(letra, R.color.dark_gray);
                }
            }
// Comprueba si el intento coincide con la palabra secreta
            if (intento.equalsIgnoreCase(palabraSecreta)) {

                return; // Salimos de la función para evitar seguir evaluando
            }
            intentos++;
            letrasIngresadas = 0;
        }
    }

    public String elegirPalabraSecreta() {
        String[] palabras = {"PERRO", "PLUMA", "RELOJ", "VINOS", "FUEGO", "JUEGO", "LIMON", "ROBAR", "BRISA"};
        int indice = (int) (Math.random() * palabras.length);
        return palabras[indice];
    }


    private void cambiarColorBoton(char letra, int color) {
        for (Button button : nombreBotones) {
            if (button.getText().toString().equalsIgnoreCase(String.valueOf(letra))) {
                button.setBackgroundColor(color);
                break;
            }
        }
    }

}

