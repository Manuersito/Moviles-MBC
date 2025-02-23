package com.moviles.bola2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    boolean continuar = true;
    int dt = 10; // intervalo de tiempo en milisegundos
    int tiempo = 0;
    Thread hilo = null;
    GraficoView dinamica;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dinamica = new GraficoView(this);
        setContentView(dinamica);
        hilo = new Thread(dinamica);
        hilo.start();
    }

    class GraficoView extends View implements Runnable {
        int x, y, ymax;
        Paint paintFondo, paintParticula, paint;
        float velocidad = 0f;        // inicia en reposo
        float aceleracion = 0.001f;    // aceleración constante

        public GraficoView(Context context) {
            super(context);
            paintFondo = new Paint();
            paintParticula = new Paint();
            paint = new Paint();
            paintFondo.setColor(Color.WHITE);
            paintParticula.setColor(Color.RED);
            paint.setColor(Color.BLACK);
            paint.setTextSize(30);
        }

        @Override
        public void run() {
            while (continuar) {
                tiempo += dt;
                // Actualiza la velocidad y posición
                velocidad += aceleracion * dt;
                y += (int) (velocidad * dt);
                // Rebote: invertir velocidad y aceleración al alcanzar los bordes
                if (y > ymax) {
                    y = ymax;
                    velocidad = -velocidad;
                    aceleracion = -aceleracion;
                } else if (y < 0) {
                    y = 0;
                    velocidad = -velocidad;
                    aceleracion = -aceleracion;
                }
                postInvalidate();
                try {
                    Thread.sleep(dt);
                } catch (InterruptedException e) {
                    // Manejo de excepción si es necesario
                }
            }
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            x = w / 2;
            y = 0;
            ymax = h;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawPaint(paintFondo);
            canvas.drawCircle(x, y, 30, paintParticula);
            canvas.drawText("ALTURA=" + y, 50, 50, paint);
            canvas.drawText("TIEMPO=" + tiempo, 50, 90, paint);
        }
    }
}
