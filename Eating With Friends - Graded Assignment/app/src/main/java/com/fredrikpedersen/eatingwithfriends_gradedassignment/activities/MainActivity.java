package com.fredrikpedersen.eatingwithfriends_gradedassignment.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.fredrikpedersen.eatingwithfriends_gradedassignment.R;
import com.fredrikpedersen.eatingwithfriends_gradedassignment.ui.bookings.BookingsFragment;
import com.fredrikpedersen.eatingwithfriends_gradedassignment.ui.friends.FriendsFragment;
import com.fredrikpedersen.eatingwithfriends_gradedassignment.ui.notifications.NotificationsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private final Fragment bookingFragment = new BookingsFragment();
    private final Fragment friendsFragment = new FriendsFragment();
    private final Fragment notificationsFragment = new NotificationsFragment();
    private final FragmentManager fragmentManager = getSupportFragmentManager();
    private Fragment activeFragment = bookingFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        fragmentManager.beginTransaction().add(R.id.main_container, notificationsFragment, "notifications").hide(notificationsFragment).commit();
        fragmentManager.beginTransaction().add(R.id.main_container, friendsFragment, "friends").hide(friendsFragment).commit();
        fragmentManager.beginTransaction().add(R.id.main_container, bookingFragment, "booking").commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = item -> {
        switch (item.getItemId()) {
            case R.id.navigation_bookings:
                fragmentManager.beginTransaction().hide(activeFragment).show(bookingFragment).commit();
                activeFragment = bookingFragment;
                return true;

            case R.id.navigation_friends:
                fragmentManager.beginTransaction().hide(activeFragment).show(friendsFragment).commit();
                activeFragment = friendsFragment;
                return true;

            case R.id.navigation_notifications:
                fragmentManager.beginTransaction().hide(activeFragment).show(notificationsFragment).commit();
                activeFragment = notificationsFragment;
                return true;
        }
        return false;
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


}
