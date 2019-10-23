package com.fredrikpedersen.eatingwithfriends_gradedassignment.database.models;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.fredrikpedersen.eatingwithfriends_gradedassignment.database.converters.FriendConverter;
import com.fredrikpedersen.eatingwithfriends_gradedassignment.database.converters.RestaurantConverter;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "booking_table")
public class Booking {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;
    private String date;
    private String time;

    @TypeConverters(RestaurantConverter.class)
    private Restaurant restaurant;

    @TypeConverters(FriendConverter.class)
    private List<Friend> friends;

    public Booking(Restaurant restaurant, String date, String time, @Nullable List<Friend> friends) {
        this.restaurant = restaurant;
        this.date = date;
        this.time = time;
        this.friends = friends;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public List<Friend> getFriends() {
        if (friends != null) {
            return friends;
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    @NonNull
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (friends != null && friends.size() > 0) {
            for (Friend friend : friends) {
                sb.append(friend.getFirstName()).append(" ").append(friend.getLastName()).append(" ");
            }
        }

        return  restaurant.toString() +
                "BookingTime= " + date + " " + time + '\n' +
                "Friends= " + sb.toString();
    }
}
