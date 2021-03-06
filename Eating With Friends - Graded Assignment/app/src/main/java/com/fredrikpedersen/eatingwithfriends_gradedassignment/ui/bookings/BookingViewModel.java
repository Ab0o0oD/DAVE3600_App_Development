package com.fredrikpedersen.eatingwithfriends_gradedassignment.ui.bookings;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.fredrikpedersen.eatingwithfriends_gradedassignment.database.models.Booking;
import com.fredrikpedersen.eatingwithfriends_gradedassignment.repository.BookingRepository;

import java.util.List;

public class BookingViewModel extends AndroidViewModel {

    private static final String TAG = "BookingViewModel";
    private BookingRepository repository;
    private LiveData<List<Booking>> allBookings;

    //Holds a reference to the Application, not the context, because the ViewModel is supposed to outlive the activity
    public BookingViewModel(@NonNull Application application) {
        super(application);
        repository = new BookingRepository(application);
        allBookings = repository.getAllBookingsAsLiveData();
    }

    public void insert(Booking booking) {
        repository.insert(booking);
    }
    public void update(Booking booking) {
        repository.update(booking);
    }
    public void delete(Booking booking) {
        repository.delete(booking);
    }
    LiveData<List<Booking>> getAllBookingsAsLiveData() {
        return allBookings;
    }
}