package com.fredrikpedersen.circlecalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText inputRadius;
    EditText outputVolume;
    EditText outputCircumference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputRadius = findViewById(R.id.inputRadius);
        outputVolume = findViewById(R.id.showVolume);
        outputCircumference = findViewById(R.id.showCircumference);

        final Button calculate = findViewById(R.id.calculate);

        calculate.setOnClickListener(view -> {
            float inValue = Float.parseFloat(inputRadius.getText().toString());
            outputVolume.setText(String.valueOf(calculateVolume(inValue)));
            outputCircumference.setText(String.valueOf(calculateCircumference(inValue)));
        });
    }


    private float calculateVolume(float radius) {
        return (float)((4/3) * Math.PI * Math.pow(radius, 3));
    }

    private float calculateCircumference(float radius) {
        return (float)(Math.PI * 2 * radius);
    }
}
