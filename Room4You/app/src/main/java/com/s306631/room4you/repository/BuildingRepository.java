package com.s306631.room4you.repository;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.s306631.room4you.activities.AddDeleteBuildingActivity;
import com.s306631.room4you.activities.BookRoomActivity;
import com.s306631.room4you.models.Booking;
import com.s306631.room4you.models.Building;
import com.s306631.room4you.models.Room;
import com.s306631.room4you.util.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
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
    public void postBuilding(AddDeleteBuildingActivity context, Building building) {
        new BuildingRepository.PostBuilding(context).execute(building);
    }
    public void deleteBuilding(AddDeleteBuildingActivity context, Building building) {
        new BuildingRepository.DeleteBuilding(context).execute(building);
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
                        int floors = jsonobject.getInt("Floors");
                        String coordinates = jsonobject.getString("Coordinates");
                        objectFromJson = new Building(buildingId, buildingName, floors, coordinates);
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
    private static class PostBuilding extends AsyncTask<Building, Void, Void> {

        private WeakReference<AddDeleteBuildingActivity> activityReference;

        private PostBuilding(AddDeleteBuildingActivity context) {
            this.activityReference = new WeakReference<>(context);
        }

        @Override
        protected Void doInBackground(Building... buildings) {
            Building building = buildings[0];
            String urlString = ("http://student.cs.hioa.no/~s306631/buildingjsonin.php/?" +
                    "bName=" + building.getBuildingName() +
                    "&floors=" + building.getFloors() +
                    "&latLng=" + building.getCoordinatesAsString())
                    .replaceAll(" ", "%20");

            try {

                URL url = new URL(urlString);
                Log.d(TAG, "postBuilding: CONNECTING TO URL" + url.toString());
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Accept", "application/json");

                int status = urlConnection.getResponseCode();
                if (status != 200) {
                    throw new RuntimeException("Failed to get response from server, HTTP response code: " + urlConnection.getResponseCode());
                }

                urlConnection.disconnect();

            } catch (MalformedURLException e) {
                Log.d(TAG, "postBuilding: MalformedURLException " + e.getMessage());
            } catch (ProtocolException e) {
                Log.d(TAG, "postBuilding: ProtocolException " + e.getMessage());
            } catch (IOException e) {
                Log.d(TAG, "postBuilding: IOException " + e.getMessage());
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            AddDeleteBuildingActivity activity = activityReference.get();
            Toast.makeText(activity, "Building Added", Toast.LENGTH_SHORT).show();
            activity.fillBuildingSpinner();
        }
    }
    private static class DeleteBuilding extends AsyncTask<Building, Void, Void> {

        private WeakReference<AddDeleteBuildingActivity> activityReference;

        private DeleteBuilding(AddDeleteBuildingActivity context) {
            this.activityReference = new WeakReference<>(context);
        }

        @Override
        protected Void doInBackground(Building... buildings) {
            Building building = buildings[0];
            String urlString = "http://student.cs.hioa.no/~s306631/deletebuilding.php/?" +
                    "buildingId=" + building.getBuildingId();

            try {

                URL url = new URL(urlString);
                Log.d(TAG, "deleteBuilding: CONNECTING TO URL" + url.toString());
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Accept", "application/json");

                int status = urlConnection.getResponseCode();
                if (status != 200) {
                    throw new RuntimeException("Failed to get response from server, HTTP response code: " + urlConnection.getResponseCode());
                }

                urlConnection.disconnect();

            } catch (MalformedURLException e) {
                Log.d(TAG, "deleteBuilding: MalformedURLException " + e.getMessage());
            } catch (ProtocolException e) {
                Log.d(TAG, "deleteBuilding: ProtocolException " + e.getMessage());
            } catch (IOException e) {
                Log.d(TAG, "deleteBuilding: IOException " + e.getMessage());
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            AddDeleteBuildingActivity activity = activityReference.get();
            Toast.makeText(activity, "Building Deleted", Toast.LENGTH_SHORT).show();
            activity.fillBuildingSpinner();
        }
    }

}


