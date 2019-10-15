package com.fredrikpedersen.eatingwithfriends_gradedassignment.database.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "friend_table")
public class Friend {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String phoneNumber;

    public Friend(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    @NonNull
    @Override
    public String toString() {
        return  "Id =" + id + '\n' +
                "Name= " + name + '\n' +
                "PhoneNumber='" + phoneNumber + '\n';
    }
}
