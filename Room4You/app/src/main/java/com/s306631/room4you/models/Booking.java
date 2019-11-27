package com.s306631.room4you.models;

import androidx.annotation.NonNull;

public class Booking {

    private int bookingId;
    private int roomId;
    private int buildingId;
    private String bookerName;
    private String fromTime;
    private String toTime;
    private String date;

    public Booking(int bookingId, int roomId, int buildingId, @NonNull String bookerName, @NonNull String fromTime,  @NonNull String toTime, @NonNull String date) {
        this.bookingId = bookingId;
        this.roomId = roomId;
        this.buildingId = buildingId;
        this.bookerName = bookerName;
        this.fromTime = fromTime;
        this.toTime = toTime;
        this.date = date;
    }

    public Booking(int roomId, int buildingId, @NonNull String bookerName, @NonNull String fromTime, @NonNull String toTime, @NonNull String date) {
        this.roomId = roomId;
        this.buildingId = buildingId;
        this.bookerName = bookerName;
        this.fromTime = fromTime;
        this.toTime = toTime;
        this.date = date;
    }

    public int getRoomId() {
        return roomId;
    }

    public int getBuildingId() {
        return buildingId;
    }

    public String getBookerName() {
        return bookerName;
    }

    public String getFromTime() {
        return fromTime;
    }

    public String getToTime() {
        return toTime;
    }

    public String getDate() {
        return date;
    }


    @NonNull
    public String toString() {
        return bookingId + " " + roomId + " " + bookerName + " " + fromTime + " " + toTime + " " + date;
    }
}
