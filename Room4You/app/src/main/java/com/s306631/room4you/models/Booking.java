package com.s306631.room4you.models;

public class Booking {

    private int bookingId;
    private int roomId;
    private String fromTime;
    private String toTime;

    public Booking(int bookingId, int roomId, String fromTime, String toTime) {
        this.bookingId = bookingId;
        this.roomId = roomId;
        this.fromTime = fromTime;
        this.toTime = toTime;
    }

    public String toString() {
        return bookingId + " " + roomId + " " + fromTime + " " + toTime;
    }
}
