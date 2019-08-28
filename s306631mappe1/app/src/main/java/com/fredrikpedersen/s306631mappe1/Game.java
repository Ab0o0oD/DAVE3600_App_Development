package com.fredrikpedersen.s306631mappe1;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.Random;

public class Game extends AppCompatActivity {

    private static final String TAG = "GAME";
    private EditText answerBox;
    private TextView taskBox;
    private TextView feedback;
    private TextView taskCounter;
    private String[] tasks;
    private String[] answers;
    private String answer;
    private int taskNumber = 0;
    private int correctAnswers = 0;
    private int wrongAnswers = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);

        initializeViews();
        initializeArrays();
        shuffleArrays();
        updateTaskCounter();
        setTask(taskNumber);
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
                    .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    })
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
            feedback.setText(getResources().getString(R.string.giveAnswer));
            feedback.setTextColor(Color.RED);
            return;
        }

        if (checkAnswer()) {
            feedback.setText(getResources().getString(R.string.correct));
            feedback.setTextColor(Color.WHITE);
            correctAnswers++;
        } else {
            feedback.setText(getResources().getString(R.string.wrong));
            feedback.setTextColor(Color.RED);
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
        answer = answers[taskNumber];
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
        return answerBox.getText().toString().equals(answer);
    }

    //Shuffles both arrays by the same random number
    private void shuffleArrays() {
        System.out.println(Arrays.toString(tasks));
        System.out.println(Arrays.toString(answers));
        Random rndm = new Random();

        for (int i = 0; i < tasks.length; i++) {
            int randomPosition = rndm.nextInt(tasks.length);
            String taskTemp = tasks[i];
            String answerTemp = answers[i];

            tasks[i] = tasks[randomPosition];
            tasks[randomPosition] = taskTemp;

            answers[i] = answers[randomPosition];
            answers[randomPosition] = answerTemp;
        }
        System.out.println(Arrays.toString(tasks));
        System.out.println(Arrays.toString(answers));
    }

    /* ---------- Initialization Methods ------------- */

    private void initializeViews() {
        answerBox = findViewById(R.id.answerBox);
        feedback = findViewById(R.id.feedbackText);
        feedback.setText("");
        taskBox = findViewById(R.id.taskBox);
        taskCounter = findViewById(R.id.taskCounter);
    }

    private void initializeArrays() {
        tasks = getResources().getStringArray(R.array.tasks);
        answers = getResources().getStringArray(R.array.answers);
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
        Log.d(TAG, "onRestoreInstanceState: done");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.d(TAG, "onSaveInstanceState: called");
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState: done");
    }
}
