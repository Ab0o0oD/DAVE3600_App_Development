package com.fredrikpedersen.fragmenttesting;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class VisFilActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visfil_layout);
        Intent i=this.getIntent();
        String link=i.getExtras().getString("link");
        VisFilFragment webfragment=new VisFilFragment();
        webfragment.init(link);
        getSupportFragmentManager().beginTransaction().add(android.R.id.content,webfragment).commit();
    }
}