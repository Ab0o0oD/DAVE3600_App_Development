package com.fredrikpedersen.eatingwithfriends_gradedassignment.ui.friends;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.fredrikpedersen.eatingwithfriends_gradedassignment.R;

public class FriendsFragment extends Fragment {

    public FriendsFragment() {}

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friends, container, false);

        return view;
    }
}