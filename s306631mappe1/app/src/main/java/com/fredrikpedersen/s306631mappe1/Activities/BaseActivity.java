package com.fredrikpedersen.s306631mappe1.Activities;

import android.content.Context;
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
    public void onBackPressed() { //Returns to the main menu when back is pressed.
        finish();
    }
}
