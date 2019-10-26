package com.fredrikpedersen.eatingwithfriends_gradedassignment.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
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
import com.fredrikpedersen.eatingwithfriends_gradedassignment.database.models.Restaurant;
import com.fredrikpedersen.eatingwithfriends_gradedassignment.ui.bookings.BookingViewModel;
import com.fredrikpedersen.eatingwithfriends_gradedassignment.ui.friends.FriendViewModel;
import com.fredrikpedersen.eatingwithfriends_gradedassignment.ui.pickers.DatePickerFragment;
import com.fredrikpedersen.eatingwithfriends_gradedassignment.ui.pickers.OnPickerValueSelectedListener;
import com.fredrikpedersen.eatingwithfriends_gradedassignment.ui.pickers.TimePickerFragment;
import com.fredrikpedersen.eatingwithfriends_gradedassignment.ui.restaurants.RestaurantViewModel;
import com.fredrikpedersen.eatingwithfriends_gradedassignment.util.DateFormater;
import com.fredrikpedersen.eatingwithfriends_gradedassignment.util.StaticHolder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static com.fredrikpedersen.eatingwithfriends_gradedassignment.util.StaticHolder.EXTRA_ID;

//TODO This class is bit of a mess, cleanup if you get the time later.
public class AddEditBookingActivity extends AppCompatActivity implements OnPickerValueSelectedListener {

    private static final String TAG = "AddEditBookingActivity";

    private TextView textViewDate;
    private TextView textViewTime;
    private TextView textViewSelectFriends;
    private TextView textViewSelectedFriends;
    private ImageView imageViewSelectDate;
    private ImageView imageViewSelectTime;
    private ImageView imageViewSelectFriends;
    private Button buttonSave;
    private Spinner spinnerRestaurants;

    private List<Friend> allFriends;
    HashSet<String> selectedFriendNames;

    private List<Restaurant> allRestaurants;
    private Restaurant selectedRestaurant;

    private ArrayList<Integer> selectedItemsInChecklist;
    private boolean[] checkedItemsInList;

    private final String[] PICKERS = {"timepicker", "datepicker"};
    private String activePicker = "";
    private String time = "";
    private String date = "";

