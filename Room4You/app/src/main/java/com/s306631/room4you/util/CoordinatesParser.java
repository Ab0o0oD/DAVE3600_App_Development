package com.s306631.room4you.util;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

public class CoordinatesParser {

    private CoordinatesParser(){}

    private static final String TAG = "CoordinatesParser";

    public static LatLng coordinatesFromString(String coordinates) {
        String[] coordinateSplit = coordinates.split(", ");
        double latitude;
        double longitude;

        try {
            latitude = Double.parseDouble(coordinateSplit[0]);
            longitude = Double.parseDouble(coordinateSplit[1]);
            return new LatLng(latitude, longitude);

        } catch (NumberFormatException e) {
            Log.d(TAG, "coordinatesFromString: ");
            return new LatLng(0, 0);
        }
    }

    public static String stringFromCoordinates(LatLng coordinates) {
        return coordinates.latitude + ", " + coordinates.longitude;
    }
}
