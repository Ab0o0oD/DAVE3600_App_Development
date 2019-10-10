package com.fredrikpedersen.eatingwithfriends_gradedassignment.activities;

import android.os.Bundle;
import android.view.Menu;

import com.fredrikpedersen.eatingwithfriends_gradedassignment.R;

import java.util.Objects;

public class SmsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

}