package com.fredrikpedersen.eatingwithfriends_gradedassignment.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.fredrikpedersen.eatingwithfriends_gradedassignment.R;
import com.fredrikpedersen.eatingwithfriends_gradedassignment.fragments.DatePickerFragment;
import com.fredrikpedersen.eatingwithfriends_gradedassignment.fragments.OnValueSelectedListener;
import com.fredrikpedersen.eatingwithfriends_gradedassignment.fragments.TimePickerFragment;

public class RestaurantBookingActivity extends BaseActivity implements OnValueSelectedListener {

    private static final String TAG = "RestaurantBookingActiv";
    private String[] fragments = {"datePicker", "timePicker"};
    private String activeFragment = "";
    private String date = "";
    private String time = "";
    private TextView dateTimePrint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants);

        initializeViews();
    }

    public void printDateAndTime(View view) {
        if (time.length() == 0 || date.length() == 0) {
            dateTimePrint.append(getResources().getString(R.string.placeholder));
        } else  {
            dateTimePrint.setText(date);
            dateTimePrint.append(time);
        }

    }

    private void initializeViews() {
        this.dateTimePrint = findViewById(R.id.dateTimePrint);
    }


    /* -------------- Fragment Methods -------------- */

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    @Override
    public void onValueSelected(String data) {
        Log.d(TAG, "onDataSelected IN ACTICITY: " + data);
        if (activeFragment.equalsIgnoreCase(fragments[0])) {
            date = data;
        } else if (activeFragment.equalsIgnoreCase(fragments[1])) {
            time = data;
        }
    }


    /* -------------- Life-Cycle Methods -------------- */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public void onAttachFragment(@NonNull Fragment fragment) { //TODO make this into a Switch/Case if more fragments are being implemented
        if (fragment instanceof DatePickerFragment) {
            DatePickerFragment datePickerFragment = (DatePickerFragment) fragment;
            datePickerFragment.setOnDateSelectedListener(this);
            activeFragment = fragments[0];
        } else if (fragment instanceof TimePickerFragment) {
            TimePickerFragment timePickerFragment = (TimePickerFragment) fragment;
            timePickerFragment.setOnDateSelectedListener(this);
            activeFragment = fragments[1];
        }
    }
}
