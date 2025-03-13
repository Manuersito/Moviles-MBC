package com.moviles.carrush;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.graphics.Paint;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class GameView extends View {
    private Bitmap roadBitmap;
    private int roadY1, roadY2;
    private int roadSpeed = 10;
    private int screenWidth, screenHeight;
    private Paint paint;
    private PlayerCar playerCar;
    private ArrayList<EnemyCar> enemies;
    private int[] enemyCarImages;
    private Random random;
    private int spawnTimer = 0;
    private boolean isGameOver = false;
    private int score = 0;
    private int baseSpeed = 10;
    private int spawnRate = 100;
    private int speedIncreaseRate = 300;

    // Variables del Power-Up
    private PowerUp powerUp;
    private boolean isPowerUpActive = false;
    private int powerUpTimer = 0;
    private int powerUpSpawnTimer = 0;
    private int powerUpDuration = 600;

    // Sonidos
    private MediaPlayer soundCollision;
    private MediaPlayer soundPowerUp;
    private MediaPlayer soundMotor;

    //ciclo dia noche
    private Paint dayNightPaint;
    private float colorFactor = 0f; // Controla la transición
    private boolean isDay = true;
    private int cycleTimer = 0;
    private int dayNightCycle = 1800; // Cambia cada 30 segundos



    public GameView(Context context) {
        super(context);
        paint = new Paint();
        random = new Random();

        // Obtener dimensiones de la pantalla
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;

        // Cargar la carretera y ajustarla al tamaño de la pantalla
        Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.carretera);
        roadBitmap = Bitmap.createScaledBitmap(originalBitmap, screenWidth, screenHeight, true);

        // Inicializar el coche del jugador
        playerCar = new PlayerCar(context, screenWidth, screenHeight);

        // Posiciones iniciales de la carretera
        roadY1 = 0;
        roadY2 = -screenHeight;

        // Cargar imágenes de coches enemigos
        enemyCarImages = new int[]{R.drawable.enemigo1, R.drawable.enemigo2, R.drawable.enemigo3};
        enemies = new ArrayList<>();

        // Inicializar sonidos
        soundCollision = MediaPlayer.create(context, R.raw.colision);
        soundPowerUp = MediaPlayer.create(context, R.raw.powerup);
        soundMotor = MediaPlayer.create(context, R.raw.motor);

        soundMotor.setLooping(true);

        // Iniciar sonidos al comenzar la partida
        soundMotor.start();

        // Inicializar la capa de color para día/noche
        dayNightPaint = new Paint();
        dayNightPaint.setARGB(100, 0, 0, 0); // Color inicial: Transparente

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Dibujar la carretera con desplazamiento infinito
        canvas.drawBitmap(roadBitmap, 0, roadY1, paint);
        canvas.drawBitmap(roadBitmap, 0, roadY2, paint);

        // Mover la carretera hacia abajo
        roadY1 += roadSpeed;
        roadY2 += roadSpeed;

        if (roadY1 >= screenHeight) {
            roadY1 = -screenHeight;
        }
        if (roadY2 >= screenHeight) {
            roadY2 = -screenHeight;
        }

        // **Dibujar el coche del jugador**
        playerCar.draw(canvas);

        // **Día/Noche: Actualizar gradualmente la opacidad del filtro**
        cycleTimer++;
        if (cycleTimer > dayNightCycle) {
            isDay = !isDay;
            cycleTimer = 0;
        }

        if (isDay) {
            colorFactor = Math.max(0f, colorFactor - 0.001f); // Hacer más claro
        } else {
            colorFactor = Math.min(1f, colorFactor + 0.001f); // Hacer más oscuro
        }

        // **Aplicar la capa de sombra dependiendo del ciclo día/noche**
        int alpha = (int) (colorFactor * 150); // Ajusta la intensidad de la sombra
        dayNightPaint.setARGB(alpha, 0, 0, 0);
        canvas.drawRect(0, 0, screenWidth, screenHeight, dayNightPaint);

        if (!isGameOver) {
            score += isPowerUpActive ? 2 : 1;
            int currentSpeed = baseSpeed + (score / speedIncreaseRate);

            spawnTimer++;
            if (spawnTimer > spawnRate) {
                spawnEnemy(currentSpeed);
                spawnTimer = 0;
            }

            powerUpSpawnTimer++;
            if (powerUpSpawnTimer > 1000) {
                powerUp = new PowerUp(getContext(), screenWidth, screenHeight, currentSpeed);
                powerUpSpawnTimer = 0;
            }

            if (powerUp != null) {
                powerUp.update();
                powerUp.draw(canvas);

                if (powerUp.checkCollision(playerCar)) {
                    isPowerUpActive = true;
                    powerUpTimer = 0;
                    powerUp.deactivate();
                    soundPowerUp.start();
                }

                if (powerUp.isOffScreen()) {
                    powerUp = null;
                }
            }

            if (isPowerUpActive) {
                powerUpTimer++;
                if (powerUpTimer > powerUpDuration) {
                    isPowerUpActive = false;
                }
            }

            Iterator<EnemyCar> iterator = enemies.iterator();
            while (iterator.hasNext()) {
                EnemyCar enemy = iterator.next();
                enemy.update();

                if (enemy.checkCollision(playerCar)) {
                    isGameOver = true;
                    soundCollision.start();
                    soundMotor.pause();
                }

                enemy.draw(canvas);

                if (enemy.isOffScreen()) {
                    iterator.remove();
                }
            }

            drawScore(canvas);
        } else {
            showGameOver(canvas);
        }

        invalidate();
    }



    public void restartGame() {
        isGameOver = false;
        enemies.clear();
        playerCar = new PlayerCar(getContext(), screenWidth, screenHeight);
        score = 0;
        isPowerUpActive = false;
        powerUp = null;

        isDay = true; // Reiniciar ciclo de día/noche
        cycleTimer = 0;
        colorFactor = 0f;

        soundMotor.start();
        invalidate();
    }


    private void spawnEnemy(int speed) {
        int lane = random.nextInt(3);
        enemies.add(new EnemyCar(getContext(), screenWidth, screenHeight, enemyCarImages, speed, lane));
    }

    private void drawScore(Canvas canvas) {
        Paint scorePaint = new Paint();
        scorePaint.setColor(Color.WHITE);
        scorePaint.setTextSize(50);
        scorePaint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText("Puntos: " + score, 50, 100, scorePaint);
    }

    private void showGameOver(Canvas canvas) {
        Paint textPaint = new Paint();
        textPaint.setColor(Color.RED);
        textPaint.setTextSize(100);
        textPaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("GAME OVER", screenWidth / 2, screenHeight / 2, textPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (isGameOver) {
                restartGame();
            } else {
                float x = event.getX();
                if (x < screenWidth / 2) {
                    playerCar.moveLeft();
                } else {
                    playerCar.moveRight();
                }
            }
            return true;
        }
        return super.onTouchEvent(event);
    }
}
