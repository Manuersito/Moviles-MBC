package com.example.miprimergrafico;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.view.View;

public class GraficoView extends View {
    private Paint paintText, paintGreen, paintRed;
    private float width, height, s;

    public GraficoView(Context context) {
        super(context);
        init();
    }

    private void init() {
        // Inicialización de los pinceles
        paintText = new Paint();
        paintText.setColor(Color.BLACK);
        paintText.setTextSize(40);
        paintText.setAntiAlias(true);

        paintGreen = new Paint();
        paintGreen.setColor(Color.GREEN);
        paintGreen.setStrokeWidth(5);

        paintRed = new Paint();
        paintRed.setColor(Color.RED);
        paintRed.setStrokeWidth(5);

        // Obtener dimensiones de la pantalla y el valor de escala (s)
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        width = metrics.widthPixels;
        height = metrics.heightPixels;
        s = metrics.density; // Factor de escala basado en la densidad
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Dibujar los textos con las dimensiones y el escalado
        canvas.drawText("Dimensiones: " + width + " x " + height, 20, 50, paintText);
        canvas.drawText("Escalado: " + s, 20, 100, paintText);

        // Dibujar la línea verde horizontal en (0, 40 * s) hasta (width, 40 * s)
        canvas.drawLine(0, 40 * s, width, 40 * s, paintGreen);

        // Dibujar la línea roja vertical en (20 * s, 0) hasta (20 * s, height)
        canvas.drawLine(20 * s, 0, 20 * s, height, paintRed);
    }
}
