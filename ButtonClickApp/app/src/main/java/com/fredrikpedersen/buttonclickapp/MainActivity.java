package com.fredrikpedersen.buttonclickapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText userInput;
    private Button button;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userInput = findViewById(R.id.editText);
        button = findViewById(R.id.button);
        textView = findViewById(R.id.textView);

        textView.setText(""); //making sure the TextView is empty at program start.
        textView.setMovementMethod(new ScrollingMovementMethod());

        button.setOnClickListener(view -> { //OnClickListener for our button, appends text to textView
            String input = userInput.getText().toString() + "\n";
            textView.append(input);
        });
    }
}
