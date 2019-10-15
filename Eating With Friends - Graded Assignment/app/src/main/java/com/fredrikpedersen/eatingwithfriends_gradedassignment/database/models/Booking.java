package com.fredrikpedersen.eatingwithfriends_gradedassignment.database.models;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.fredrikpedersen.eatingwithfriends_gradedassignment.database.Converters;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "booking_table")
public class Booking {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;
    private String restaurantName;
    private String address;
    private String date;
    private String time;
    @TypeConverters(Converters.class)
    private List<Friend> friends;

    public Booking(String restaurantName, String address, String date, String time, @Nullable List<Friend> friends) {
        this.restaurantName = restaurantName;
        this.address = address;
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

    public String getRestaurantName() {
        return restaurantName;
    }

    public String getAddress() {
        return address;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
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
                sb.append(friend.getName()).append(" ");
            }
        }

        return "RestaurantName= " + restaurantName + '\n' +
                "Address= " + address + '\n' +
                "BookingTime= " + date + " " + time + '\n' +
                "Friends= " + sb.toString();
    }
}
