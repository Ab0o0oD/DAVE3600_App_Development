package com.fredrikpedersen.eatingwithfriends_gradedassignment.activities;

import android.os.Bundle;
import android.view.Menu;

import com.fredrikpedersen.eatingwithfriends_gradedassignment.R;

public class FriendsActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

}
