package com.fredrikpedersen.eatingwithfriends_gradedassignment.models;

import androidx.annotation.NonNull;

public class Friend {

    private String name;
    private String phoneNumber;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @NonNull
    @Override
    public String toString() {
        return "Name= " + name + '\n' +
                "PhoneNumber='" + phoneNumber + '\n';
    }
}
