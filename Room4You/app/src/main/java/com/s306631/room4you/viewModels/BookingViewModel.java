package com.s306631.room4you.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.s306631.room4you.models.Booking;
import com.s306631.room4you.repository.BookingRepository;

import java.util.List;

public class BookingViewModel extends AndroidViewModel {

    private static final String TAG = "BookingViewModel";

    private BookingRepository bookingRepository;

    public BookingViewModel(@NonNull Application application) {
        super(application);
        bookingRepository = new BookingRepository();
    }

    public List<Booking> getAllBookingsAsList() {
        return bookingRepository.getBookingsFromWebService();
    }
}
