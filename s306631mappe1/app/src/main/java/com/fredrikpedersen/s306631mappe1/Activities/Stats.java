package com.fredrikpedersen.s306631mappe1.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.fredrikpedersen.s306631mappe1.R;

public class Stats extends BaseActivity {

    //Save content Strings
    private String PREFERENCE;
    private String CORRECTPREF;
    private String WRONGPREF;

    //Views
    private TextView correctAnswers; //Textview for showcasing how many correct answers the player have had in total
    private TextView wrongAnswers;  //Textview for showcasing how many wrong answers the player have had in total

    //Variables
    private int correct; //How many correct answers the player have had in total
    private int wrong; //How many wrong answers the player have had in total

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

    //Sets correct and wrong to zero, then updates the textViews accordingly
    public void deleteStats(View view) { //TODO add a MessageDialog asking the user if they really want to delete their stats
        this.correct = 0;
        this.wrong = 0;
        correctAnswers.setText("0");
        wrongAnswers.setText("0");
    }

    //Initializes all the views
    private void initializeViews() {
        correctAnswers = findViewById(R.id.correctAnswers);
        wrongAnswers = findViewById(R.id.wrongAnswers);
    }

    //Initializes all the save content Strings
    private void initializeSaveContentStrings() {
        PREFERENCE = getResources().getString(R.string.PREFERENCE);
        CORRECTPREF = getResources().getString(R.string.CORRECTPREF);
        WRONGPREF = getResources().getString(R.string.WRONGPREF);
    }

    //Sets the text in the TextViews
    private void showStats() {
        correctAnswers.setText(String.valueOf(correct));
        wrongAnswers.setText(String.valueOf(wrong));
    }

    /* ---------- Life-Cycle Methods ------------- */

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getSharedPreferences(PREFERENCE, MODE_PRIVATE)
                .edit()
                .putInt(CORRECTPREF, correct)
                .putInt(WRONGPREF,wrong)
                .apply();
    }
}