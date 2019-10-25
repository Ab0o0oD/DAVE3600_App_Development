package com.fredrikpedersen.eatingwithfriends_gradedassignment.database.models;

import android.provider.BaseColumns;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = Friend.TABLE_NAME)
public class Friend {

    public static final String TABLE_NAME = "friend_table";
    public static final String COLUMN_ID = BaseColumns._ID;
    private static final String COLUMN_FIRST_NAME = "first_name";
    private static final String COLUMN_LAST_NAME = "last_name";
    private static final String COLUMN_PHONE_NUMBER ="phone_number";

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_ID)
    private int id;

    @ColumnInfo(name = COLUMN_FIRST_NAME)
    private String firstName;

    @ColumnInfo(name = COLUMN_LAST_NAME)
    private String lastName;

    @ColumnInfo(name = COLUMN_PHONE_NUMBER)
    private String phoneNumber;

    public Friend(String firstName, String lastName, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    @NonNull
    @Override
    public String toString() {
        return  firstName + " " + lastName;
    }
}
