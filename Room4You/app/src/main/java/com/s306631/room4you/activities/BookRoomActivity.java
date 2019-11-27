package com.s306631.room4you.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.s306631.room4you.R;
import com.s306631.room4you.models.Booking;
import com.s306631.room4you.models.Room;
import com.s306631.room4you.ui.fragments.pickers.DatePickerFragment;
import com.s306631.room4you.ui.fragments.pickers.OnPickerValueSelectedListener;
import com.s306631.room4you.util.DateFormater;
import com.s306631.room4you.viewModels.BookingViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class BookRoomActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, OnPickerValueSelectedListener {

    public static Room selectedRoom = null;
    private static final String TAG = "BookRoomActivity";

    private TextView textViewRegisteredHeadline, textViewTakenTimes, textViewBookerNames, textViewDate;
    private Spinner spinnerAvailableSpinner;
    private EditText editTextBookerName;

    private BookingViewModel bookingViewModel;

    private List<Booking> bookingsFromWebService, takenBookingTimes;
    private List<String> availableBookingTimes;
    private String selectedTime, selectedDate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_room_activity);

        bookingViewModel = ViewModelProviders.of(this).get(BookingViewModel.class);
        bookingsFromWebService = bookingViewModel.getAllBookingsAsList();

        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat formater = new SimpleDateFormat("dd. MMM yyyy");
        selectedDate = formater.format(date);


        fillTakenBookingTimes();
        fillAvailableBookingTimes();
        initializeViews();
        fillAvailableSpinner();

        //Can't find a way to prevent editTextBookerName from gaining focus when the activity loads.
        //This at least prevents the on-screen keyboard from popping up automatically.
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    /* ---------- Register Bookings ---------- */

    private void registerBooking() {
        String bookerName = getNameFromView();

        if (bookerName == null) {
            return;
        }

        String[] timeSplit = selectedTime.split(" - ");
        String fromTime = timeSplit[0];
        String toTime = timeSplit[1];
        Booking booking = new Booking(selectedRoom.getRoomId(), selectedRoom.getBuildingId(), bookerName, fromTime, toTime, selectedDate);
        bookingViewModel.postBooking(this, booking);
    }

    private String getNameFromView() {
        String nameFromView = editTextBookerName.getText().toString();

        if (nameFromView.length() == 0) {
            editTextBookerName.setError("Name is required");
            return null;
        } else if (nameFromView.length() > 16) {
            editTextBookerName.setError("Name can't be more than 16 characters!");
            return null;
        }

        return nameFromView;
    }

    /* ---------- Pickers ---------- */

    private void showDateSelector() {
        DatePickerFragment datePicker = new DatePickerFragment();
        datePicker.setOnDateSelectedListener(this);
        datePicker.show(getSupportFragmentManager(), "datePicker");
    }

    @Override
    public void onValueSelected(String data) {
        String date = DateFormater.formatDateText(data);
        textViewDate.setText(date);
        selectedDate = date;
        refreshAfterDateSelection();
    }

    /* ---------- Lists ---------- */

    private void fillTakenBookingTimes() {
        takenBookingTimes = new ArrayList<>();
        for (Booking booking : bookingsFromWebService) {
            if (booking.getRoomId() == selectedRoom.getRoomId() && booking.getDate().equals(selectedDate)) {
                takenBookingTimes.add(booking);
            }
        }
    }

    private void fillAvailableBookingTimes() {
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
    }

    /* ---------- Views ---------- */

    /* ----- Spinners ---- */

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

    /* ----- Misc Views ----- */

    private void initializeViews() {
        TextView textViewHeadline = findViewById(R.id.text_view_bookings_headline);
        textViewHeadline.setText(selectedRoom.getRoomName());

        textViewTakenTimes = findViewById(R.id.text_view_taken_times);
        textViewTakenTimes.setText("");

        textViewBookerNames = findViewById(R.id.text_view_booker_name);
        textViewBookerNames.setText("");


        for (Booking booking : takenBookingTimes) {
            textViewTakenTimes.append(booking.getFromTime() + " - " + booking.getToTime() + "\n");
            textViewBookerNames.append(booking.getBookerName() + "\n");
        }

        textViewDate = findViewById(R.id.text_view_date);
        textViewDate.setOnClickListener(v -> showDateSelector());
        textViewDate.setText(selectedDate);

        ImageView imageViewSelectDate = findViewById(R.id.image_view_select_date);
        imageViewSelectDate.setOnClickListener(v -> showDateSelector());

        spinnerAvailableSpinner = findViewById(R.id.spinner_available_times);
        spinnerAvailableSpinner.setOnItemSelectedListener(this);

        Button buttonRegisterBooking = findViewById(R.id.button_register_booking);
        buttonRegisterBooking.setOnClickListener(v -> registerBooking());

        editTextBookerName = findViewById(R.id.edit_text_booker_name);

        textViewRegisteredHeadline = findViewById(R.id.text_view_registered_bookings_headline);
        textViewRegisteredHeadline.setText(getResources().getString(R.string.registered_bookings) + ":\n" + selectedDate);
    }

    private void refreshAfterDateSelection() {
        Log.d(TAG, "refreshAfterDateSelection: REFRESHING PAGE FOR " + selectedDate);
        textViewTakenTimes.setText("");
        textViewBookerNames.setText("");
        fillTakenBookingTimes();
        for(Booking booking : takenBookingTimes) {
            Log.d(TAG, "refreshAfterDateSelection: BOOKINGS ON " + selectedDate + " :" + booking.toString());
        }

        fillAvailableBookingTimes();
        fillAvailableSpinner();
        textViewRegisteredHeadline.setText(getResources().getString(R.string.registered_bookings) + ":\n" + selectedDate);

        for (Booking booking : takenBookingTimes) {
            textViewTakenTimes.append(booking.getFromTime() + " - " + booking.getToTime() + "\n");
            textViewBookerNames.append(booking.getBookerName() + "\n");
        }
    }

    public void refresh() {
        bookingsFromWebService = bookingViewModel.getAllBookingsAsList();
        fillTakenBookingTimes();
        fillAvailableBookingTimes();
        initializeViews();
        fillAvailableSpinner();
    }

    /* ---------- Life-Cycle and Default Functionality ---------- */

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
