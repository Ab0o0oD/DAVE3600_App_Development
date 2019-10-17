package com.fredrikpedersen.eatingwithfriends_gradedassignment.ui.bookings;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fredrikpedersen.eatingwithfriends_gradedassignment.R;
import com.fredrikpedersen.eatingwithfriends_gradedassignment.activities.AddEditBookingActivity;
import com.fredrikpedersen.eatingwithfriends_gradedassignment.database.models.Booking;
import com.fredrikpedersen.eatingwithfriends_gradedassignment.database.models.Friend;

import java.util.List;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;
import static com.fredrikpedersen.eatingwithfriends_gradedassignment.activities.MainActivity.EDIT_BOOKING_REQUEST;

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
        intent.putExtra(AddEditBookingActivity.EXTRA_ID, booking.getId());
        intent.putExtra(AddEditBookingActivity.EXTRA_RESTAURANT_NAME, booking.getRestaurantName());
        intent.putExtra(AddEditBookingActivity.EXTRA_ADDRESS, booking.getAddress());
        intent.putExtra(AddEditBookingActivity.EXTRA_PHONENUMBER, booking.getPhoneNumber());
        intent.putExtra(AddEditBookingActivity.EXTRA_TYPE, booking.getType());
        intent.putExtra(AddEditBookingActivity.EXTRA_DATE, booking.getDate());
        intent.putExtra(AddEditBookingActivity.EXTRA_TIME, booking.getTime());

        List<Friend> friends = booking.getFriends();
        String[] friendNames = new String[friends.size()];

        for (int i = 0; i < friends.size(); i++) {
            friendNames[i] = friends.get(i).getFirstName() + " " + friends.get(i).getLastName();
        }

        intent.putExtra(AddEditBookingActivity.EXTRA_FRIENDS, friendNames);

        startActivityForResult(intent, EDIT_BOOKING_REQUEST);
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

        if (requestCode == EDIT_BOOKING_REQUEST && resultCode == RESULT_OK) {
            Toast.makeText(getActivity(), "Booking updated!", Toast.LENGTH_SHORT).show();
        }
    }
}