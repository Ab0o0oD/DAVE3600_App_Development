package com.fredrikpedersen.s306631mappe1.Activities;

import com.fredrikpedersen.s306631mappe1.LocaleManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;


/**
 * Class for for implementing logic that all activites are to follow
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleManager.setLocale(base));
    }

    @Override
    public void onBackPressed() { //Returns to the main menu when back is pressed.
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
        finish();
    }
}
