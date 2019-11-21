package com.s306631.room4you.models;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;

public class Room {

    private static final String TAG = "Room";

    private int roomId;
    private int buildingId;
    private String roomName;
    private LatLng coordinates;

    public Room(@NonNull int roomId, @NonNull int buildingId, @NonNull String roomName, @NonNull String coordinates) {
        this.roomId = roomId;
        this.buildingId = buildingId;
        this.roomName = roomName;
        this.coordinates = coordinatesFromString(coordinates);
    }

    private LatLng coordinatesFromString(String coordinates) {
        String[] coordinateSplit = coordinates.split(", ");
        double latitude;
        double longtitude;

        try {
            latitude = Double.parseDouble(coordinateSplit[0]);
            longtitude = Double.parseDouble(coordinateSplit[1]);
            return new LatLng(latitude, longtitude);

        } catch (NumberFormatException e) {
            Log.d(TAG, "coordinatesFromString: ");
            return new LatLng(0, 0);
        }
    }

    public String toString() {
        return roomId + " " + buildingId + " " + roomName + " " + coordinates.toString();
    }
}
