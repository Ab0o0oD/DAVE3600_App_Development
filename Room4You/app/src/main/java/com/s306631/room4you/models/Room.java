package com.s306631.room4you.models;

import androidx.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;
import com.s306631.room4you.util.CoordinatesParser;

public class Room {

    private static final String TAG = "Room";

    private int roomId;
    private int buildingId;
    private String roomName;
    private int floor;
    private LatLng coordinates;

    public Room(@NonNull int roomId, @NonNull int buildingId, @NonNull String roomName, @NonNull int floor, @NonNull String coordinates) {
        this.roomId = roomId;
        this.buildingId = buildingId;
        this.roomName = roomName;
        this.floor = floor;
        this.coordinates = CoordinatesParser.coordinatesFromString(coordinates);
    }

    public Room(@NonNull int buildingId, @NonNull String roomName, @NonNull int floor, @NonNull String coordinates) {
        this.buildingId = buildingId;
        this.roomName = roomName;
        this.floor = floor;
        this.coordinates = CoordinatesParser.coordinatesFromString(coordinates);
    }

    public static String getTAG() {
        return TAG;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(int buildingId) {
        this.buildingId = buildingId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public LatLng getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(LatLng coordinates) {
        this.coordinates = coordinates;
    }

    public String toString() {
        return roomName;
    }
}
