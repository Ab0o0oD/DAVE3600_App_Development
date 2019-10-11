package com.fredrikpedersen.eatingwithfriends_gradedassignment.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import com.fredrikpedersen.eatingwithfriends_gradedassignment.models.Booking;

import java.util.List;

public class BookingAdapter extends ArrayAdapter {

    private static final String TAG = "BookingAdapter";
    private final int layoutResource;
    private final LayoutInflater layoutInflater;
    private final List<Booking> restaurantBookings;


    public BookingAdapter(@NonNull Context context, int resource, List<Booking> restaurantBookings) {
        super(context, resource);
        this.layoutInflater = LayoutInflater.from(context);
        this.layoutResource = resource;
        this.restaurantBookings = restaurantBookings;
    }
}
