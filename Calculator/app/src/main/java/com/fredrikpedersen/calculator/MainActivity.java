package com.fredrikpedersen.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText newNumber;
    private TextView displayOperation;

    //Variables to hold the operands and type of calculations
    private Double operand1 = null;
    private Double operand2 = null;
    private String pendingOperation = "=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
    }

    public void  appendListener(View view) {
        Button b = (Button)view;
        newNumber.append(b.getText().toString());
    }

    public void operationListener(View view) {
        Button b = (Button) view;
        String operation = b.getText().toString();
        String value = newNumber.getText().toString();

        if (value.length() != 0) {
            performOperation(value, operation);
        }

        pendingOperation = operation;
        displayOperation.setText(pendingOperation);
    }

    private void performOperation(String value, String operation) {
        displayOperation.setText(operation);
    }

    private void initializeViews() {
        newNumber = findViewById(R.id.newNumber);
        displayOperation = findViewById(R.id.operation);
    }


}
