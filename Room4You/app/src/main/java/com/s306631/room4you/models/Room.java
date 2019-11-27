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

    public Room(int roomId, int buildingId, @NonNull String roomName, int floor, @NonNull String coordinates) {
        this.roomId = roomId;
        this.buildingId = buildingId;
        this.roomName = roomName;
        this.floor = floor;
        this.coordinates = CoordinatesParser.coordinatesFromString(coordinates);
    }

    public Room(int buildingId, @NonNull String roomName, int floor, @NonNull String coordinates) {
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

    public int getBuildingId() {
        return buildingId;
    }

    public String getRoomName() {
        return roomName;
    }

    public int getFloor() {
        return floor;
    }

    public LatLng getCoordinates() {
        return coordinates;
    }

    @NonNull
    public String toString() {
        return roomName;
    }
}
