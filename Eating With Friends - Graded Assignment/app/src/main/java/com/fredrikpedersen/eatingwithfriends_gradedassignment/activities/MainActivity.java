package com.fredrikpedersen.eatingwithfriends_gradedassignment.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fredrikpedersen.eatingwithfriends_gradedassignment.R;
import com.fredrikpedersen.eatingwithfriends_gradedassignment.database.models.Booking;
import com.fredrikpedersen.eatingwithfriends_gradedassignment.ui.bookings.BookingAdapter;
import com.fredrikpedersen.eatingwithfriends_gradedassignment.ui.bookings.BookingViewModel;
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

    private BookingViewModel bookingViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = findViewById(R.id.navigation);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        BookingAdapter bookingAdapter = new BookingAdapter();
        recyclerView.setAdapter(bookingAdapter);

        bookingViewModel = ViewModelProviders.of(this).get(BookingViewModel.class);
        bookingViewModel.getAllBookings().observe(this, bookingAdapter::submitList);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                bookingViewModel.delete(bookingAdapter.getBookingAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Booking Deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);


        //navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        /* fragmentManager.beginTransaction().add(R.id.main_container, notificationsFragment, "notifications").hide(notificationsFragment).commit();
        fragmentManager.beginTransaction().add(R.id.main_container, friendsFragment, "friends").hide(friendsFragment).commit();
        fragmentManager.beginTransaction().add(R.id.main_container, bookingFragment, "booking").commit(); */
    }

   /* private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = item -> {
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
    }; */

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
