package com.s306631.room4you.repository;

import android.os.AsyncTask;
import android.util.Log;

import com.s306631.room4you.models.Booking;
import com.s306631.room4you.util.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class BookingRepository {

    private static final String TAG = "BookingRepository";

    public List<Booking> getBookingsFromWebService() {
        GetBookings task = new GetBookings();

        try {
            return task.execute("http://student.cs.hioa.no/~s306631/bookingsjsonout.php").get();

        } catch (InterruptedException | ExecutionException e) {
            Log.d(TAG, "getRoomsFromWebService: Interrupted Exception | Execution Exception: " + e.getMessage());
        }

        return new ArrayList<>();
    }

    private static class GetBookings extends AsyncTask<String, Void, List<Booking>> {

        @Override
        protected List<Booking> doInBackground(String... urls) {
            Booking objectFromJson;
            List<Booking> buildingList = new ArrayList<>();
            JSONArray bookings = JsonParser.createJsonArray(urls[0]);

            if (bookings != null) {
                try {

                    for (int i = 0; i < bookings.length(); i++) {
                        JSONObject jsonobject = bookings.getJSONObject(i);
                        int bookingId = jsonobject.getInt("BookingID");
                        int roomId = jsonobject.getInt("RoomID");
                        String bookerName = jsonobject.getString("BookerName");
                        String fromTime = jsonobject.getString("FromTime");
                        String toTime = jsonobject.getString("ToTime");
                        objectFromJson = new Booking(bookingId, roomId, bookerName, fromTime, toTime);
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
