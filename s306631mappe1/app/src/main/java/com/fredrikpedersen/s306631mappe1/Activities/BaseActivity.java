package com.fredrikpedersen.s306631mappe1.Activities;

import com.fredrikpedersen.s306631mappe1.LocaleManager;
import android.content.Context;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

 //Class for for implementing logic that all activites are to follow

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //Is called before onCreate to set the locale to the currently preferred one
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleManager.setLocale(base));
    }

    //Default behaviour for activities is to finish when back is pressed.
    @Override
    public void onBackPressed() {
        finish();
    }
}
