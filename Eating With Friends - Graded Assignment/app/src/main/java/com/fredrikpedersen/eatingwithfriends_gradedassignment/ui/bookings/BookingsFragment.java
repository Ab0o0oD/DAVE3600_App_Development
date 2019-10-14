package com.fredrikpedersen.eatingwithfriends_gradedassignment.ui.bookings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.fredrikpedersen.eatingwithfriends_gradedassignment.R;
import com.fredrikpedersen.eatingwithfriends_gradedassignment.ui.pickers.DatePickerFragment;
import com.fredrikpedersen.eatingwithfriends_gradedassignment.ui.pickers.OnPickerValueSelectedListener;
import com.fredrikpedersen.eatingwithfriends_gradedassignment.ui.pickers.TimePickerFragment;

public class BookingsFragment extends Fragment implements OnPickerValueSelectedListener {

    private static final String TAG = "BookingsFragment";
    private final String[] FRAGMENTS = {"timepicker", "datepicker"};
    private TextView dateTimePrint;
    private Button printBtn;
    private Button timeBtn;
    private Button dateBtn;
    private String activePicker;

    private String time = "";
    private String date = "";

    public BookingsFragment() { }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bookings, container, false);

        initializeViews(view);
        setOnClicks();

        return view;
    }


    private void appendText() {
        if (date.equals("") || time.equals("")) {
            dateTimePrint.setText("Date and time now set");
        } else {
            dateTimePrint.setText(date + " " + time);
        }
    }

    /* ----- Initializations ----- */

    private void initializeViews(View view) {
        dateTimePrint = view.findViewById(R.id.dateTimePrint);
        printBtn = view.findViewById(R.id.printBtn);
        timeBtn = view.findViewById(R.id.pickTimeBtn);
        dateBtn = view.findViewById(R.id.pickDateBtn);
    }

    private void setOnClicks() {
        printBtn.setOnClickListener(v -> appendText());
        timeBtn.setOnClickListener(v -> showTimePickerDialog());
        dateBtn.setOnClickListener(v -> showDatePickerDialog());
    }

    /* ----- Date and Time Pickers ----- */

    private void showTimePickerDialog() {
        TimePickerFragment newTimePicker = new TimePickerFragment();
        newTimePicker.setOnTimeSelectedListener(this);
        newTimePicker.show(getChildFragmentManager(), "timePicker");
        activePicker = FRAGMENTS[0];
    }

    private void showDatePickerDialog() {
        DatePickerFragment newDatePicker = new DatePickerFragment();
        newDatePicker.setOnDateSelectedListener(this);
        newDatePicker.show(getChildFragmentManager(), "datePicker");
        activePicker = FRAGMENTS[1];
    }

    @Override
    public void onValueSelected(String data) {
        if (activePicker.equals(FRAGMENTS[0]))
            time = data;
        else if (activePicker.equals(FRAGMENTS[1]))
            date = data;

    }




}