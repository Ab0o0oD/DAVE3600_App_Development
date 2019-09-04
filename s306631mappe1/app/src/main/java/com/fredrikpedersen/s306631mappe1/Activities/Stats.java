package com.fredrikpedersen.s306631mappe1.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.fredrikpedersen.s306631mappe1.R;

public class Stats extends BaseActivity {

    private final String PREFERENCE = "Preferences";
    private final String CORRECTPREF = "Number of Correct Answers";
    private final String WRONGPREF = "Number of Wrong Answers";

    //Views
    private TextView correctAnswers;
    private TextView wrongAnswers;

    //Variables
    private static int correct = 0;
    private static int wrong = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stats_activity);

        initializeViews();
        showStats();
    }

    public void deleteStats(View view) {
        setCorrect(0);
        setWrong(0);
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

    public static void incrementCorrect(int increment) {
        correct += increment;
    }

    public static void incrementWrong(int increment) {
        wrong += increment;
    }

    public static void setCorrect(int correct) {
        Stats.correct = correct;
    }

    public static void setWrong(int wrong) {
        Stats.wrong = wrong;
    }

    /* ---------- Life-Cycle Methods ------------- */

    @Override
    protected void onPause() {
        super.onPause();
        getSharedPreferences(PREFERENCE, MODE_PRIVATE)
                .edit()
                .putInt(CORRECTPREF, correct)
                .putInt(WRONGPREF,wrong)
                .apply();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setCorrect(getSharedPreferences(PREFERENCE, MODE_PRIVATE).getInt(CORRECTPREF,0));
        setWrong(getSharedPreferences(PREFERENCE, MODE_PRIVATE).getInt(WRONGPREF,0));
    }
}