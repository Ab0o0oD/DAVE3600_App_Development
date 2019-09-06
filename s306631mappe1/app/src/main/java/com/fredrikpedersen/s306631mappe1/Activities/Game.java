package com.fredrikpedersen.s306631mappe1.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.fredrikpedersen.s306631mappe1.R;

import java.util.Random;

public class Game extends BaseActivity {

    //Save content Strings
    private final String TASKCOUNTER_CONTENTS = "Task Counter";
    private final String TASKNUMBER_VALUE = "Tasknumber";
    private final String CORRECT_VALUE = "Correct Answers";
    private final String WRONG_VALUE = "Wrong Answers";
    private final String TASKS_ARRAY = "Tasks";
    private final String PREFERENCE = "Preferences";
    private final String CORRECTPREF = "Number of Correct Answers";
    private final String WRONGPREF = "Number of Wrong Answers";

    //Views
    private EditText answerBox;
    private TextView taskBox;
    private TextView feedbackText;
    private ImageView feedbackIcon;
    private TextView correctCounter;

    //Values
    private String[] tasks;
    private int taskNumber = 0;
    private int correctAnswers = 0;
    private int wrongAnswers = 0;
    private int numberOfTasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);

        //Initialize views and values
        initializeViews();
        tasks = getResources().getStringArray(R.array.tasks);
        numberOfTasks = getSharedPreferences(PREFERENCE, MODE_PRIVATE).getInt(NUMBER_OF_TASKS_PREF,5);

        if (savedInstanceState == null) {
            shuffleArray();
            updateCorrectCounter();
            setTask(taskNumber);
        }
    }

    /* ---------- OnClick Methods ------------- */

    //Assigned to btn0-9 in game_activity.xml
    public void onClickAppend(View view) {
        Button btn = (Button)view;
        answerBox.append(btn.getText().toString());
    }

    //Assigned to the clearBtn in game_activity.xml
    public void onClickRemove(View view) {
        answerBox.setText("");
    }

    //Assigned to the confirmBtn in game_activity.xml
    public void onClickAnswer(View view) {
        if (gameFinished()) { //Checks if the game is finished. Asks the player if they want to start a new game if it is.
                Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.done), Toast.LENGTH_SHORT);
                toast.show();
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(getResources().getString(R.string.newGame))
                    .setCancelable(false)
                    .setPositiveButton(getResources().getString(R.string.yes), (dialogInterface, i) -> {
                        finish();
                        Intent intent = new Intent(this, this.getClass());
                        startActivity(intent);
                    })
                    .setNegativeButton(getResources().getString(R.string.no), (dialogInterface, i) -> {
                        finish();
                    })
                    .show();
        }
        handleAnswer();
    }

    @Override
    public void onBackPressed() {
        if (!gameFinished()) { //If the player haven't finished all tasks, ask if they really want to leave the activity
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(getResources().getString(R.string.backPressed))
                    .setCancelable(false)
                    .setPositiveButton(getResources().getString(R.string.yes), (dialogInterface, i) -> finish())
                    .setNegativeButton(getResources().getString(R.string.no), null)
                    .show();
        } else { //Close the activity if the player has finished the game
            finish();
        }
    }

    /* ---------- Game Methods ------------- */

    private void handleAnswer() { //Handles no, wrong or correct answer input from the user
        if (answerBox.getText().toString().equals("")) { //If the user haven't given any input
            feedbackText.setText(getResources().getString(R.string.giveAnswer));
            feedbackText.setTextColor(Color.RED);
            feedbackIconVisible(false);
            return;
        }

        if (checkAnswer()) {
            feedbackText.setText(getResources().getString(R.string.correct));
            feedbackText.setTextColor(Color.WHITE);
            setFeedbackIconImage(true);
            feedbackIconVisible(true);
            correctAnswers++;
        } else {
            feedbackText.setText(getResources().getString(R.string.wrong));
            feedbackText.setTextColor(Color.RED);
            setFeedbackIconImage(false);
            feedbackIconVisible(true);
            wrongAnswers++;
        }

        if (taskNumber < Data.getNumberOfTasks()) { //Preventing taskNumber to go beyond numberOfTasks
            taskNumber++;
        }

        setTask(taskNumber);
        updateCorrectCounter();
        answerBox.setText("");
    }

    //Gives sets the task shown in taskBox based on the current taskNumber
    private void setTask(int taskNumber) {
        taskBox.setText(tasks[taskNumber]);
    }

    //Updates the text in the taskCounter
    private void updateCorrectCounter() {
        String counter = correctAnswers + "/" + taskNumber;
        correctCounter.setText(counter);
    }

    //Checks if all the tasks have been done
    private boolean gameFinished() {
        return taskNumber >= numberOfTasks;
    }

    //Checks if the player's answer is correct
    private boolean correctAnswer() {
        return Integer.parseInt(answerBox.getText().toString()) == calculateAnswer();
    }

    //Calculates the answer of the current task
    private int calculateAnswer() {
        String[] currentTask = tasks[taskNumber].split(" ");
        return Integer.parseInt(currentTask[0]) + Integer.parseInt(currentTask[2]);
    }

    //Shuffles both arrays by the same random number
    private void shuffleArray() {
        Random rndm = new Random();

        for (int i = 0; i < tasks.length; i++) {
            int randomPosition = rndm.nextInt(tasks.length);
            String taskTemp = tasks[i];

            tasks[i] = tasks[randomPosition];
            tasks[randomPosition] = taskTemp;
        }
    }

    //Sets the feedbackIcon alpha-value to 0 or 255 based on boolean value
    private void feedbackIconVisible(boolean visible) {

        if (visible && feedbackIcon.getImageAlpha() != 255) {
            feedbackIcon.setImageAlpha(255);
        } else if (!visible && feedbackIcon.getImageAlpha() != 0) {
            feedbackIcon.setImageAlpha(0);
        }
    }

    //Sets the feedBackIcon to a cross or a check mark based on a boolean value
    private void setFeedbackIconImage(boolean correct) {
        if (correct) {
            feedbackIcon.setImageResource(R.drawable.check_mark);
        } else {
            feedbackIcon.setImageResource(R.drawable.cross_mark);
        }
    }

    //Initializes all the views
    private void initializeViews() {
        answerBox = findViewById(R.id.answerBox);
        feedbackText = findViewById(R.id.feedbackText);
        feedbackText.setText("");
        feedbackIcon = findViewById(R.id.feedbackIcon);
        feedbackIcon.setImageAlpha(0);
        taskBox = findViewById(R.id.taskBox);
        correctCounter = findViewById(R.id.correctCounter);
    }

    /* ---------- Life-Cycle Methods ------------- */

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        restoreValuesFromBundle(savedInstanceState);
    }

    private void restoreValuesFromBundle(Bundle savedInstanceState) {
        correctCounter.setText(savedInstanceState.getString(TASKCOUNTER_CONTENTS));
        taskNumber = savedInstanceState.getInt(TASKNUMBER_VALUE);
        correctAnswers = savedInstanceState.getInt(CORRECT_VALUE);
        wrongAnswers = savedInstanceState.getInt(WRONG_VALUE);
        tasks = savedInstanceState.getStringArray(TASKS_ARRAY);
        setTask(taskNumber);
        feedbackText.setText("");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        putValuesInBundle(outState);
        super.onSaveInstanceState(outState);
    }

    private void putValuesInBundle(Bundle outState) {
        outState.putString(TASKCOUNTER_CONTENTS, correctCounter.getText().toString());
        outState.putInt(TASKNUMBER_VALUE, taskNumber);
        outState.putInt(CORRECT_VALUE, correctAnswers);
        outState.putInt(WRONG_VALUE, wrongAnswers);
        outState.putStringArray(TASKS_ARRAY, tasks);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("Log", "onPause: Called");
        getSharedPreferences(PREFERENCE, MODE_PRIVATE)
                .edit()
                .putInt(CORRECTPREF, correctAnswers)
                .putInt(WRONGPREF,wrongAnswers)
                .apply();
    }
}
