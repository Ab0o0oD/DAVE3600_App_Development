package com.fredrikpedersen.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText newNumber;
    private EditText result;
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
        if (operand1 == null) {
            operand1 = Double.valueOf(value);
        } else {
            operand2 = Double.valueOf(value);
        }

        if (pendingOperation.equals("=")) {
            pendingOperation = operation;
        }

        switch (pendingOperation) {
            case "=":
                operand1 = operand2;
                break;
            case "/":
                if (operand2 == 0) {
                    operand1 = 0.0;
                } else {
                    operand1 /= operand2;
                }
                break;
            case "*":
                operand1 *= operand2;
                break;
            case "-":
                operand1 -= operand2;
                break;
            case "+":
                operand1 += operand2;
                break;
        }

        result.setText(operand1.toString());
        newNumber.setText("");
    }

    private void initializeViews() {
        newNumber = findViewById(R.id.newNumber);
        result = findViewById(R.id.result);
        displayOperation = findViewById(R.id.operation);
    }


}
