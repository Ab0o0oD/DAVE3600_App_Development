package com.fredrikpedersen.eatingwithfriends_gradedassignment.models;

import androidx.annotation.NonNull;

public class Booking {
    private String name;
    private String address;
    private String date;
    private String time;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    @NonNull
    public String toString() {
        return "Name= " + name + '\n' +
                "Address= " + address + '\n' +
                "BookingTime=" + date + " " + time;
    }
}
