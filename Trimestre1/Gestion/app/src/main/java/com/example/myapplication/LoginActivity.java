package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private Map<String, String> usuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Inicializar el mapa con los usuarios y contraseñas
        usuarios = new HashMap<>();
        usuarios.put("admin", "1234");
        usuarios.put("usuario", "usuario");
        usuarios.put("a", "a");

        EditText usernameEditText = findViewById(R.id.usernameEditText);
        EditText passwordEditText = findViewById(R.id.passwordEditText);
        Button loginButton = findViewById(R.id.loginButton);

        SharedPreferences prefe=getSharedPreferences("datos", Context.MODE_PRIVATE);
        usernameEditText.setText(prefe.getString("usuario",""));

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                SharedPreferences preferencias=getSharedPreferences("datos",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=preferencias.edit();
                editor.putString("usuario", usernameEditText.getText().toString());
                editor.commit();
                finish();
                // Validar usuario
                if (validarUsuario(username, password)) {
                    // Inicio de sesión exitoso
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish(); // Termina la actividad de login
                } else {
                    // Error de inicio de sesión
                    Toast.makeText(LoginActivity.this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Método para validar el usuario
    private boolean validarUsuario(String username, String password) {
        return usuarios.containsKey(username) && usuarios.get(username).equals(password);
    }
}
