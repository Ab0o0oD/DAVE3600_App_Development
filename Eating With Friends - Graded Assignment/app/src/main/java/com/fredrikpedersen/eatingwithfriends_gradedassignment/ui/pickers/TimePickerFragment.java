package com.fredrikpedersen.eatingwithfriends_gradedassignment.ui.pickers;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    private static final String TAG = "TimePickerFragment";

    private OnPickerValueSelectedListener callback;

    public void setOnTimeSelectedListener(OnPickerValueSelectedListener callback) {
        this.callback = callback;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(), this, hour, minute, true);
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        callback.onValueSelected(formatTime(hourOfDay, minute));
    }

    private String formatTime(int hourOfDay, int minute) {
        String hourString = String.valueOf(hourOfDay);
        String minuteString = String.valueOf(minute);

        if (hourOfDay < 10) {
            hourString = "0" + hourOfDay;
        }

        if (minute < 10) {
            minuteString = "0" + minute;
        }
        return hourString + ":" + minuteString;
    }
}
