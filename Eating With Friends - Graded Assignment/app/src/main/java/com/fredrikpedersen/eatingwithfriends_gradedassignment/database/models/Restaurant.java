package com.fredrikpedersen.eatingwithfriends_gradedassignment.database.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "restaurant_table")
public class Restaurant {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String restaurantName;
    private String address;
    private String phoneNumber;
    private String type;

    public Restaurant(String restaurantName, String address, String phoneNumber, String type) {
        this.restaurantName = restaurantName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getType() {
        return type;
    }

    @NonNull
    @Override
    public String toString() {
        return  restaurantName;
    }
}
