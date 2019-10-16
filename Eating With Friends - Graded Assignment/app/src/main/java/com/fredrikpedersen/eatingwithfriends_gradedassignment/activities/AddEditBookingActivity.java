package com.fredrikpedersen.eatingwithfriends_gradedassignment.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.fredrikpedersen.eatingwithfriends_gradedassignment.R;
import com.fredrikpedersen.eatingwithfriends_gradedassignment.database.models.Booking;
import com.fredrikpedersen.eatingwithfriends_gradedassignment.database.models.Friend;
import com.fredrikpedersen.eatingwithfriends_gradedassignment.ui.bookings.BookingViewModel;
import com.fredrikpedersen.eatingwithfriends_gradedassignment.ui.friends.FriendViewModel;
import com.fredrikpedersen.eatingwithfriends_gradedassignment.ui.pickers.DatePickerFragment;
import com.fredrikpedersen.eatingwithfriends_gradedassignment.ui.pickers.OnPickerValueSelectedListener;
import com.fredrikpedersen.eatingwithfriends_gradedassignment.ui.pickers.TimePickerFragment;

import java.util.ArrayList;
import java.util.List;

//TODO This class is bit of a mess, cleanup if you get the time later.
public class AddEditBookingActivity extends AppCompatActivity implements OnPickerValueSelectedListener, AdapterView.OnItemSelectedListener {

    private static final String TAG = "AddEditBookingActivity";

    public static final String EXTRA_ID = "com.fredrikpedersen.eatingwithfriends_gradedassignment.activities.EXTRA_ID";
    public static final String EXTRA_RESTAURANT_NAME = "com.fredrikpedersen.eatingwithfriends_gradedassignment.activities.EXTRA_RESTAURANT_NAME";
    public static final String EXTRA_ADDRESS = "com.fredrikpedersen.eatingwithfriends_gradedassignment.activities.EXTRA_ADDRESS";
    public static final String EXTRA_DATE = "com.fredrikpedersen.eatingwithfriends_gradedassignment.activities.EXTRA_DATE";
    public static final String EXTRA_TIME = "com.fredrikpedersen.eatingwithfriends_gradedassignment.activities.EXTRA_TIME";
    public static final String EXTRA_FRIENDS = "com.fredrikpedersen.eatingwithfriends_gradedassignment.activities.EXTRA_RESTAURANT_FRIENDS";

    private EditText editTextRestaurantName;
    private EditText editTextAddress;
    private TextView textViewDate;
    private TextView textViewTime;
    private TextView textViewSelectedFriends;
    private Spinner spinnerFriendSelector;
    private Button buttonSave;
    private Button buttonSelectFriend;

    private ArrayAdapter<Friend> spinnerAdapter;
    private List<Friend> allFriends;
    private List<Friend> selectedFriends;
    private Friend selectedFriend;

    private final String[] PICKERS = {"timepicker", "datepicker"};
    private String activePicker = "";
    private String time = "";
    private String date = "";

    FriendViewModel friendViewModel;
    BookingViewModel bookingViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_booking);
        initializeViews();
        setTitle("Add Booking");
        friendViewModel = ViewModelProviders.of(this).get(FriendViewModel.class);
        bookingViewModel = ViewModelProviders.of(this).get(BookingViewModel.class);
        allFriends = friendViewModel.getAllFriendsAsList();
        selectedFriends = new ArrayList<>();

        buttonSave.setOnClickListener(v -> checkBooking());
        buttonSelectFriend.setOnClickListener(v -> addFriend());
        textViewDate.setOnClickListener(v -> showDatePickerDialog());
        textViewTime.setOnClickListener(v -> showTimePickerDialog());
        loadFriendsToSpinner();
    }

    private void checkBooking() {
        String restaurantName = editTextRestaurantName.getText().toString();
        String address = editTextAddress.getText().toString();

        if (restaurantName.trim().isEmpty() || address.trim().isEmpty() || date.equals("") || time.equals("")) {
            Toast.makeText(this, "Please give data in all required fields!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (selectedFriends.isEmpty()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("No Friends Selected. Proceed with saving the booking?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", (dialogInterface, i) -> saveBooking(false))
                    .setNegativeButton("No", null)
                    .show();
        } else {
            saveBooking(true);
        }
    }

    private void saveBooking(Boolean withFriends) {
        String restaurantName = editTextRestaurantName.getText().toString();
        String address = editTextAddress.getText().toString();
        Booking newBooking;

        if (withFriends)
            newBooking = new Booking(restaurantName, address, date, time, selectedFriends);
        else
            newBooking = new Booking(restaurantName, address, date, time, null);

        bookingViewModel.insert(newBooking);
        setResult(RESULT_OK);
        finish();
    }

    private void initializeViews() {
        editTextRestaurantName = findViewById(R.id.edit_text_restaurant_name);
        editTextAddress = findViewById(R.id.edit_text_address);
        textViewDate = findViewById(R.id.text_view_date);
        textViewTime = findViewById(R.id.text_view_time);
        textViewSelectedFriends = findViewById(R.id.text_view_selected_friends);
        spinnerFriendSelector = findViewById(R.id.spinner_friend_selector);
        buttonSave = findViewById(R.id.button_save_booking);
        buttonSelectFriend = findViewById(R.id.button_select_friend);
    }

    private void addFriend() {
        if (selectedFriend != null) {
            selectedFriends.add(selectedFriend);
            spinnerAdapter.remove(selectedFriend);
        }
        appendSelectedFriends();
    }

    private void appendSelectedFriends() {
        for (Friend friend : selectedFriends) {
            textViewSelectedFriends.append(friend.toString() + "\n");
        }
    }

    private void loadFriendsToSpinner() {
        if (allFriends != null) {
            spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, allFriends);
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerFriendSelector.setAdapter(spinnerAdapter);
            spinnerFriendSelector.setOnItemSelectedListener(this);
        }
    }

    private void showTimePickerDialog() {
        TimePickerFragment timePicker = new TimePickerFragment();
        timePicker.setOnTimeSelectedListener(this);
        timePicker.show(getSupportFragmentManager(), "timePicker");
        activePicker = PICKERS[0];
    }

    private void showDatePickerDialog() {
        DatePickerFragment datePicker = new DatePickerFragment();
        datePicker.setOnDateSelectedListener(this);
        datePicker.show(getSupportFragmentManager(), "datePicker");
        activePicker = PICKERS[1];
    }

    @Override
    public void onValueSelected(String data) {
        if (activePicker.equals(PICKERS[0])) {
            time = data;
            textViewTime.setText(time);
        } else if (activePicker.equals(PICKERS[1])) {
            date = data;
            textViewDate.setText(date);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedFriend = spinnerAdapter.getItem(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}
