package com.s306631.room4you.util;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

public class CoordinatesParser {

    private CoordinatesParser(){}

    private static final String TAG = "CoordinatesParser";

    public static LatLng coordinatesFromString(String coordinates) {
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
}
