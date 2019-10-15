package com.fredrikpedersen.eatingwithfriends_gradedassignment.ui.bookings;

import android.os.Bundle;
import android.util.Log;
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
import com.fredrikpedersen.eatingwithfriends_gradedassignment.database.models.Booking;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class BookingsFragment extends Fragment {

    private static final String TAG = "BookingsFragment";
    private BookingViewModel bookingViewModel;

    public BookingsFragment() { }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bookings, container, false);

        FloatingActionButton addButton = view.findViewById(R.id.button_add_booking);
        addButton.setOnClickListener(v -> {
            bookingViewModel.insert(new Booking("Mi Case", "Seven 11", "29 October 2019", "13:37", null));
            Toast.makeText(getActivity(), "New booking Created", Toast.LENGTH_SHORT);
        });

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        BookingAdapter bookingAdapter = new BookingAdapter();
        recyclerView.setAdapter(bookingAdapter);

        bookingViewModel = ViewModelProviders.of(this).get(BookingViewModel.class);
        bookingViewModel.getAllBookings().observe(this, bookingAdapter::submitList);
        Log.d(TAG, "onCreateView: PRINTING GETALLBOOKINGS" + bookingViewModel.getAllBookings().toString());

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                bookingViewModel.delete(bookingAdapter.getBookingAt(viewHolder.getAdapterPosition()));
                Toast.makeText(getActivity(), "Booking Deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        return view;
    }



    /* ----- Initializations ----- */

    private void initializeViews(View view) {
    }

    private void setOnClicks() {
    }





}