package com.fredrikpedersen.eatingwithfriends_gradedassignment;

import android.os.Bundle;
import android.view.Menu;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initializeToolbar();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    void initializeToolbar() {
        //setSupportActionBar((androidx.appcompat.widget.Toolbar)findViewById(R.id.toolbar));
        //Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
    }

}
