package com.fredrikpedersen.s306631mappe1.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.fredrikpedersen.s306631mappe1.Data;
import com.fredrikpedersen.s306631mappe1.R;

public class Stats extends BaseActivity {

    public static final String TAG = "Stats";

    private TextView correctAnswers;
    private TextView wrongAnswers;
    private int correct = Data.getCorrect();
    private int wrong = Data.getWrong();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stats_activity);

        initializeViews();
        showStats();
    }

    public void deleteStats(View view) {
        Data.setCorrect(0);
        Data.setWrong(0);
        correctAnswers.setText("0");
        wrongAnswers.setText("0");
    }

    private void initializeViews() {
        correctAnswers = findViewById(R.id.correctAnswers);
        wrongAnswers = findViewById(R.id.wrongAnswers);
    }

    private void showStats() {
        correctAnswers.setText(String.valueOf(correct));
        wrongAnswers.setText(String.valueOf(wrong));
    }
}