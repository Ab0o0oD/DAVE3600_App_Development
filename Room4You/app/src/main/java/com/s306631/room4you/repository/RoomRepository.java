package com.s306631.room4you.repository;

import android.os.AsyncTask;
import android.util.Log;

import com.s306631.room4you.models.Room;
import com.s306631.room4you.util.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class RoomRepository {

    private static final String TAG = "RoomRepository";

    public RoomRepository() {
    }

    public List<Room> getRoomsFromWebService() {
        GetRooms task = new GetRooms();

        try {
            return task.execute("http://student.cs.hioa.no/~s306631/jsonout.php").get();

        } catch (InterruptedException | ExecutionException e) {
            Log.d(TAG, "getRoomsFromWebService: Interrupted Exception | Execution Exception: " + e.getMessage());
        }

        return new ArrayList<>();
    }

    private static class GetRooms extends AsyncTask<String, Void, List<Room>> {

        @Override
        protected List<Room> doInBackground(String... urls) {
            Room objectFromJson;
            List<Room> roomList = new ArrayList<>();
            JSONArray rooms = JsonParser.createJsonArray(urls[0]);

            if (rooms != null) {
                try {

                    for (int j = 0; j < rooms.length(); j++) {
                        JSONObject jsonobject = rooms.getJSONObject(j);
                        int roomId = jsonobject.getInt("RoomID");
                        int buildingId = jsonobject.getInt("BuildingID");
                        String roomName = jsonobject.getString("Name");
                        String coordinates = jsonobject.getString("Coordinates");
                        objectFromJson = new Room(roomId, buildingId, roomName, coordinates);

                        roomList.add(objectFromJson);
                    }

                } catch (JSONException e) {
                    Log.d(TAG, "doInBackground: JSONException" + e.getMessage());
                    e.printStackTrace();
                }

                return roomList;
            }

            return new ArrayList<>();
        }
    }
}
