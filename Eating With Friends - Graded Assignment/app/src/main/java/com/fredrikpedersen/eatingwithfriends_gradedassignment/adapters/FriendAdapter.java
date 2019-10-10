package com.fredrikpedersen.eatingwithfriends_gradedassignment.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import com.fredrikpedersen.eatingwithfriends_gradedassignment.models.Friend;

import java.util.List;

public class FriendAdapter extends ArrayAdapter {

    private static final String TAG = "FriendAdapter";
    private final int layoutResource;
    private final LayoutInflater layoutInflater;
    private final List<Friend> friends;

    public FriendAdapter(@NonNull Context context, int resource, List<Friend> friends) {
        super(context, resource);
        this.layoutInflater = LayoutInflater.from(context);
        this.layoutResource = resource;
        this.friends = friends;
    }
}
