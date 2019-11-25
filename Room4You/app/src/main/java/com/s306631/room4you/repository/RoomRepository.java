package com.s306631.room4you.repository;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.s306631.room4you.activities.AddDeleteBuildingActivity;
import com.s306631.room4you.activities.AddDeleteRoomActivity;
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


public class RoomRepository {

    private static final String TAG = "RoomRepository";

    public List<Room> getRoomsFromWebService() {
        GetRooms task = new GetRooms();

        try {
            return task.execute("http://student.cs.hioa.no/~s306631/roomjsonout.php").get();

        } catch (InterruptedException | ExecutionException e) {
            Log.d(TAG, "getRoomsFromWebService: Interrupted Exception | Execution Exception: " + e.getMessage());
        }

        return new ArrayList<>();
    }
    public void postRoom(AddDeleteRoomActivity context, Room room) {
        new PostRoom(context).execute(room);
    }

    private static class GetRooms extends AsyncTask<String, Void, List<Room>> {

        @Override
        protected List<Room> doInBackground(String... urls) {
            Room objectFromJson;
            List<Room> roomList = new ArrayList<>();
            JSONArray rooms = JsonParser.createJsonArray(urls[0]);

            if (rooms != null) {
                try {

                    for (int i = 0; i < rooms.length(); i++) {
                        JSONObject jsonobject = rooms.getJSONObject(i);
                        int roomId = jsonobject.getInt("RoomID");
                        int buildingId = jsonobject.getInt("BuildingID");
                        String roomName = jsonobject.getString("Name");
                        int floor = jsonobject.getInt("Floor");
                        String coordinates = jsonobject.getString("Coordinates");
                        objectFromJson = new Room(roomId, buildingId, roomName, floor, coordinates);

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
    private static class PostRoom extends AsyncTask<Room, Void, Void> {

        private WeakReference<AddDeleteRoomActivity> activityReference;

        private PostRoom(AddDeleteRoomActivity context) {
            this.activityReference = new WeakReference<>(context);
        }

        @Override
        protected Void doInBackground(Room... rooms) {
            Room room = rooms[0];
            String urlString = ("http://student.cs.hioa.no/~s306631/roomjsonin.php/?" +
                    "buildingId=" + room.getBuildingId() +
                    "&name=" + room.getRoomName() +
                    "&floor=" + room.getFloor() +
                    "&coordinates=" + room.getCoordinates())
                    .replaceAll(" ", "%20");

            try {

                URL url = new URL(urlString);
                Log.d(TAG, "postRoom: CONNECTING TO URL" + url.toString());
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Accept", "application/json");

                int status = urlConnection.getResponseCode();
                if (status != 200) {
                    throw new RuntimeException("Failed to get response from server, HTTP response code: " + urlConnection.getResponseCode());
                }

                urlConnection.disconnect();

            } catch (MalformedURLException e) {
                Log.d(TAG, "postRoom: MalformedURLException " + e.getMessage());
            } catch (ProtocolException e) {
                Log.d(TAG, "postRoom: ProtocolException " + e.getMessage());
            } catch (IOException e) {
                Log.d(TAG, "postRoom: IOException " + e.getMessage());
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            AddDeleteRoomActivity activity = activityReference.get();
            Toast.makeText(activity, "Room Added", Toast.LENGTH_SHORT).show();
        }
    }
}
