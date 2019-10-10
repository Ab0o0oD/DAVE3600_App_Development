package com.fredrikpedersen.eatingwithfriends_gradedassignment.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import com.fredrikpedersen.eatingwithfriends_gradedassignment.models.RestaurantBooking;

import java.util.List;

public class RestaurantBookingAdapter extends ArrayAdapter {

    private static final String TAG = "RestaurantAdapter";
    private final int layoutResource;
    private final LayoutInflater layoutInflater;
    private final List<RestaurantBooking> restaurantBookings;


    public RestaurantBookingAdapter(@NonNull Context context, int resource, List<RestaurantBooking> restaurantBookings) {
        super(context, resource);
        this.layoutInflater = LayoutInflater.from(context);
        this.layoutResource = resource;
        this.restaurantBookings = restaurantBookings;
    }
}
