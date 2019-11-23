package com.s306631.room4you.models;

public class Booking {

    private int bookingId;
    private int roomId;
    private String bookerName;
    private String fromTime;
    private String toTime;
    private String date;

    public Booking(int bookingId, int roomId, String bookerName, String fromTime, String toTime, String date) {
        this.bookingId = bookingId;
        this.roomId = roomId;
        this.bookerName = bookerName;
        this.fromTime = fromTime;
        this.toTime = toTime;
        this.date = date;
    }

    public Booking(int roomId, String bookerName, String fromTime, String toTime, String date) {
        this.roomId = roomId;
        this.bookerName = bookerName;
        this.fromTime = fromTime;
        this.toTime = toTime;
        this.date = date;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getBookerName() {
        return bookerName;
    }

    public void setBookerName(String bookerName) {
        this.bookerName = bookerName;
    }

    public String getFromTime() {
        return fromTime;
    }

    public void setFromTime(String fromTime) {
        this.fromTime = fromTime;
    }

    public String getToTime() {
        return toTime;
    }

    public void setToTime(String toTime) {
        this.toTime = toTime;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String toString() {
        return bookingId + " " + roomId + " " + bookerName + " " + fromTime + " " + toTime + " " + date;
    }
}
