package com.fredrikpedersen.eatingwithfriends_gradedassignment.ui.bookings;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.fredrikpedersen.eatingwithfriends_gradedassignment.R;

public class BookingsFragment extends Fragment {

    public BookingsFragment() { }

    private TextView dateTimePrint;
    private Button printBtn;
    private static final String TAG = "BookingsFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: FRAGMENT IS CREATED");
        View view = inflater.inflate(R.layout.fragment_bookings, container, false);
        dateTimePrint = view.findViewById(R.id.dateTimePrint);
        printBtn = view.findViewById(R.id.printBtn);

        printBtn.setOnClickListener((v) -> appendText());

        return view;
    }

    private void appendText() {
        dateTimePrint.append("Hello There");
    }
}