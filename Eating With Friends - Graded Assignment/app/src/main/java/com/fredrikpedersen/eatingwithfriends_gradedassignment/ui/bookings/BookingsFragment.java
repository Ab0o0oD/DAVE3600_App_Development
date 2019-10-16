package com.fredrikpedersen.eatingwithfriends_gradedassignment.ui.bookings;

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
import com.fredrikpedersen.eatingwithfriends_gradedassignment.database.models.Booking;
import com.fredrikpedersen.eatingwithfriends_gradedassignment.database.models.Friend;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class BookingsFragment extends Fragment {

    private static final String TAG = "BookingsFragment";
    private BookingViewModel bookingViewModel;
    private FloatingActionButton addButton;
    private RecyclerView recyclerView;
    private BookingAdapter bookingAdapter;

    public BookingsFragment() { }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bookings, container, false);

        bookingAdapter = new BookingAdapter();
        initializeViews(view);
        setOnClicks();

        bookingViewModel = ViewModelProviders.of(this).get(BookingViewModel.class);
        bookingViewModel.getAllBookings().observe(this, bookingAdapter::submitList);

        return view;
    }



    /* ----- Initializations ----- */

    private void initializeViews(View view) {
        addButton = view.findViewById(R.id.button_add_booking);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(bookingAdapter);
        swipeToDelete().attachToRecyclerView(recyclerView);
    }

    private void setOnClicks() {
        addButton.setOnClickListener(v -> {
            List<Friend> friends = new ArrayList<>();
            friends.add(new Friend("Bjarne", "123456789"));
            friends.add(new Friend("Kåre Johhny", "98765432"));
            bookingViewModel.insert(new Booking("Mi Case", "Seven 11", "29 October 2019", "13:37", friends));
            Toast.makeText(getActivity(), "New booking Created", Toast.LENGTH_SHORT);
        });
    }

    private ItemTouchHelper swipeToDelete() {
        return new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                bookingViewModel.delete(bookingAdapter.getBookingAt(viewHolder.getAdapterPosition()));
                Toast.makeText(getActivity(), "Booking Deleted", Toast.LENGTH_SHORT).show();
            }
        });
    }





}