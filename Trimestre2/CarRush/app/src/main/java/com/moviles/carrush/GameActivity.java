package com.moviles.carrush;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;

public class GameActivity extends Activity {
    private GameView gameView;
    private static MediaPlayer backgroundMusic; // Ahora es static para evitar duplicados

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gameView = new GameView(this);
        setContentView(gameView);

        // Verificar si la música ya está en reproducción
        if (backgroundMusic == null) {
            backgroundMusic = MediaPlayer.create(this, R.raw.musicafondo);
            backgroundMusic.setLooping(true);
            backgroundMusic.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (backgroundMusic != null) {
            backgroundMusic.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (backgroundMusic != null && !backgroundMusic.isPlaying()) {
            backgroundMusic.start();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // No liberar la música aquí para que siga sonando entre reinicios
    }
}
