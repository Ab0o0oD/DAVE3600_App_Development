package com.fredrikpedersen.eatingwithfriends_gradedassignment.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.fredrikpedersen.eatingwithfriends_gradedassignment.R;
import com.fredrikpedersen.eatingwithfriends_gradedassignment.ui.bookings.BookingsFragment;
import com.fredrikpedersen.eatingwithfriends_gradedassignment.ui.friends.FriendsFragment;
import com.fredrikpedersen.eatingwithfriends_gradedassignment.ui.restaurants.RestaurantFragment;
import com.fredrikpedersen.eatingwithfriends_gradedassignment.ui.settings.SettingsFragment;
import com.fredrikpedersen.eatingwithfriends_gradedassignment.util.StaticHolder;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private final Fragment bookingFragment = new BookingsFragment();
    private final Fragment friendsFragment = new FriendsFragment();
    private final Fragment restaurantFragment = new RestaurantFragment();
    private Fragment settingsFragment = new SettingsFragment();
    private final FragmentManager fragmentManager = getSupportFragmentManager();
    private Fragment activeFragment = bookingFragment;

    private FloatingActionButton addButton;
    private BottomNavigationView navigation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
        addButton.setOnClickListener(v -> goToAddItem());
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        fragmentManager.beginTransaction().add(R.id.main_container, restaurantFragment, "restaurants").hide(restaurantFragment).commit();
        fragmentManager.beginTransaction().add(R.id.main_container, friendsFragment, "friends").hide(friendsFragment).commit();
        fragmentManager.beginTransaction().add(R.id.main_container, bookingFragment, "bookings").commit();

        startReminderService();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = item -> {
        switch (item.getItemId()) {
            case R.id.navigation_bookings:
                fragmentManager.beginTransaction().hide(activeFragment).show(bookingFragment).commit();
                destroyIfActiveIsSettings();
                activeFragment = bookingFragment;
                setTitle("Bookings");
                addButton.show();
                return true;

            case R.id.navigation_friends:
                fragmentManager.beginTransaction().hide(activeFragment).show(friendsFragment).commit();
                destroyIfActiveIsSettings();
                activeFragment = friendsFragment;
                setTitle("Friends");
                addButton.show();
                return true;

            case R.id.navigation_restaurants:
                fragmentManager.beginTransaction().hide(activeFragment).show(restaurantFragment).commit();
                destroyIfActiveIsSettings();
                activeFragment = restaurantFragment;
                setTitle("Restaurants");
                addButton.show();
                return true;

            case R.id.navigation_settings:
                settingsFragment = new SettingsFragment();
                fragmentManager.beginTransaction().add(R.id.main_container, settingsFragment, "settings").hide(activeFragment).show(settingsFragment).commit();
                activeFragment = settingsFragment;
                setTitle("Settings");
                addButton.hide();
                return true;
        }
        return false;
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == StaticHolder.ADD_BOOKING_REQUEST && resultCode == RESULT_OK) {
            Toast.makeText(this, "Booking saved!", Toast.LENGTH_SHORT).show();
        }

        if (requestCode == StaticHolder.ADD_FRIEND_REQUEST && resultCode == RESULT_OK) {
            Toast.makeText(this, "Friend saved!", Toast.LENGTH_SHORT).show();
        }

        if (requestCode == StaticHolder.ADD_RESTAURANT_REQUEST && resultCode == RESULT_OK) {
            Toast.makeText(this, "Restaurant saved!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void initializeViews() {
        addButton = findViewById(R.id.floating_button_add);
        navigation = findViewById(R.id.navigation);
    }

    private void goToAddItem() {
       if (activeFragment == friendsFragment) {
           startActivityForResult(new Intent(MainActivity.this, AddEditFriendActivity.class), StaticHolder.ADD_FRIEND_REQUEST);
       } else if (activeFragment == bookingFragment) {
           startActivityForResult(new Intent(MainActivity.this, AddEditBookingActivity.class), StaticHolder.ADD_BOOKING_REQUEST);
       } else if (activeFragment == restaurantFragment) {
           startActivityForResult(new Intent(MainActivity.this, AddEditRestaurantActivity.class), StaticHolder.ADD_RESTAURANT_REQUEST);
       }
    }

    private void startReminderService() {
        Intent intent = new Intent();
        intent.setAction(StaticHolder.REMINDER_BROADCAST);
        this.sendBroadcast(intent);
    }

    private void destroyIfActiveIsSettings() {
        if (activeFragment == settingsFragment) {
            fragmentManager.beginTransaction().remove(settingsFragment).commit();
        }
    }
}
