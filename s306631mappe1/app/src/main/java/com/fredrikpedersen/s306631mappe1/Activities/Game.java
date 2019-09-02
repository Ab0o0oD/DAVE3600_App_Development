package com.fredrikpedersen.s306631mappe1.Activities;

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

import com.fredrikpedersen.s306631mappe1.Data;
import com.fredrikpedersen.s306631mappe1.R;

import java.util.Random;

public class Game extends BaseActivity {

    //Save content Strings
    private final String TASKCOUNTER_CONTENTS = "Task Counter";
    private final String TASKNUMBER_VALUE = "Tasknumber";
    private final String CORRECT_VALUE = "Correct Answers";
    private final String WRONG_VALUE = "Wrong Answers";
    private final String TASKS_ARRAY = "Tasks";

    //Debugging
    private static final String TAG = "GAME";

    //Views
    private EditText answerBox;
    private TextView taskBox;
    private TextView feedbackText;
    private ImageView feedbackIcon;
    private TextView taskCounter;

    //Values
    private String[] tasks;
    private int taskNumber = 0;
    private int correctAnswers = 0;
    private int wrongAnswers = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);

        //Initialize views and values
        initializeViews();
        tasks = getResources().getStringArray(R.array.tasks);

        if (savedInstanceState == null) {
            shuffleArray();
            updateTaskCounter();
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
        Button btn = (Button)view;
        answerBox.setText("");
    }

    //Assigned to the confirmBtn in game_activity.xml
    public void onClickAnswer(View view) {
        if (gameFinished()) {
                Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.done), Toast.LENGTH_SHORT);
                toast.show();
                return;
        }
        Button btn = (Button)view;
        handleAnswer();
    }

    @Override
    public void onBackPressed() {
        if (taskNumber < Data.getNumberOfTasks()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(getResources().getString(R.string.backPressed))
                    .setCancelable(false)
                    .setPositiveButton(getResources().getString(R.string.yes), (dialogInterface, i) -> finish())
                    .setNegativeButton(getResources().getString(R.string.no), null)
                    .show();
        } else {
            Data.incrementCorrect(correctAnswers);
            Data.incrementWrong(wrongAnswers);
            finish();
        }
    }

    /* ---------- Game Methods ------------- */

    private void handleAnswer() {
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

        taskNumber++;
        setTask(taskNumber);
        updateTaskCounter();
        answerBox.setText("");
    }

    //Gives the player the next task from the list
    private void setTask(int taskNumber) {
        taskBox.setText(tasks[taskNumber]);
    }

    private void updateTaskCounter() {
        int maks = Data.getNumberOfTasks();
        String counter = taskNumber + "/" + maks;
        taskCounter.setText(counter);
    }

    //Checks if all the tasks have been done
    private boolean gameFinished() {
        return taskNumber == Data.getNumberOfTasks();
    }

    //Checks if the player's answer is correct
    private boolean checkAnswer() {
        return Integer.parseInt(answerBox.getText().toString()) == calculateAnswer();
    }

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

    private void feedbackIconVisible(boolean visible) {

        if (visible && feedbackIcon.getImageAlpha() != 255) {
            feedbackIcon.setImageAlpha(255);
        } else if (!visible && feedbackIcon.getImageAlpha() != 0) {
            feedbackIcon.setImageAlpha(0);
        }
    }

    private void setFeedbackIconImage(boolean correct) {
        if (correct) { //TODO find out how get current image resource
            feedbackIcon.setImageResource(R.drawable.check_mark);
        } else {
            feedbackIcon.setImageResource(R.drawable.cross_mark);
        }
    }

    /* ---------- Initialization Methods ------------- */

    private void initializeViews() {
        answerBox = findViewById(R.id.answerBox);
        feedbackText = findViewById(R.id.feedbackText);
        feedbackText.setText("");
        feedbackIcon = findViewById(R.id.feedbackIcon);
        feedbackIcon.setImageAlpha(0);
        taskBox = findViewById(R.id.taskBox);
        taskCounter = findViewById(R.id.taskCounter);
    }

    /* ---------- Life-Cycle Methods ------------- */

    @Override
    protected void onStart() {
        Log.d(TAG, "onStart: called");
        super.onStart();
        Log.d(TAG, "onStart: done");
    }

    @Override
    protected void onRestart() {
        Log.d(TAG, "onRestart: called");
        super.onRestart();
        Log.d(TAG, "onRestart: done");
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume: called");
        super.onResume();
        Log.d(TAG, "onResume: done");
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause: called");
        super.onPause();
        Log.d(TAG, "onPause: done");
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "onStop: called");
        super.onStop();
        Log.d(TAG, "onStop: done");
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy: called");
        super.onDestroy();
        Log.d(TAG, "onDestroy: done");
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        Log.d(TAG, "onRestoreInstanceState: called");
        super.onRestoreInstanceState(savedInstanceState);
        taskCounter.setText(savedInstanceState.getString(TASKCOUNTER_CONTENTS));
        taskNumber = savedInstanceState.getInt(TASKNUMBER_VALUE);
        correctAnswers = savedInstanceState.getInt(CORRECT_VALUE);
        wrongAnswers = savedInstanceState.getInt(WRONG_VALUE);
        tasks = savedInstanceState.getStringArray(TASKS_ARRAY);
        setTask(taskNumber);

        Log.d(TAG, "onRestoreInstanceState: done");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.d(TAG, "onSaveInstanceState: called");
        //String content = taskCounter.getText().toString() + correctAnswers + wrongAnswers; //Puts all relevant data into a String
        outState.putString(TASKCOUNTER_CONTENTS, taskCounter.getText().toString());
        outState.putInt(TASKNUMBER_VALUE, taskNumber);
        outState.putInt(CORRECT_VALUE, correctAnswers);
        outState.putInt(WRONG_VALUE, wrongAnswers);
        outState.putStringArray(TASKS_ARRAY, tasks);
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState: done");
    }
}
