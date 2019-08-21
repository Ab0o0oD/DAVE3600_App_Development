package com.example.gradeksempel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        text=(EditText)findViewById(R.id.temp);
        final Button gradknapp=(Button)findViewById(R.id.tilgrader);
        final Button fahrenheitknapp=(Button)findViewById(R.id.tilfahrenheit);

        gradknapp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                float innverdi = Float.parseFloat(text.getText().toString());
                text.setText(String.valueOf(convertFahrenheitToCelsius(innverdi)));
            }
        });

        fahrenheitknapp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                float innverdi = Float.parseFloat(text.getText().toString());
                text.setText(String.valueOf(convertCelsiusToFahrenheit(innverdi)));
            }
        });
    }

    float convertFahrenheitToCelsius(float fahrenheit) {
        return ((fahrenheit - 32) * 5 /9);
    }

    float convertCelsiusToFahrenheit(float celsius) {
        return ((celsius * 9) /5) +32;
    }
}
