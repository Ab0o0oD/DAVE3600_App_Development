package com.fredrikpedersen.eatingwithfriends_gradedassignment.database.models;

import android.content.ContentValues;
import android.provider.BaseColumns;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = Restaurant.TABLE_NAME)
public class Restaurant {

    public static final String TABLE_NAME = "restaurant_table";
    public static final String COLUMN_ID = BaseColumns._ID;
    private static final String COLUMN_RESTAURANT_NAME = "restaurant_name";
    private static final String COLUMN_ADDRESS = "address";
    private static final String COLUMN_PHONE_NUMBER = "phone_number";
    private static final String COLUMN_TYPE = "type";

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_ID)
    private int id;

    @ColumnInfo(name = COLUMN_RESTAURANT_NAME)
    private String restaurantName;

    @ColumnInfo(name = COLUMN_ADDRESS)
    private String address;

    @ColumnInfo(name = COLUMN_PHONE_NUMBER)
    private String phoneNumber;

    @ColumnInfo(name = COLUMN_TYPE)
    private String type;

    private Restaurant() {}

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

    public static Restaurant fromContentValues(ContentValues values) {
        final Restaurant restaurant = new Restaurant();

        if (values.containsKey(COLUMN_ID)) {
            restaurant.id = values.getAsInteger(COLUMN_ID);
        }

        if (values.containsKey(COLUMN_ADDRESS)) {
            restaurant.address = values.getAsString(COLUMN_ADDRESS);
        }

        if (values.containsKey(COLUMN_PHONE_NUMBER)) {
            restaurant.phoneNumber = values.getAsString(COLUMN_PHONE_NUMBER);
        }

        if (values.containsKey(COLUMN_TYPE)) {
            restaurant.type = values.getAsString(COLUMN_TYPE);
        }

        return restaurant;
    }
}
