package com.fredrikpedersen.eatingwithfriends_gradedassignment.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.fredrikpedersen.eatingwithfriends_gradedassignment.R;
import com.fredrikpedersen.eatingwithfriends_gradedassignment.ui.pickers.DatePickerFragment;
import com.fredrikpedersen.eatingwithfriends_gradedassignment.ui.pickers.OnPickerValueSelectedListener;
import com.fredrikpedersen.eatingwithfriends_gradedassignment.ui.pickers.TimePickerFragment;

public class AddEditBookingActivity extends AppCompatActivity implements OnPickerValueSelectedListener {

    public static final String EXTRA_ID = "com.fredrikpedersen.eatingwithfriends_gradedassignment.activities.EXTRA_ID";
    public static final String EXTRA_RESTAURANT_NAME = "com.fredrikpedersen.eatingwithfriends_gradedassignment.activities.EXTRA_RESTAURANT_NAME";
    public static final String EXTRA_ADDRESS = "com.fredrikpedersen.eatingwithfriends_gradedassignment.activities.EXTRA_ADDRESS";
    public static final String EXTRA_DATE = "com.fredrikpedersen.eatingwithfriends_gradedassignment.activities.EXTRA_DATE";
    public static final String EXTRA_TIME = "com.fredrikpedersen.eatingwithfriends_gradedassignment.activities.EXTRA_TIME";
    public static final String EXTRA_FRIENDS = "com.fredrikpedersen.eatingwithfriends_gradedassignment.activities.EXTRA_RESTAURANT_FRIENDS";

    private EditText editTextRestaurantName;
    private EditText editTextAddress;
    private TextView textViewDate;
    private TextView textViewTime;
    private TextView textViewSelectedFriends;
    private Spinner spinnerFriendSelector;
    private Button buttonSave;

    private final String[] PICKERS = {"timepicker", "datepicker"};
    private String activePicker = "";
    private String time = "";
    private String date = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_booking);
        initializeViews();
        setTitle("Add Booking");

        buttonSave.setOnClickListener(v -> saveBooking());
        textViewDate.setOnClickListener(v -> showDatePickerDialog());
        textViewTime.setOnClickListener(v -> showTimePickerDialog());
    }

    private void saveBooking() {
        String restaurantName = editTextRestaurantName.getText().toString();
        String address = editTextAddress.getText().toString();

        if (restaurantName.trim().isEmpty() || address.trim().isEmpty() || date.equals("") || time.equals("")) {
            Toast.makeText(this, "Please give data in all required fields!", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_RESTAURANT_NAME, restaurantName);
        data.putExtra(EXTRA_ADDRESS, address);
        data.putExtra(EXTRA_DATE, date);
        data.putExtra(EXTRA_TIME, time);
        setResult(RESULT_OK, data);
        finish();
    }

    private void initializeViews() {
        editTextRestaurantName = findViewById(R.id.edit_text_restaurant_name);
        editTextAddress = findViewById(R.id.edit_text_address);
        textViewDate = findViewById(R.id.text_view_date);
        textViewTime = findViewById(R.id.text_view_time);
        textViewSelectedFriends = findViewById(R.id.text_view_selected_friends);
        spinnerFriendSelector = findViewById(R.id.spinner_friend_selector);
        buttonSave = findViewById(R.id.button_save_booking);
    }

    private void showTimePickerDialog() {
        TimePickerFragment timePicker = new TimePickerFragment();
        timePicker.setOnTimeSelectedListener(this);
        timePicker.show(getSupportFragmentManager(), "timePicker");
        activePicker = PICKERS[0];
    }

    private void showDatePickerDialog() {
        DatePickerFragment datePicker = new DatePickerFragment();
        datePicker.setOnDateSelectedListener(this);
        datePicker.show(getSupportFragmentManager(), "datePicker");
        activePicker = PICKERS[1];
    }

    @Override
    public void onValueSelected(String data) {
        if (activePicker.equals(PICKERS[0])) {
            time = data;
            textViewTime.setText(time);
        } else if (activePicker.equals(PICKERS[1])) {
            date = data;
            textViewDate.setText(date);
        }
    }
}
