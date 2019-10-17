package com.fredrikpedersen.eatingwithfriends_gradedassignment.ui.pickers;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;
import java.util.Objects;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{

    private static final String TAG = "DatePickerFragment";
    private OnPickerValueSelectedListener callback;

    public void setOnDateSelectedListener(OnPickerValueSelectedListener callback) {
        this.callback = callback;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(Objects.requireNonNull(getActivity()), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        String date = day + ". " + getMonthText(month) + " " + year;
        callback.onValueSelected(date);
    }

    private String getMonthText (int month) {
        String monthText = "";
        switch (month) {
            case 1:
                monthText = "Jan";
                break;
            case 2:
                monthText = "Feb";
                break;
            case 3:
                monthText = "Mar";
                break;
            case 4:
                monthText = "Apr";
                break;
            case  5:
                monthText = "May";
                break;
            case 6:
                monthText = "Jun";
                break;
            case 7:
                monthText = "Jul";
                break;
            case 8:
                monthText = "Aug";
                break;
            case 9:
                monthText = "Sep";
                break;
            case 10:
                monthText = "Oct";
                break;
            case 11:
                monthText = "Nov";
                break;
            case 12:
                monthText = "Dec";
                break;
            default:
                break;
        }
        return monthText;
    }
}

