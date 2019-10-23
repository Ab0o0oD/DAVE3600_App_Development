package com.fredrikpedersen.eatingwithfriends_gradedassignment.ui.bookings;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fredrikpedersen.eatingwithfriends_gradedassignment.R;
import com.fredrikpedersen.eatingwithfriends_gradedassignment.activities.AddEditBookingActivity;
import com.fredrikpedersen.eatingwithfriends_gradedassignment.database.models.Booking;
import com.fredrikpedersen.eatingwithfriends_gradedassignment.database.models.Friend;
import com.fredrikpedersen.eatingwithfriends_gradedassignment.util.StaticHolder;

import java.util.List;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;

public class BookingsFragment extends Fragment {

    private static final String TAG = "BookingsFragment";
    private BookingViewModel bookingViewModel;
    private BookingAdapter bookingAdapter;

    public BookingsFragment() { }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bookings, container, false);

        Objects.requireNonNull(getActivity()).setTitle("Bookings");
        bookingAdapter = new BookingAdapter();
        initializeViews(view);

        bookingViewModel = ViewModelProviders.of(this).get(BookingViewModel.class);
        bookingViewModel.getAllBookings().observe(this, bookingAdapter::submitList);
        bookingAdapter.setOnItemClickListener(this::touchToEdit);

        if (!(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED))
            requestSmsPermission();

        return view;
    }

    /* ----- Initializations ----- */

    private void initializeViews(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_booking);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(bookingAdapter);
        swipeToDelete().attachToRecyclerView(recyclerView);
    }

    private void touchToEdit(Object item) {
        Booking booking = (Booking)item;
        Intent intent = new Intent(getActivity(), AddEditBookingActivity.class);
        intent.putExtra(StaticHolder.EXTRA_ID, booking.getId());
        intent.putExtra(StaticHolder.EXTRA_RESTAURANT_NAME, booking.getRestaurant().getRestaurantName());
        intent.putExtra(StaticHolder.EXTRA_DATE, booking.getDate());
        intent.putExtra(StaticHolder.EXTRA_TIME, booking.getTime());

        List<Friend> friends = booking.getFriends();
        String[] friendNames = new String[friends.size()];

        for (int i = 0; i < friends.size(); i++) {
            friendNames[i] = friends.get(i).getFirstName() + " " + friends.get(i).getLastName();
        }

        intent.putExtra(StaticHolder.EXTRA_FRIENDS, friendNames);

        startActivityForResult(intent, StaticHolder.EDIT_BOOKING_REQUEST);
    }

    private ItemTouchHelper swipeToDelete() {
        return new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                bookingViewModel.delete(bookingAdapter.getBookingFromPosition(viewHolder.getAdapterPosition()));
                Toast.makeText(getActivity(), "Booking Deleted", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == StaticHolder.EDIT_BOOKING_REQUEST && resultCode == RESULT_OK) {
            Toast.makeText(getActivity(), "Booking updated!", Toast.LENGTH_SHORT).show();
        }
    }

    private void requestSmsPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.SEND_SMS)) {
            new AlertDialog.Builder(getActivity())
                    .setTitle("SMS Permission Needed")
                    .setMessage("This app has a feature where it sends SMS reminders to your friends on the day you have an appointment with them, but it needs your approval to do so")
                    .setPositiveButton("ok", (dialog, which) -> ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.SEND_SMS}, StaticHolder.SMS_PERMISSION_CODE))
                    .setNegativeButton("cancel", (dialog, which) -> dialog.dismiss())
                    .create()
                    .show();

        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.SEND_SMS}, StaticHolder.SMS_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == StaticHolder.SMS_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getActivity(), "Permission GRANTED", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }
}