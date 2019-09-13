package com.fredrikpedersen.s306631mappe1.Activities;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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

        numberOfTasks = getSharedPreferences(PREFERENCE, MODE_PRIVATE).getInt(NUMBER_OF_TASKS_PREF,5);
        initializeViews();
        initializeSaveContentStrings();
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
            setLocale(LocaleManager.GERMAN);
        } else if (view == findViewById(R.id.norwegianFlag)) {
            setLocale(LocaleManager.NORWEGIAN);
        }
    }

    //Sets the locale and restarts the activity
    private void setLocale(@LocaleManager.LocaleDef String language) {
        LocaleManager.setNewLocale(this, language);
        recreate();
    }

    //Initializes all the views
    private void initializeViews() {
        btn5 = findViewById(R.id.tasksBtn0);
        btn10 = findViewById(R.id.tasksBtn1);
        btn25 = findViewById(R.id.tasksBtn2);

        switch (numberOfTasks) {
            case 5:
                btn5.setBackgroundColor(Color.WHITE);
                break;
            case 10:
                btn10.setBackgroundColor(Color.WHITE);
                break;
            case 25:
                btn25.setBackgroundColor(Color.WHITE);
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

    private void updateButtonPressedStates()  {
        switch (numberOfTasks) {
            case 5:
                btn5.setBackgroundColor(Color.WHITE);
                btn10.setBackgroundResource(android.R.drawable.btn_default);
                btn25.setBackgroundResource(android.R.drawable.btn_default);
                break;
            case 10:
                btn5.setBackgroundResource(android.R.drawable.btn_default);
                btn10.setBackgroundColor(Color.WHITE);
                btn25.setBackgroundResource(android.R.drawable.btn_default);
                break;
            case 25:
                btn5.setBackgroundResource(android.R.drawable.btn_default);
                btn10.setBackgroundResource(android.R.drawable.btn_default);
                btn25.setBackgroundColor(Color.WHITE);
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