    FriendViewModel friendViewModel;
    BookingViewModel bookingViewModel;
    RestaurantViewModel restaurantViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_booking);
        initializeViews();

        friendViewModel = ViewModelProviders.of(this).get(FriendViewModel.class);
        bookingViewModel = ViewModelProviders.of(this).get(BookingViewModel.class);
        restaurantViewModel = ViewModelProviders.of(this).get(RestaurantViewModel.class);

        initializeVariables();
        setOnClicks();
        fillSpinner();
        initializeAddOrEdit();
    }

    /* ----- Save Booking ----- */

    private void checkBookingComplete() {
        List<Friend> selectedFriends = addSelectedFriends();
        getSelectedRestaurant();

        if (selectedRestaurant == null || date.equals("") || time.equals("")) {
            Toast.makeText(this, "Please give data in all required fields!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (selectedFriends.isEmpty() && textViewSelectedFriends.getText().equals("")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("No Friends Selected. Proceed with saving the booking?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", (dialogInterface, i) -> saveBooking(false, null))
                    .setNegativeButton("No", null)
                    .show();
        } else {
            saveBooking(true, selectedFriends);
        }
    }

    private void saveBooking(Boolean withFriends, @Nullable List<Friend> selectedFriends) {

        Booking newBooking;

        if (withFriends) {
            newBooking = new Booking(selectedRestaurant, date, time, selectedFriends);
        } else {
            newBooking = new Booking(selectedRestaurant, date, time, null);
        }

        if (getIntent().hasExtra(EXTRA_ID)) {
            newBooking.setId(getIntent().getIntExtra(EXTRA_ID, -1));
            bookingViewModel.update(newBooking);
        } else {
            bookingViewModel.insert(newBooking);
        }

        setResult(RESULT_OK);
        finish();
    }

    private List<Friend> addSelectedFriends() {
        List<Friend> selectedFriends = new ArrayList<>();

        for (String friendName : selectedFriendNames) {
            for (Friend friend : allFriends) {
                if (friendName.equals(friend.getFirstName() + " " + friend.getLastName())) {
                    selectedFriends.add(friend);
                }
            }
        }
        return selectedFriends;
    }

    /* ----- Multiple Choice List ----- */

    private void showSelectFriendsList() {
        String[] friendNames = new String[allFriends.size()];
        for (int i = 0; i < allFriends.size(); i++) {
            friendNames[i] = allFriends.get(i).getFirstName() + " " + allFriends.get(i).getLastName();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Friends")
                .setMultiChoiceItems(friendNames, checkedItemsInList, (dialog, position, isChecked) -> controlCheckedInList(isChecked, position))
                .setPositiveButton("Ok", (dialog, which) -> addSelectedToTextView(friendNames))
                .setNegativeButton("Close", (dialog, which) -> dialog.dismiss())
                .setNeutralButton("Clear all", (dialog, which) -> clearMultipleChoice())
                .setCancelable(false)
                .show();
    }

    //Used in showMultipleChoice to clear the list's checked items
    private void clearMultipleChoice() {
        for (int i = 0; i < checkedItemsInList.length; i++) {
            checkedItemsInList[i] = false;
            selectedItemsInChecklist.clear();
            textViewSelectedFriends.setText("");
            selectedFriendNames.clear();
        }
    }

    //Used in showMultipleChoice to add checked items to a TextView
    private void addSelectedToTextView(String[] itemNames) {
        selectedFriendNames = new HashSet<>();
        StringBuilder item = new StringBuilder();

        for (int i = 0; i < selectedItemsInChecklist.size(); i++) {
            item.append(itemNames[selectedItemsInChecklist.get(i)]);
            selectedFriendNames.add(itemNames[selectedItemsInChecklist.get(i)]);

            if (i != selectedItemsInChecklist.size()-1) {
                item.append("\n");
            }
        }

        if (selectedFriendNames.size() == 0) {
            clearMultipleChoice();
        }

        textViewSelectedFriends.setText(item.toString());
    }

    //Used in showMultipleChoice to check what items in the list are checked
    private void controlCheckedInList(boolean isChecked, int position) {
        if (isChecked) {
            if(!selectedItemsInChecklist.contains(position)) {
                selectedItemsInChecklist.add(position);
            } else {
                selectedItemsInChecklist.remove(position);
            }
        }
    }

    private void fillSpinner() {
        ArrayAdapter<Restaurant> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, allRestaurants);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRestaurants.setAdapter(spinnerAdapter);
    }

    private void getSelectedRestaurant() {
        selectedRestaurant = (Restaurant)spinnerRestaurants.getSelectedItem();
        Log.d(TAG, "getSelectedRestaurant: SELECTED RESTAURANT" + selectedRestaurant);
    }

    /* ----- Pickers ----- */

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
            textViewTime.setVisibility(VISIBLE);
        } else if (activePicker.equals(PICKERS[1])) {
            date = data;
            textViewDate.setText(DateFormater.formatDateText(date));
            textViewDate.setVisibility(VISIBLE);
        }
    }

    /* ----- Initializations ----- */

    private void initializeViews() {
        textViewSelectFriends = findViewById(R.id.text_view_select_friends);
        textViewSelectedFriends = findViewById(R.id.text_view_selected_friends);
        buttonSave = findViewById(R.id.button_save_booking);
        spinnerRestaurants = findViewById(R.id.spinner_restaurants);
        imageViewSelectDate = findViewById(R.id.image_view_select_date);
        imageViewSelectTime = findViewById(R.id.image_view_select_time);
        imageViewSelectFriends = findViewById(R.id.image_view_select_friends);

        textViewDate = findViewById(R.id.text_view_date);
        textViewDate.setVisibility(INVISIBLE);
        textViewTime = findViewById(R.id.text_view_time);
        textViewTime.setVisibility(INVISIBLE);
    }

    private void initializeVariables() {
        allFriends = friendViewModel.getAllFriendsAsList();
        allRestaurants = restaurantViewModel.getAllRestaurantsAsList();
        checkedItemsInList = new boolean[allFriends.size()];
        selectedItemsInChecklist = new ArrayList<>();
        selectedFriendNames = new HashSet<>();
    }

    private void setOnClicks() {
        textViewSelectFriends.setOnClickListener(v -> showSelectFriendsList());
        buttonSave.setOnClickListener(v -> checkBookingComplete());
        imageViewSelectDate.setOnClickListener(v -> showDatePickerDialog());
        imageViewSelectTime.setOnClickListener(v -> showTimePickerDialog());
        imageViewSelectFriends.setOnClickListener(v -> showSelectFriendsList());
    }

    private void initializeAddOrEdit() {
        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Booking");
            String passedRestaurantName = intent.getStringExtra(StaticHolder.EXTRA_RESTAURANT_NAME);
            selectedRestaurant = getChosenRestaurantFromName(passedRestaurantName);

            for (int i = 0; i < allRestaurants.size(); i++) {
                if (allRestaurants.get(i) == selectedRestaurant) {
                    spinnerRestaurants.setSelection(i);
                }
            }

            time = intent.getStringExtra(StaticHolder.EXTRA_TIME);
            textViewTime.setText(time);
            textViewTime.setVisibility(VISIBLE);

            date = intent.getStringExtra(StaticHolder.EXTRA_DATE);
            textViewDate.setText(date);
            textViewDate.setVisibility(VISIBLE);

            String[] friendsFromList = intent.getStringArrayExtra(StaticHolder.EXTRA_FRIENDS);

            if (friendsFromList != null) {
                for (String friendName : friendsFromList) {
                    selectedFriendNames.add(friendName);
                    textViewSelectedFriends.append(friendName + "\n");
                }
            }
        } else {
            setTitle("Add Booking");
        }
    }

    private Restaurant getChosenRestaurantFromName(String passedRestaurantName) {
        for (Restaurant restaurant : allRestaurants) {
            if (restaurant.getRestaurantName().equals(passedRestaurantName)) {
                return restaurant;
            }
        }
        return allRestaurants.get(0); //This should never happen, as a booking can never be saved, thus never be edited without having a restaurant
    }
}
