package com.s306631.room4you.models;

import com.google.android.gms.maps.model.LatLng;
import com.s306631.room4you.util.CoordinatesParser;

public class Building {

    private int buildingId;
    private String buildingName;
    private int floors;
    private LatLng coordinates;

    public Building(int buildingId, String buildingName, int floors, String coordinates) {
        this.buildingId = buildingId;
        this.buildingName = buildingName;
        this.floors = floors;
        this.coordinates = CoordinatesParser.coordinatesFromString(coordinates);
    }

    public String toString() {
        return buildingId + " " + buildingName + " " + floors + " " + coordinates.toString();
    }

}
