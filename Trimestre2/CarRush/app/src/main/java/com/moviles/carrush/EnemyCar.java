package com.moviles.carrush;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import java.util.Random;

public class EnemyCar {
    private Bitmap carBitmap;
    private int x, y;
    private int screenWidth, screenHeight;
    private int laneWidth;
    private int speed;
    private Random random;

    public EnemyCar(Context context, int screenWidth, int screenHeight, int[] carImages, int speed, int lane) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.random = new Random();
        this.speed = speed; // **Ahora la velocidad depende de la puntuación**

        // Seleccionar una imagen de coche enemigo aleatoria
        Bitmap originalBitmap = BitmapFactory.decodeResource(context.getResources(), carImages[random.nextInt(carImages.length)]);
        carBitmap = Bitmap.createScaledBitmap(originalBitmap, screenWidth / 4, screenHeight / 6, true);

        // Calcular tamaño de los carriles
        laneWidth = screenWidth / 3;

        // Asignar carril aleatorio
        x = lane * laneWidth + (laneWidth - carBitmap.getWidth()) / 2;
        y = -carBitmap.getHeight() - random.nextInt(screenHeight / 2); // Generar con alturas diferentes
    }

    public void update() {
        y += speed; // Mover hacia abajo
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(carBitmap, x, y, null);
    }

    public boolean isOffScreen() {
        return y > screenHeight;
    }

    public boolean checkCollision(PlayerCar player) {
        return x < player.getX() + player.getWidth() &&
                x + getWidth() > player.getX() &&
                y < player.getY() + player.getHeight() &&
                y + getHeight() > player.getY();
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getWidth() { return carBitmap.getWidth(); }
    public int getHeight() { return carBitmap.getHeight(); }
}
