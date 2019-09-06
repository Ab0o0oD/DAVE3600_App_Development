package com.fredrikpedersen.s306631mappe1.Activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.fredrikpedersen.s306631mappe1.R;

public class Stats extends BaseActivity {

    //Save content Strings
    private String PREFERENCE;
    private String CORRECTPREF;
    private String WRONGPREF;

    //Views
    private TextView correctAnswers;
    private TextView wrongAnswers;

    //Variables
    private int correct;
    private int wrong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stats_activity);

        initializeViews();
        initializeSaveContentStrings();
        correct = getSharedPreferences(PREFERENCE, MODE_PRIVATE).getInt(CORRECTPREF,0);
        wrong = getSharedPreferences(PREFERENCE, MODE_PRIVATE).getInt(WRONGPREF,0);
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

    private void initializeSaveContentStrings() {
        PREFERENCE = getResources().getString(R.string.PREFERENCE);
        CORRECTPREF = getResources().getString(R.string.CORRECTPREF);
        WRONGPREF = getResources().getString(R.string.WRONGPREF);
    }

    private void showStats() {
        correctAnswers.setText(String.valueOf(correct));
        wrongAnswers.setText(String.valueOf(wrong));
    }

    public void setCorrect(int correct) {
       this.correct = correct;
    }

    public void setWrong(int wrong) {
        this.wrong = wrong;
    }

    /* ---------- Life-Cycle Methods ------------- */

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("Log", "onPause: Called");
        getSharedPreferences(PREFERENCE, MODE_PRIVATE)
                .edit()
                .putInt(CORRECTPREF, correct)
                .putInt(WRONGPREF,wrong)
                .apply();
    }
}