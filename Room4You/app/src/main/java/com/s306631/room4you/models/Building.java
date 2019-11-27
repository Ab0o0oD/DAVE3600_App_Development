package com.s306631.room4you.models;

import androidx.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;
import com.s306631.room4you.util.CoordinatesParser;

public class Building {

    private int buildingId;
    private String buildingName;
    private int floors;
    private LatLng coordinates;

    public Building(int buildingId, @NonNull String buildingName, int floors, @NonNull String coordinates) {
        this.buildingId = buildingId;
        this.buildingName = buildingName;
        this.floors = floors;
        this.coordinates = CoordinatesParser.coordinatesFromString(coordinates);
    }

    public Building(@NonNull String buildingName, int floors, @NonNull String coordinates) {
        this.buildingName = buildingName;
        this.floors = floors;
        this.coordinates = CoordinatesParser.coordinatesFromString(coordinates);
    }

    public int getBuildingId() {
        return buildingId;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public int getFloors() {
        return floors;
    }

    public LatLng getCoordinates() {
        return coordinates;
    }

    public String getCoordinatesAsString() {
        return CoordinatesParser.stringFromCoordinates(coordinates);
    }

    @NonNull
    public String toString() {
        return buildingName;
    }

}
