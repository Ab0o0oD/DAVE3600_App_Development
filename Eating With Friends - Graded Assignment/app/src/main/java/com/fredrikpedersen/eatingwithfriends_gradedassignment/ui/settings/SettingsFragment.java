package com.fredrikpedersen.eatingwithfriends_gradedassignment.ui.settings;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
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
    private Button buttonSaveMessage;
    private Button buttonUseDefault;
    private TextView textViewTimePicker;
    private EditText editTextMessage;

    private boolean smsFunctionality;
    private boolean useDefaultMessage;
    private String smsTime;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        smsFunctionality = smsPermission();
        useDefaultMessage = Objects.requireNonNull(getActivity()).getSharedPreferences(StaticHolder.PREFERENCE, Context.MODE_PRIVATE).getBoolean(StaticHolder.USE_DEFAULT_MESSAGE_PREF, true);
        smsTime = Objects.requireNonNull(getActivity()).getSharedPreferences(StaticHolder.PREFERENCE, Context.MODE_PRIVATE).getString(StaticHolder.SMS_TIMING_PREF, "12:00");

        initializeViews(view);
        setOnlicks();

        return view;
    }

    private void setMessageCustom() {
        if (editTextMessage.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "The message box is empty", Toast.LENGTH_SHORT).show();
        } else {
            useDefaultMessage = false;
            String customMessage = editTextMessage.getText().toString();

            Objects.requireNonNull(getActivity()).getSharedPreferences(StaticHolder.PREFERENCE, Context.MODE_PRIVATE)
                    .edit()
                    .putBoolean(StaticHolder.USE_DEFAULT_MESSAGE_PREF, useDefaultMessage)
                    .putString(StaticHolder.CUSTOM_MESSAGE_PREF, customMessage)
                    .apply();

            Toast.makeText(getActivity(), "New message set!", Toast.LENGTH_SHORT).show();
        }
    }

    private void setMessageDefault() {
        useDefaultMessage = true;
        Objects.requireNonNull(getActivity()).getSharedPreferences(StaticHolder.PREFERENCE, Context.MODE_PRIVATE)
                .edit()
                .putBoolean(StaticHolder.USE_DEFAULT_MESSAGE_PREF, useDefaultMessage)
                .apply();

        Toast.makeText(getActivity(), "SMS Message set to default message!", Toast.LENGTH_SHORT).show();
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

        buttonSaveMessage = view.findViewById(R.id.button_save_message);
        buttonUseDefault = view.findViewById(R.id.button_use_default);
        editTextMessage = view.findViewById(R.id.edit_text_write_message);
        textViewTimePicker = view.findViewById(R.id.text_view_time_picker_sms);
        textViewTimePicker.setText(smsTime);
    }

    private void setOnlicks() {
        buttonToggleSms.setOnClickListener(v -> toggleSmsOn());
        buttonUseDefault.setOnClickListener(v -> setMessageDefault());
        buttonSaveMessage.setOnClickListener(v -> setMessageCustom());
        textViewTimePicker.setOnClickListener(v -> showTimePickerDialog());
    }

}