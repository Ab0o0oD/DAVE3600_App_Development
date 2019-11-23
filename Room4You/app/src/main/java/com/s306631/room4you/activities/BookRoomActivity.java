package com.s306631.room4you.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

public class BookRoomActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static Room selectedRoom = null;
    private static final String TAG = "BookRoomActivity";

    private TextView textViewHeadline, textViewAvailableTimes, textViewTakenTimes, textViewBookerNames;
    private Spinner spinnerAvailableSpinner;
    private EditText editTextBookerName;
    private Button buttonRegisterBooking;

    private BookingViewModel bookingViewModel;

    private List<Booking> bookingsFromWebService;
    private List<Booking> takenBookingTimes;
    private List<String> availableBookingTimes;
    private String selectedTime;

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
        fillAvailableSpinner();

        for (Booking booking : takenBookingTimes) {
            textViewTakenTimes.append(booking.getFromTime() + " - " + booking.getToTime() + "\n");
            textViewBookerNames.append(booking.getBookerName() + "\n");
        }
    }

    private void registerBooking() {
        if (editTextBookerName.getText().length() == 0) {
            editTextBookerName.setError("Name is required");
            return;
        }

        Booking newBooking = createNewBooking();
        Log.d(TAG, "registerBooking: BOOKING TO BE REGISTERED " + newBooking.toString());
    }

    private Booking createNewBooking() {
        String bookerName = editTextBookerName.getText().toString();

        String[] timeSplit = selectedTime.split(" - ");
        String fromTime = timeSplit[0];
        String toTime = timeSplit[1];

        return new Booking(selectedRoom.getRoomId(), bookerName, fromTime, toTime);
    }

    private void fillAvailableSpinner() {
        ArrayAdapter<String> availableSpinnerAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, availableBookingTimes);
        availableSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAvailableSpinner.setAdapter(availableSpinnerAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedTime = availableBookingTimes.get(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void initializeViews() {
        textViewHeadline = findViewById(R.id.text_view_bookings_headline);
        textViewHeadline.setText(selectedRoom.getRoomName());

        textViewAvailableTimes = findViewById(R.id.text_view_available_times);

        textViewTakenTimes = findViewById(R.id.text_view_taken_times);
        textViewTakenTimes.setText("");

        textViewBookerNames = findViewById(R.id.text_view_booker_name);
        textViewBookerNames.setText("");

        spinnerAvailableSpinner = findViewById(R.id.spinner_available_times);
        spinnerAvailableSpinner.setOnItemSelectedListener(this);

        buttonRegisterBooking = findViewById(R.id.button_register_booking);
        buttonRegisterBooking.setOnClickListener(v -> registerBooking());

        editTextBookerName = findViewById(R.id.edit_text_booker_name);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
