package com.fredrikpedersen.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText result;
    private EditText newNumber;
    private TextView displayOperation;
    private Button button0;
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;
    private Button button6;
    private Button button7;
    private Button button8;
    private Button button9;
    private Button buttonDot;
    private Button buttonEquals;
    private Button buttonDivide;
    private Button buttonMultiply;
    private Button buttonMinus;
    private Button buttonPlus;

    //Variables to hold the operands and type of calculations
    private Double operand1 = null;
    private Double operand2 = null;
    private String pendingOperation = "=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();

        //Create and set listeners listeners
        View.OnClickListener appendListener = createAppendListener();
        View.OnClickListener operationListener = createOperationListener();
        setAppendListeners(appendListener);
        setOperationListeners(operationListener);



    }


    private void initializeViews() {
        result = findViewById(R.id.result);
        newNumber = findViewById(R.id.newNumber);
        displayOperation = findViewById(R.id.operation);

        button0 = findViewById(R.id.button0);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        button5 = findViewById(R.id.button5);
        button6 = findViewById(R.id.button6);
        button7 = findViewById(R.id.button7);
        button8 = findViewById(R.id.button8);
        button9 = findViewById(R.id.button9);
        buttonDot = findViewById(R.id.dotButton);
        buttonEquals = findViewById(R.id.equalsButton);
        buttonDivide = findViewById(R.id.divideButton);
        buttonMultiply = findViewById(R.id.multiplyButton);
        buttonMinus = findViewById(R.id.subtractionButton);
        buttonPlus = findViewById(R.id.additionButton);
    }

    private View.OnClickListener createAppendListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button b = (Button)view;
                newNumber.append(b.getText().toString());
            }
        };
    }

    private View.OnClickListener createOperationListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button b = (Button) view;
                String operation = b.getText().toString();
                String value = newNumber.getText().toString();

                if (value.length() != 0) {
                    performOperation(value, operation);
                }

                pendingOperation = operation;
                displayOperation.setText(pendingOperation);
            }
        };
    }

    private void setAppendListeners(View.OnClickListener listener) {
        button0.setOnClickListener(listener);
        button1.setOnClickListener(listener);
        button2.setOnClickListener(listener);
        button3.setOnClickListener(listener);
        button4.setOnClickListener(listener);
        button5.setOnClickListener(listener);
        button6.setOnClickListener(listener);
        button7.setOnClickListener(listener);
        button8.setOnClickListener(listener);
        button9.setOnClickListener(listener);
        buttonDot.setOnClickListener(listener);
    }

    private void setOperationListeners(View.OnClickListener operationListener) {
        buttonEquals.setOnClickListener(operationListener);
        buttonDivide.setOnClickListener(operationListener);
        buttonMultiply.setOnClickListener(operationListener);
        buttonMinus.setOnClickListener(operationListener);
        buttonPlus.setOnClickListener(operationListener);
    }

    private void performOperation(String value, String operation) {
        displayOperation.setText(operation);
    }


}
