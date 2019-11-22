package com.s306631.room4you.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.s306631.room4you.R;
import com.s306631.room4you.models.Booking;
import com.s306631.room4you.models.Room;
import com.s306631.room4you.viewModels.BookingViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BookRoomActivity extends AppCompatActivity {

    public static Room selectedRoom = null;
    private static final String TAG = "BookRoomActivity";

    private TextView headline, availableTimes, takenTimes, bookerNames;
    private BookingViewModel bookingViewModel;
    private List<Booking> bookingsFromWebService;
    private List<Booking> takenBookingTimes;
    private List<String> availableBookingTimes;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_room_activity);

        bookingViewModel = ViewModelProviders.of(this).get(BookingViewModel.class);
        bookingsFromWebService = bookingViewModel.getAllBookingsAsList();
        takenBookingTimes = new ArrayList<>();

        //Create a list of bookings belonging to the passed in Room
        for (Booking booking : bookingsFromWebService) {
            if (booking.getRoomId() == selectedRoom.getRoomId()) {
                takenBookingTimes.add(booking);
            }
        }

        String[] possibleBookingTimes = getResources().getStringArray(R.array.possible_booking_times);
        availableBookingTimes = new ArrayList<>(Arrays.asList(possibleBookingTimes));

        //Find out what booking are taken
        for (String possibleTime : possibleBookingTimes) {
            for (Booking takenBooking : takenBookingTimes) {
                if (possibleTime.equals(takenBooking.getFromTime() + " - " + takenBooking.getToTime())) {
                    availableBookingTimes.remove(possibleTime);
                }
            }
        }

        initializeViews();

        for (String bookingTime : availableBookingTimes) {
            availableTimes.append(bookingTime + "\n");
        }

        for (Booking booking : takenBookingTimes) {
            takenTimes.append(booking.getFromTime() + " - " + booking.getToTime());
            bookerNames.append(booking.getBookerName());
        }
    }

    private void initializeViews() {
        headline = findViewById(R.id.text_view_bookings_headline);
        headline.setText(selectedRoom.getRoomName());

        availableTimes = findViewById(R.id.text_view_available_times);
        availableTimes.setText("");

        takenTimes = findViewById(R.id.text_view_taken_times);
        takenTimes.setText("");

        bookerNames = findViewById(R.id.text_view_booker_name);
        bookerNames.setText("");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
