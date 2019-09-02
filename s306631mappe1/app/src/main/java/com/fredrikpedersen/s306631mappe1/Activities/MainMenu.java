package com.fredrikpedersen.s306631mappe1.Activities;

import androidx.appcompat.app.AlertDialog;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.fredrikpedersen.s306631mappe1.R;

public class MainMenu extends BaseActivity {

    public static final String TAG = "MainMenu";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);
    }

    /* ----- Button Methods ------ */

    //Assigned to playButton in main_menu.xml
    public void startGame(View view) {
        Intent intent = new Intent(this, Game.class);
        startActivity(intent);
    }

    //Assigned to statsButton in main_menu.xml
    public void showStats(View view) {
        Intent intent = new Intent(this, Stats.class);
        startActivity(intent);
    }

    //Assigned to preferencesButton in main_menu.xml
    public void showPreferences(View view) {
        Intent intent = new Intent(this, Preferences.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {  //Asks the user if they really want to quit the application
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getResources().getString(R.string.backPressed))
                .setCancelable(false)
                .setPositiveButton(getResources().getString(R.string.yes), (dialogInterface, i) -> finish())
                .setNegativeButton(getResources().getString(R.string.no), null)
                .show();
    }
}

