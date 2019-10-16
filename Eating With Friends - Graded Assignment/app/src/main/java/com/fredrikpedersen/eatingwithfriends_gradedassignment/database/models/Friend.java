package com.fredrikpedersen.eatingwithfriends_gradedassignment.database.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "friend_table")
public class Friend {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String firstName;
    private String lastName;
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
        return  "Id =" + id + '\n' +
                "Name= " + firstName + " " + lastName + '\n' +
                "PhoneNumber='" + phoneNumber + '\n';
    }
}
