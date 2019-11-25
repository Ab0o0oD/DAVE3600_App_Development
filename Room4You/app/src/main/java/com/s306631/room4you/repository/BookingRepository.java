package com.s306631.room4you.repository;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.s306631.room4you.activities.BookRoomActivity;
import com.s306631.room4you.models.Booking;
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
    public void postBooking(BookRoomActivity context, Booking booking) {
        new PostBooking(context).execute(booking);
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
                        int buildingId = jsonobject.getInt("BuildingID");
                        String bookerName = jsonobject.getString("BookerName");
                        String fromTime = jsonobject.getString("FromTime");
                        String toTime = jsonobject.getString("ToTime");
                        String date = jsonobject.getString("Date");
                        objectFromJson = new Booking(bookingId, roomId, buildingId, bookerName, fromTime, toTime, date);
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
    private static class PostBooking extends AsyncTask<Booking, Void, Void> {

        private WeakReference<BookRoomActivity> activityReference;

        private PostBooking(BookRoomActivity context) {
            this.activityReference = new WeakReference<>(context);
        }

        @Override
        protected Void doInBackground(Booking... bookings) {
            Booking booking = bookings[0];
            String urlString = ("http://student.cs.hioa.no/~s306631/bookingjsonin.php/?" +
                    "roomid=" + booking.getRoomId() +
                    "&buildingid=" + booking.getBuildingId() +
                    "&name=" + booking.getBookerName() +
                    "&fromtime=" + booking.getFromTime() +
                    "&totime=" + booking.getToTime() +
                    "&date=" + booking.getDate())
                    .replaceAll(" ", "%20");

                try {

                    URL url = new URL(urlString);
                    Log.d(TAG, "postBooking: CONNECTING TO URL" + url.toString());
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                    urlConnection.setRequestMethod("POST");
                    urlConnection.setRequestProperty("Accept", "application/json");

                    int status = urlConnection.getResponseCode();
                    if (status != 200) {
                        throw new RuntimeException("Failed to get response from server, HTTP response code: " + urlConnection.getResponseCode());
                    }

                    urlConnection.disconnect();

                } catch (MalformedURLException e) {
                    Log.d(TAG, "postBooking: MalformedURLException " + e.getMessage());
                } catch (ProtocolException e) {
                    Log.d(TAG, "postBooking: ProtocolException " + e.getMessage());
                } catch (IOException e) {
                    Log.d(TAG, "postBooking: IOException " + e.getMessage());
                }

                return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            BookRoomActivity activity = activityReference.get();
            Toast.makeText(activity, "Booking Registered", Toast.LENGTH_SHORT).show();
            activity.refresh();
        }
    }
}
