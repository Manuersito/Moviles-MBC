package com.example.calculadora;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    boolean sum, res, mul, div, pot = false;
    double num1, num2, num3;
    TextView visor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Configurar la visibilidad de las barras del sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Referencia al visor
        visor = findViewById(R.id.visor);
        Typeface mifuente = Typeface.createFromAsset(getAssets(), "font/colour.ttf");
        visor.setTypeface(mifuente);
        visor.setText("Visor");
    }

    // Métodos para asignar números
    public void set0(View view) {
        if (visor.getText()=="Visor"){
        visor.setText("0");
        }else if(visor.getText()!="Visor"){
            visor.append("0");
        }


    }

    public void set1(View view) {
        visor.append("1");
    }

    public void set2(View view) {
        visor.append("2");
    }

    public void set3(View view) {
        visor.append("3");
    }

    public void set4(View view) {
        visor.append("4");
    }

    public void set5(View view) {
        visor.append("5");
    }

    public void set6(View view) {
        visor.append("6");
    }

    public void set7(View view) {
        visor.append("7");
    }

    public void set8(View view) {
        visor.append("8");
    }

    public void set9(View view) {
        visor.append("9");
    }

    // Métodos de operaciones
    public void sumar(View view) {
        num1 = Double.parseDouble(visor.getText().toString());
        sum = true;
        visor.setText("");
    }

    public void restar(View view) {
        num1 = Double.parseDouble(visor.getText().toString());
        res = true;
        visor.setText("");
    }

    public void division(View view) {
        num1 = Double.parseDouble(visor.getText().toString());
        div = true;
        visor.setText("");
    }

    public void multiplicacion(View view) {
        num1 = Double.parseDouble(visor.getText().toString());
        mul = true;
        visor.setText("");
    }

    public void potencia(View view) {
        num1 = Double.parseDouble(visor.getText().toString());
        pot = true;
        visor.setText("");
    }

    public void decimal(View view) {
        visor.append(".");
    }

    // Método para calcular el resultado
    public void calcular(View view) {
        // Si no hay valor en el visor, no hacer nada
        if (visor.getText().toString().isEmpty()) {
            return;
        }

        num2 = Double.parseDouble(visor.getText().toString()); // Obtén el segundo número

        double resultado = 0; // Variable para guardar el resultado

        if (sum) {
            suma(num1, num2);
        } else if (res) {
            resta(num1, num2);
        } else if (div) {
            if (num2 != 0) {
                dividir(num1, num2);
            } else {
                visor.setText("Error"); // Maneja división por cero
                resetCalculator(); // Reinicia después de error
                return;
            }
        } else if (mul) {
            multiplicar(num1, num2);
        } else if (pot) {
            potencia(num1, num2);
        }

        // Mostrar el resultado en el visor
        visor.setText(String.valueOf(resultado));

        // Reiniciar todas las variables para el próximo cálculo
        resetCalculator();
    }

    public void resetCalculator() {
        num1 = 0;
        num2 = 0;
        sum = false;
        res = false;
        div = false;
        mul = false;
        pot = false;
    }


    // Métodos de operaciones matemáticas
    public double suma(double num1, double num2) {
        return num1 + num2;
    }

    public double resta(double num1, double num2) {
        return num1 - num2;
    }

    public double dividir(double num1, double num2) {
        return num1 / num2;
    }

    public double multiplicar(double num1, double num2) {
        return num1 * num2;
    }

    public double potencia(double num1, double num2) {
        return Math.pow(num1, num2);
    }
}
