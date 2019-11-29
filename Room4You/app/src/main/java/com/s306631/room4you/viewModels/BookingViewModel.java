package com.s306631.room4you.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.s306631.room4you.activities.BookRoomActivity;
import com.s306631.room4you.models.Booking;
import com.s306631.room4you.repository.BookingRepository;

import java.util.Collections;
import java.util.List;

public class BookingViewModel extends AndroidViewModel {

    private BookingRepository bookingRepository;

    public BookingViewModel(@NonNull Application application) {
        super(application);
        bookingRepository = new BookingRepository();
    }

    public List<Booking> getAllBookingsAsList() {
        return sortBookingListByTime(bookingRepository.getBookingsFromWebService());
    }

    public void postBooking(BookRoomActivity context, Booking booking) {
        bookingRepository.postBooking(context, booking);
    }

    private List<Booking> sortBookingListByTime(List<Booking> bookingList) {
        Collections.sort(bookingList, (b1, b2) -> {
            String[] fromTimeSplit = b1.getFromTime().split(":");
            int fromTime1 = Integer.parseInt(fromTimeSplit[0]);

            fromTimeSplit = b2.getFromTime().split(":");
            int fromTime2 = Integer.parseInt(fromTimeSplit[0]);

            return Integer.compare(fromTime1, fromTime2);
        });

        return bookingList;
    }
}
