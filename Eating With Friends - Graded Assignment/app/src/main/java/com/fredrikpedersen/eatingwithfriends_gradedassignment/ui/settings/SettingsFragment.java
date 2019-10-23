package com.fredrikpedersen.eatingwithfriends_gradedassignment.ui.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.fredrikpedersen.eatingwithfriends_gradedassignment.R;

public class SettingsFragment extends Fragment {

    public SettingsFragment() {}

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);

        initializeViews(view);
        setOnlicks();

        return view;
    }

    private void initializeViews(View view) {
    }

    private void setOnlicks() {
    }

}