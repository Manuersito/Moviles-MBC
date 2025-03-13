package com.moviles.carrush;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import java.util.Random;

public class PowerUp {
    private Bitmap powerUpBitmap;
    private int x, y;
    private int screenWidth, screenHeight;
    private int laneWidth;
    private int speed;
    private boolean isActive;

    public PowerUp(Context context, int screenWidth, int screenHeight, int speed) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.speed = speed;
        this.isActive = true;

        // Cargar la imagen del Power-Up
        Bitmap originalBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.powerup);
        powerUpBitmap = Bitmap.createScaledBitmap(originalBitmap, screenWidth / 6, screenHeight / 10, true);

        // Calcular tamaño de los carriles
        laneWidth = screenWidth / 3;

        // Posicionar en un carril aleatorio
        int lane = new Random().nextInt(3);
        x = lane * laneWidth + (laneWidth - powerUpBitmap.getWidth()) / 2;
        y = -powerUpBitmap.getHeight(); // Comienza fuera de la pantalla
    }

    public void update() {
        y += speed; // Mover hacia abajo
    }

    public void draw(Canvas canvas) {
        if (isActive) {
            canvas.drawBitmap(powerUpBitmap, x, y, null);
        }
    }

    public boolean isOffScreen() {
        return y > screenHeight;
    }

    public boolean checkCollision(PlayerCar player) {
        return isActive &&
                x < player.getX() + player.getWidth() &&
                x + getWidth() > player.getX() &&
                y < player.getY() + player.getHeight() &&
                y + getHeight() > player.getY();
    }

    public void deactivate() {
        isActive = false;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getWidth() { return powerUpBitmap.getWidth(); }
    public int getHeight() { return powerUpBitmap.getHeight(); }
}
