package com.s306631.room4you.repository;

import android.os.AsyncTask;
import android.util.Log;

import com.s306631.room4you.models.Building;
import com.s306631.room4you.models.Room;
import com.s306631.room4you.util.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class BuildingRepository {

    private static final String TAG = "BuildingRepository";

    public List<Building> getBuildingsFromWebService() {
        GetBuildings task = new GetBuildings();

        try {
            return task.execute("http://student.cs.hioa.no/~s306631/buildingsjsonout.php").get();

        } catch (InterruptedException | ExecutionException e) {
            Log.d(TAG, "getRoomsFromWebService: Interrupted Exception | Execution Exception: " + e.getMessage());
        }

        return new ArrayList<>();
    }

    private static class GetBuildings extends AsyncTask<String, Void, List<Building>> {

        @Override
        protected List<Building> doInBackground(String... urls) {
            Building objectFromJson;
            List<Building> buildingList = new ArrayList<>();
            JSONArray buildings = JsonParser.createJsonArray(urls[0]);

            if (buildings != null) {
                try {

                    for (int i = 0; i < buildings.length(); i++) {
                        JSONObject jsonobject = buildings.getJSONObject(i);
                        int buildingId = jsonobject.getInt("BuildingID");
                        String buildingName = jsonobject.getString("Name");
                        String coordinates = jsonobject.getString("Coordinates");
                        objectFromJson = new Building(buildingId, buildingName, coordinates);

                        buildingList.add(objectFromJson);
                    }

                } catch (JSONException e) {
                    Log.d(TAG, "doInBackground: JSONException" + e.getMessage());
                    e.printStackTrace();
                }

                return buildingList;
            }

            return new ArrayList<>();
        }
    }

}


