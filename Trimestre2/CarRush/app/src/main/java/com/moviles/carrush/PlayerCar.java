package com.moviles.carrush;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

public class PlayerCar {
    private Bitmap carBitmap;
    private int x, y;
    private int screenWidth, screenHeight;
    private int laneWidth;
    private int currentLane = 1; // 0 = Izquierda, 1 = Centro, 2 = Derecha
    private Paint paint;

    public PlayerCar(Context context, int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.paint = new Paint();

        // Cargar imagen del coche principal
        Bitmap originalBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.principal);
        carBitmap = Bitmap.createScaledBitmap(originalBitmap, screenWidth / 4, screenHeight / 6, true);

        // Calcular tamaño de los carriles
        laneWidth = screenWidth / 3;

        // Posicionar el coche en el carril central
        x = laneWidth * currentLane + (laneWidth - carBitmap.getWidth()) / 2;
        y = screenHeight - carBitmap.getHeight() - 50; // Un poco arriba del borde inferior
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(carBitmap, x, y, paint);
    }

    public void moveLeft() {
        if (currentLane > 0) {
            currentLane--;
            x = laneWidth * currentLane + (laneWidth - carBitmap.getWidth()) / 2;
        }
    }

    public void moveRight() {
        if (currentLane < 2) {
            currentLane++;
            x = laneWidth * currentLane + (laneWidth - carBitmap.getWidth()) / 2;
        }
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getWidth() { return carBitmap.getWidth(); }
    public int getHeight() { return carBitmap.getHeight(); }
}
