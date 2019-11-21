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

    public String toString() {
        return roomId + " " + buildingId + " " + roomName + " " + floor + " " + coordinates.toString();
    }
}
