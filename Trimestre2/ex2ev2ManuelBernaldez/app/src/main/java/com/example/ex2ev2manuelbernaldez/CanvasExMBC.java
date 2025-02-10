package com.example.ex2ev2manuelbernaldez;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class CanvasExMBC extends View {

    private Paint linePaint;
    private Paint textPaint;
    private float scaleDensity;
    private float step;
    private float textSize;

    // Variables para las posiciones de los textos
    private float textOffsetX;
    private float filaScale;
    private float filaWidth;
    private float filaHeight;
    private float filaRatio;

    public CanvasExMBC(Context context) {
        super(context);
        init();
    }

    private void init() {
        linePaint = new Paint();
        linePaint.setColor(Color.GREEN);
        linePaint.setStrokeWidth(3);

        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);

        scaleDensity = getResources().getDisplayMetrics().scaledDensity;
        step = 30 * scaleDensity;
        textSize = 20 * scaleDensity;
        textPaint.setTextSize(textSize);

        // Inicializar las posiciones de los textos y el ratio
        textOffsetX = 500; // Mover el texto a la derecha

        filaScale = 460;
        filaWidth = 1000;
        filaHeight = 1500;
        filaRatio = 2100;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();

        canvas.drawColor(Color.WHITE);

        // Dibujar líneas y texto en el eje Y
        for (float y = step; y < height; y += step) {
            canvas.drawLine(0, y, width, y, linePaint);
            canvas.drawText("Y: " + (int) y, 10, y - 5, textPaint);
        }

        // Dibujar textos con espacio igual entre ellos
        canvas.drawText("fila: 495 scale= " + scaleDensity, textOffsetX, filaScale, textPaint);
        canvas.drawText("fila: 990 width= " + width, textOffsetX, filaWidth, textPaint);
        canvas.drawText("fila: 1485 height= " + height, textOffsetX, filaHeight, textPaint);
        canvas.drawText("ratio= " + (float) width / height, textOffsetX, filaRatio, textPaint);
    }
}
