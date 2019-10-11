package com.fredrikpedersen.eatingwithfriends_gradedassignment.ui.bookings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.fredrikpedersen.eatingwithfriends_gradedassignment.R;
import com.fredrikpedersen.eatingwithfriends_gradedassignment.ui.XmlClickable;

public class BookingsFragment extends Fragment implements XmlClickable {

    private BookingsViewModel bookingsViewModel;
    private TextView dateTimePrint;

    private String date = "";
    private String time = "";

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        bookingsViewModel = ViewModelProviders.of(this).get(BookingsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_bookings, container, false);

        dateTimePrint = root.findViewById(R.id.dateTimePrint);

        return root;
    }

    @Override
    public void myClickMethod(View v) {
        switch (v.getId()) {
            case R.id.printBtn:
                dateTimePrint.append("Hello There");
                break;
        default:
            break;
        }
    }
}