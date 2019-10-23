package com.fredrikpedersen.eatingwithfriends_gradedassignment.database.models;

import android.content.ContentValues;
import android.provider.BaseColumns;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.fredrikpedersen.eatingwithfriends_gradedassignment.database.converters.FriendConverter;
import com.fredrikpedersen.eatingwithfriends_gradedassignment.database.converters.RestaurantConverter;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = Booking.TABLE_NAME)
public class Booking {

    public static final String TABLE_NAME = "booking_table";
    public static final String COLUMN_ID = BaseColumns._ID;
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_RESTAURANT = "restaurant";
    public static final String COLUMN_FRIENDS = "friends";

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(index = true, name = COLUMN_ID)
    private int id;

    @ColumnInfo(name = COLUMN_DATE)
    private String date;

    @ColumnInfo(name = COLUMN_TIME)
    private String time;

    @TypeConverters(RestaurantConverter.class)
    @ColumnInfo(name = COLUMN_RESTAURANT)
    private Restaurant restaurant;

    @TypeConverters(FriendConverter.class)
    @ColumnInfo(name = COLUMN_FRIENDS)
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
