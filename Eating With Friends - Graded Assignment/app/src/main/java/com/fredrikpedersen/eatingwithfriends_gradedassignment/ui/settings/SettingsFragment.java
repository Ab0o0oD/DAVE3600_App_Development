package com.fredrikpedersen.eatingwithfriends_gradedassignment.ui.settings;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.fredrikpedersen.eatingwithfriends_gradedassignment.R;
import com.fredrikpedersen.eatingwithfriends_gradedassignment.ui.pickers.OnPickerValueSelectedListener;
import com.fredrikpedersen.eatingwithfriends_gradedassignment.ui.pickers.TimePickerFragment;
import com.fredrikpedersen.eatingwithfriends_gradedassignment.util.StaticHolder;

import java.util.Objects;

public class SettingsFragment extends Fragment implements OnPickerValueSelectedListener {

    private static final String TAG = "SettingsFragment";

    private ToggleButton buttonToggleSms;
    private Button buttonUseDefault;
    private TextView textViewTimePicker;
    private ImageView imageViewTimePicker;
    private EditText editTextMessage;

    private boolean smsFunctionality;
    private String smsTime;
    private String smsMessage;
    private String smsDefault;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        smsFunctionality = smsPermission();
        smsDefault = Objects.requireNonNull(getActivity()).getResources().getString(R.string.default_message);
        smsMessage = Objects.requireNonNull(getActivity().getSharedPreferences(StaticHolder.PREFERENCE, Context.MODE_PRIVATE).getString(StaticHolder.SMS_MESSAGE_PREF, smsDefault));
        smsTime = Objects.requireNonNull(getActivity()).getSharedPreferences(StaticHolder.PREFERENCE, Context.MODE_PRIVATE).getString(StaticHolder.SMS_TIMING_PREF, "12:00");

        initializeViews(view);
        setOnlicks();

        return view;
    }

    private void setMessageDefault() {
        editTextMessage.setText(smsDefault);
    }

    private void toggleSmsOn() {
        if (!smsPermission()) {
            requestSmsPermission();
            buttonToggleSms.setChecked(smsFunctionality);
            return;
        }

        smsFunctionality = buttonToggleSms.getText().toString().toLowerCase().equals("on");
        Objects.requireNonNull(getActivity()).getSharedPreferences(StaticHolder.PREFERENCE, Context.MODE_PRIVATE)
                .edit()
                .putBoolean(StaticHolder.SMS_FUNCTIONALITY_PREF, smsFunctionality)
                .apply();

        buttonToggleSms.setChecked(smsFunctionality);
    }

    private void requestSmsPermission() {
        new AlertDialog.Builder(Objects.requireNonNull(getActivity()))
                .setTitle("SMS Permission Needed")
                .setMessage("We need your permission to send SMS in order to turn this on")
                .setPositiveButton("ok", (dialog, which) -> ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.SEND_SMS}, StaticHolder.SMS_PERMISSION_CODE))
                .setNegativeButton("cancel", (dialog, which) -> dialog.dismiss())
                .create()
                .show();
    }

    private boolean smsPermission() {
        return !ActivityCompat.shouldShowRequestPermissionRationale(Objects.requireNonNull(getActivity()), Manifest.permission.SEND_SMS);
    }

    private void showTimePickerDialog() {
        TimePickerFragment timePicker = new TimePickerFragment();
        timePicker.setOnTimeSelectedListener(this);
        timePicker.show(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), "timePicker");
    }

    @Override
    public void onValueSelected(String data) {
        smsTime = data;
        textViewTimePicker.setText(data);

        Objects.requireNonNull(getActivity()).getSharedPreferences(StaticHolder.PREFERENCE, Context.MODE_PRIVATE)
                .edit()
                .putString(StaticHolder.SMS_TIMING_PREF, smsTime)
                .apply();

        restartReminderService();
    }

    private void restartReminderService() {
        Intent intent = new Intent();
        intent.setAction(StaticHolder.REMINDER_BROADCAST);
        Objects.requireNonNull(getActivity()).sendBroadcast(intent);
    }

    private void initializeViews(View view) {
        buttonToggleSms = view.findViewById(R.id.toggle_button_sms);
        buttonToggleSms.setChecked(smsFunctionality);

        buttonUseDefault = view.findViewById(R.id.button_use_default);
        editTextMessage = view.findViewById(R.id.edit_text_write_message);
        editTextMessage.setText(smsMessage);
        textViewTimePicker = view.findViewById(R.id.text_view_time_picker_sms);
        textViewTimePicker.setText(smsTime);
        imageViewTimePicker = view.findViewById(R.id.image_view_select_time);
    }

    private void setOnlicks() {
        buttonToggleSms.setOnClickListener(v -> toggleSmsOn());
        buttonUseDefault.setOnClickListener(v -> setMessageDefault());
        textViewTimePicker.setOnClickListener(v -> showTimePickerDialog());
        imageViewTimePicker.setOnClickListener(v -> showTimePickerDialog());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        smsMessage = editTextMessage.getText().toString();

        Objects.requireNonNull(getActivity()).getSharedPreferences(StaticHolder.PREFERENCE, Context.MODE_PRIVATE)
                .edit()
                .putString(StaticHolder.SMS_MESSAGE_PREF, smsMessage)
                .apply();
    }
}