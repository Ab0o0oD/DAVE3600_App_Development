package com.example.gradeksempel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        text = findViewById(R.id.temp);
        final Button gradknapp = findViewById(R.id.tilgrader);
        final Button fahrenheitknapp = findViewById(R.id.tilfahrenheit);

        gradknapp.setOnClickListener((v) -> {
                float innverdi = Float.parseFloat(text.getText().toString());
                text.setText(String.valueOf(convertFahrenheitToCelsius(innverdi)));
            });

        fahrenheitknapp.setOnClickListener((v) -> {
                float innverdi = Float.parseFloat(text.getText().toString());
                text.setText(String.valueOf(convertCelsiusToFahrenheit(innverdi)));
        });
    }

    float convertFahrenheitToCelsius(float fahrenheit) {
        return ((fahrenheit - 32) * 5 /9);
    }

    float convertCelsiusToFahrenheit(float celsius) {
        return ((celsius * 9) /5) +32;
    }
}
