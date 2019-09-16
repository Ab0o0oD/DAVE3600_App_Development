package com.fredrikpedersen.s306631mappe1.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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

    /*
    Note: A lot of code in this class should probably be moved to a separate Game Logic-class, so
    that the activity classes functions more like standard controllers in line with a MVC-pattern.
    This has not been done due to time constraints with this project.
    */

    //Save content Strings
    private String TASKCOUNTER_CONTENTS;
    private String TASKNUMBER_VALUE;
    private String CORRECT_VALUE;
    private String WRONG_VALUE;
    private String TASKS_ARRAY;
    private String PREFERENCE;
    private String CORRECTPREF;
    private String WRONGPREF;
    private String NUMBER_OF_TASKS_PREF;

    //Views
    private EditText answerBox; //The textbox where the users input is shown
    private TextView taskBox; //Textbox where the current task is shown
    private TextView feedbackText; //Textbox giving feedback to the player in case of correct, wrong or no answer
    private ImageView feedbackIcon; //An icon complementing the feedbackText
    private TextView correctCounter; //Showcases how many correct answers the user have in the current game

    //Values
    private String[] tasks; //Array with all the tasks for the current game
    private int taskNumber = 0; //What task number the player is currently at. Used as the index in the tasks-array
    private int correctAnswers = 0; //How many correct answers the user have in the current session
    private int wrongAnswers = 0; //How many wrong answers the player have in the current session
    private int numberOfTasks; //How many tasks there are to be in total. This is set in the Preferences-activity.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);

        //Initialize views and values
        initializeSaveContentStrings();
        initializeViews();
        initializeValues();

        if (savedInstanceState == null) { //If there is a save state, we do not reshuffle the array and set a new task.
            shuffleArray();
            updateCorrectCounter();
            setTask(taskNumber);
        }
    }

    /* ---------- OnClick Methods ------------- */

    //Assigned to btn0-9 in game_activity.xml. Appends the value of a button to the answerBox
    public void onClickAppend(View view) {
        Button btn = (Button)view;
        answerBox.append(btn.getText().toString());
    }

    //Assigned to the clearBtn in game_activity.xml. Removes all text in the answerBox
    public void onClickRemove(View view) {
        answerBox.setText("");
    }

    //Assigned to the confirmBtn in game_activity.xml.
    public void onClickAnswer(View view) {
        if (gameFinished()) { //Checks if the game is finished. Asks the player if they want to start a new game if it is.
                Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.done), Toast.LENGTH_SHORT);
                toast.show();

                AlertDialog.Builder builder = new AlertDialog.Builder(this); //AlertDialog to check if the user wants to start a new game or quit
                builder.setMessage(getResources().getString(R.string.newGame))
                    .setCancelable(false) //This dialog should not be cancelable
                    .setPositiveButton(getResources().getString(R.string.yes), (dialogInterface, i) -> { //Starts a new game if the user gives a positive response
                        finish();
                        Intent intent = new Intent(this, this.getClass());
                        startActivity(intent);
                    })
                    .setNegativeButton(getResources().getString(R.string.no), (dialogInterface, i) -> finish()) //Finishes the activity if the user gives a negative response
                    .show();
        }
        handleAnswer();
    }

    @Override
    public void onBackPressed() {
        if (!gameFinished()) { //If the player haven't finished all tasks, ask if they really want to leave the activity
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(getResources().getString(R.string.backPressed))
                    .setPositiveButton(getResources().getString(R.string.yes), (dialogInterface, i) ->  {
                        finish();
                        correctAnswers = 0;
                        wrongAnswers = 0;
                    })
                    .setNegativeButton(getResources().getString(R.string.no), null)
                    .show();
        } else { //Close the activity if the player has finished the game
            finish();
        }
    }

    /* ---------- Game Methods ------------- */

    //Handles no, wrong or correct answer input from the user
    private void handleAnswer() {
        if (gameFinished()) { //Got a bug where the game counts one extra wrong answer if the user tries to input an extra answer after the game is finished. Might want to find a better solution for this.
            return;
        }

        if (answerBox.getText().toString().equals("")) { //If the user haven't given any input, a message appear prompting them to give an input
            feedbackText.setText(getResources().getString(R.string.giveAnswer));
            feedbackText.setTextColor(Color.RED);
            feedbackIconVisible(false);
            return;
        }

        if (correctAnswer()) { //If the user's answer is correct
            feedbackText.setText(getResources().getString(R.string.correct));
            feedbackText.setTextColor(Color.WHITE);
            setFeedbackIconImage(true);
            feedbackIconVisible(true);
            correctAnswers++;

        } else { //If the user's answer is not empty or correct, it is wrong
            feedbackText.setText(getResources().getString(R.string.wrong));
            feedbackText.setTextColor(Color.RED);
            setFeedbackIconImage(false);
            feedbackIconVisible(true);
            wrongAnswers++;
        }

        if (taskNumber < numberOfTasks) { //Preventing taskNumber from going beyond numberOfTasks
            taskNumber++;
            setTask(taskNumber);
        }

        updateCorrectCounter();
        answerBox.setText(""); //Clear the answerbox after each answer from the user
    }

    //Sets the task shown in taskBox based on the current taskNumber
    private void setTask(int taskNumber) {
        taskBox.setText(tasks[taskNumber]);
    }

    //Updates the text in the taskCounter
    private void updateCorrectCounter() {
        String counter = correctAnswers + "/" + taskNumber;
        correctCounter.setText(counter);
    }

    //Checks if all the tasks have been completed
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

    //Randomly shuffles the task array
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

    //Sets the feedBackIcon image based on a boolean value
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

    //Initialize all values
    private void initializeValues() {
        tasks = getResources().getStringArray(R.array.tasks);
        numberOfTasks = getSharedPreferences(PREFERENCE, MODE_PRIVATE).getInt(NUMBER_OF_TASKS_PREF,5);
    }

    //Initialize all save content Strings
    private void initializeSaveContentStrings() {
        TASKCOUNTER_CONTENTS = getResources().getString(R.string.TASKCOUNTER_CONTENTS);
        TASKNUMBER_VALUE = getResources().getString(R.string.TASKNUMBER_VALUE);
        CORRECT_VALUE = getResources().getString(R.string.CORRECT_VALUE);
        WRONG_VALUE = getResources().getString(R.string.WRONG_VALUE);
        TASKS_ARRAY = getResources().getString(R.string.TASKS_ARRAY);
        PREFERENCE = getResources().getString(R.string.PREFERENCE);
        CORRECTPREF = getResources().getString(R.string.CORRECTPREF);
        WRONGPREF = getResources().getString(R.string.WRONGPREF);
        NUMBER_OF_TASKS_PREF = getResources().getString(R.string.NUMBER_OF_TASKS_PREF);
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
        int savedCorrect = getSharedPreferences(PREFERENCE, MODE_PRIVATE).getInt(CORRECTPREF,0) + correctAnswers;
        int savedWrong = getSharedPreferences(PREFERENCE, MODE_PRIVATE).getInt(WRONGPREF,0) + wrongAnswers;
        getSharedPreferences(PREFERENCE, MODE_PRIVATE)
                .edit()
                .putInt(CORRECTPREF, savedCorrect)
                .putInt(WRONGPREF,savedWrong)
                .apply();
    }
}
