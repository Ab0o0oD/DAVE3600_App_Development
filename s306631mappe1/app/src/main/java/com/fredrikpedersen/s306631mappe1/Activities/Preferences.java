package com.fredrikpedersen.s306631mappe1.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.fredrikpedersen.s306631mappe1.LocaleManager;
import com.fredrikpedersen.s306631mappe1.R;

public class Preferences extends BaseActivity {

    //Save content Strings
    private String PREFERENCE;
    private String NUMBER_OF_TASKS_PREF;

    //Views
    private Button btn5;
    private Button btn10;
    private Button btn25;

    //Variables
    private int numberOfTasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preferences_activity);

        initializeViews();
        initializeSaveContentStrings();
        numberOfTasks = getSharedPreferences(PREFERENCE, MODE_PRIVATE).getInt(NUMBER_OF_TASKS_PREF,5);
    }

    //Assigned to tasksBtn0-2 in preferences_activity.xml
    public void chooseNumberOfTasks(View view) {
        Button btn = (Button)view;
        numberOfTasks = (Integer.valueOf(btn.getText().toString()));
        updateButtonPressedStates();
    }

    //Assigned to norwegianFlag and germanFlag in preferences_activity.xml
    public void selectLanguage(View view) {
        if (view == findViewById(R.id.germanFlag)) { //Make this if-else into a switch/case if more languages are added
            setLocale(this, LocaleManager.GERMAN);
        } else if (view == findViewById(R.id.norwegianFlag)) {
            setLocale(this, LocaleManager.NORWEGIAN);
        }
    }

    //Sets the locale and restarts the activity
    private void setLocale(AppCompatActivity context, @LocaleManager.LocaleDef String language) {
        LocaleManager.setNewLocale(this, language);
        Intent intent = context.getIntent();
        startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    //Initializes all the views
    private void initializeViews() {
        btn5 = findViewById(R.id.tasksBtn0);
        btn10 = findViewById(R.id.tasksBtn1);
        btn25 = findViewById(R.id.tasksBtn2);

        switch (numberOfTasks) {
            case 5:
                btn5.setPressed(true);
                break;
            case 10:
                btn10.setPressed(true);
                break;
            case 25:
                btn25.setPressed(true);
                break;
            default:
                break;
        }
    }

    //Initialize all the Save Content Strings
    private void initializeSaveContentStrings() {
        PREFERENCE = getResources().getString(R.string.PREFERENCE);
        NUMBER_OF_TASKS_PREF = getResources().getString(R.string.NUMBER_OF_TASKS_PREF);
    }

    private void updateButtonPressedStates() { //TODO Fungerer ikke. Prat med Anders!
        switch (numberOfTasks) {
            case 5:
                btn5.setPressed(true);
                btn10.setPressed(false);
                btn25.setPressed(false);
                break;
            case 10:
                btn5.setPressed(false);
                btn10.setPressed(true);
                btn25.setPressed(false);
                break;
            case 25:
                btn5.setPressed(false);
                btn10.setPressed(false);
                btn25.setPressed(true);
                break;
            default:
                break;
        }
    }

    /* ---------- Life-Cycle Methods ------------- */

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getSharedPreferences(PREFERENCE, MODE_PRIVATE)
                .edit()
                .putInt(NUMBER_OF_TASKS_PREF, numberOfTasks)
                .apply();
    }
}
