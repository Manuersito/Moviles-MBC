package com.moviles.bola3;

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
        int x, y, xmax, ymax;
        Paint paintFondo, paintParticula, paint;
        // Velocidades y aceleraciones para movimiento en x e y
        float vx = 0.5f, vy = 0.5f;
        float ax = 0.001f, ay = 0.001f;

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
                // Actualiza velocidades y posiciones
                vx += ax * dt;
                vy += ay * dt;
                x += (int) (vx * dt);
                y += (int) (vy * dt);
                // Rebote en bordes horizontales
                if (x > xmax) {
                    x = xmax;
                    vx = -vx;
                    ax = -ax;
                } else if (x < 0) {
                    x = 0;
                    vx = -vx;
                    ax = -ax;
                }
                // Rebote en bordes verticales
                if (y > ymax) {
                    y = ymax;
                    vy = -vy;
                    ay = -ay;
                } else if (y < 0) {
                    y = 0;
                    vy = -vy;
                    ay = -ay;
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
            xmax = w;
            ymax = h;
            // Iniciamos la bola en el centro de la pantalla
            x = w / 2;
            y = h / 2;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawPaint(paintFondo);
            canvas.drawCircle(x, y, 30, paintParticula);
            canvas.drawText("POSICIÓN=(" + x + "," + y + ")", 50, 50, paint);
            canvas.drawText("TIEMPO=" + tiempo, 50, 90, paint);
        }
    }
}
