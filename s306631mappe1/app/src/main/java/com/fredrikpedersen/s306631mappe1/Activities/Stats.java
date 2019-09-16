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
    private TextView correctAnswersText; //Textview for showcasing how many correct answers the player have had in total
    private TextView wrongAnswersText;  //Textview for showcasing how many wrong answers the player have had in total

    //Variables
    private int correctAnswers; //How many correct answers the player have had in total
    private int wrongAnswers; //How many wrong answers the player have had in total

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stats_activity);

        initializeViews();
        initializeSaveContentStrings();
        initializeValues();
        showStats();
    }

    //Sets correct and wrong to zero, then updates the textViews accordingly
    public void deleteStats(View view) { //TODO add a MessageDialog asking the user if they really want to delete their stats
        this.correctAnswers = 0;
        this.wrongAnswers = 0;
        correctAnswersText.setText("0");
        wrongAnswersText.setText("0");
    }

    //Initializes all the views
    private void initializeViews() {
        correctAnswersText = findViewById(R.id.correctAnswers);
        wrongAnswersText = findViewById(R.id.wrongAnswers);
    }

    //Initializes all values
    private void initializeValues() {
        correctAnswers = getSharedPreferences(PREFERENCE, MODE_PRIVATE).getInt(CORRECTPREF,0);
        wrongAnswers = getSharedPreferences(PREFERENCE, MODE_PRIVATE).getInt(WRONGPREF,0);
    }

    //Initializes all the save content Strings
    private void initializeSaveContentStrings() {
        PREFERENCE = getResources().getString(R.string.PREFERENCE);
        CORRECTPREF = getResources().getString(R.string.CORRECTPREF);
        WRONGPREF = getResources().getString(R.string.WRONGPREF);
    }

    //Sets the text in the TextViews
    private void showStats() {
        correctAnswersText.setText(String.valueOf(correctAnswers));
        wrongAnswersText.setText(String.valueOf(wrongAnswers));
    }

    /* ---------- Life-Cycle Methods ------------- */

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getSharedPreferences(PREFERENCE, MODE_PRIVATE)
                .edit()
                .putInt(CORRECTPREF, correctAnswers)
                .putInt(WRONGPREF,wrongAnswers)
                .apply();
    }
}