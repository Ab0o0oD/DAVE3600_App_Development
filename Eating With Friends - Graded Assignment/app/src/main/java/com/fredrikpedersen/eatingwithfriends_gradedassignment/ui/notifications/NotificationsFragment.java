package com.fredrikpedersen.eatingwithfriends_gradedassignment.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.fredrikpedersen.eatingwithfriends_gradedassignment.R;

public class NotificationsFragment extends Fragment {

    public NotificationsFragment() {}

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