package com.fredrikpedersen.s306631mappe1.Activities;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.fredrikpedersen.s306631mappe1.LocaleManager;
import com.fredrikpedersen.s306631mappe1.R;

import java.util.Locale;

public class Preferences extends BaseActivity {

    //Save content Strings
    private String PREFERENCE;
    private String NUMBER_OF_TASKS_PREF;

    //Views
    private Button btn5; //Button for setting numberOfTasks to 5
    private Button btn10; //Button for setting numberOfTasks to 10
    private Button btn25; //Button for setting numberOfTasks to 25
    private ImageView norFlag; //A clickable ImageView showing the Norwegian flag
    private ImageView gerFlag; //A clickable ImageView showing the German flag

    //Variables
    private int numberOfTasks; //How many tasks there are to be in total.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preferences_activity);

        numberOfTasks = getSharedPreferences(PREFERENCE, MODE_PRIVATE).getInt(NUMBER_OF_TASKS_PREF,5);
        initializeViews();
        initializeSaveContentStrings();
        updateFlags();
    }

    //Assigned to tasksBtn0-2 in preferences_activity.xml. numberOfTasks is set equal to the text value on the button.
    public void chooseNumberOfTasks(View view) {
        Button btn = (Button)view;
        numberOfTasks = (Integer.valueOf(btn.getText().toString()));
        updateButtonPressedStates();
    }

    //Assigned to norwegianFlag and germanFlag in preferences_activity.xml. Calls setLocale() with a parameter based on which button is pressed
    public void selectLanguage(View view) {
        //Make this if-else into a switch/case if more languages are added
        if (view == findViewById(R.id.germanFlag)) {
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

    //Greys out and disables clicking on the currently selected locale's flag
    private void updateFlags() {
        Locale currentLocale = LocaleManager.getLocale(this.getResources());

        if (currentLocale.toString().equals(LocaleManager.GERMAN)) {
            gerFlag.setImageResource(R.drawable.gerflag_bw_300x500);
            gerFlag.setClickable(false);
            norFlag.setImageResource(R.drawable.norflag_300x500);
            norFlag.setClickable(true);
        } else {
            gerFlag.setImageResource(R.drawable.gerflag_300x500);
            gerFlag.setClickable(true);
            norFlag.setImageResource(R.drawable.norflag_bw_300x500);
            norFlag.setClickable(false);
        }
    }

    //Makes a button appear to be pressed in, in accordance with the value of numberOfTasks. Resets the two other buttons to default appearance values.
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

    //Initializes all the views
    private void initializeViews() {
        norFlag = findViewById(R.id.norwegianFlag);
        gerFlag = findViewById(R.id.germanFlag);
        btn5 = findViewById(R.id.tasksBtn0);
        btn10 = findViewById(R.id.tasksBtn1);
        btn25 = findViewById(R.id.tasksBtn2);

        //Makes a button appear to be pressed in, in accordance with the value of numberOfTasks
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
