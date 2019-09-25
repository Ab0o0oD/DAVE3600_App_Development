package com.fredrikpedersen.fragmenttesting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements ListeFragment.UrlEndret {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void linkEndret(String link) {
        if (findViewById(R.id.filfragment) != null) {
            VisFilFragment wf = (VisFilFragment) getSupportFragmentManager().findFragmentById(R.id.filfragment);
            if (wf == null) {
                wf = new VisFilFragment();
                wf.init(link);
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction trans = fm.beginTransaction();
                trans.replace(R.id.webside, wf);
                trans.commit();
            } else {
                wf.updateUrl(link);
            }
        } else {
            Intent i = new Intent(this, VisFilActivity.class);
            i.putExtra("link", link);
            startActivity(i);
        }}}
