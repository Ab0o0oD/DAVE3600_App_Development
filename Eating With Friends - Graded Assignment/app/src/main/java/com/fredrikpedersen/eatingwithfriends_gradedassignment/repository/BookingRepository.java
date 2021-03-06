package com.fredrikpedersen.eatingwithfriends_gradedassignment.repository;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.fredrikpedersen.eatingwithfriends_gradedassignment.database.BookingDatabase;
import com.fredrikpedersen.eatingwithfriends_gradedassignment.database.daos.BookingDao;
import com.fredrikpedersen.eatingwithfriends_gradedassignment.database.models.Booking;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class BookingRepository {

    private static final String TAG = "BookingRepository";
    private BookingDao bookingDao;
    private LiveData<List<Booking>> allBookingsLiveData;

    public BookingRepository(Application application) {
        BookingDatabase database = BookingDatabase.getInstance(application);
        bookingDao = database.bookingDao();
        allBookingsLiveData = bookingDao.getAllBookingsAsLiveData();
    }

    public void insert(Booking booking) {
        new InsertBookingAsyncTask(bookingDao).execute(booking);
    }
    public void update(Booking booking) {
        new UpdateBookingAsyncTask(bookingDao).execute(booking);
    }
    public void delete(Booking booking) {
        new DeleteBookingAsyncTask(bookingDao).execute(booking);
    }
    public LiveData<List<Booking>> getAllBookingsAsLiveData() {
        return allBookingsLiveData;
    }
    public List<Booking> getAllBookingsAsList() {
        try {
            return new getAllBookingsAsList(bookingDao).execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    private static class InsertBookingAsyncTask extends AsyncTask<Booking, Void, Void> {
        private BookingDao bookingDao;

        private InsertBookingAsyncTask(BookingDao bookingDao) {
            this.bookingDao = bookingDao;
        }

        @Override
        protected Void doInBackground(Booking... bookings) {
            bookingDao.insert(bookings[0]);
            return null;
        }
    }
    private static class UpdateBookingAsyncTask extends AsyncTask<Booking, Void, Void> {
        private BookingDao bookingDao;

        private UpdateBookingAsyncTask(BookingDao bookingDao) {
            this.bookingDao = bookingDao;
        }

        @Override
        protected Void doInBackground(Booking... bookings) {
            bookingDao.update(bookings[0]);
            return null;
        }
    }
    private static class DeleteBookingAsyncTask extends AsyncTask<Booking, Void, Void> {
        private BookingDao bookingDao;

        private DeleteBookingAsyncTask(BookingDao bookingDao) {
            this.bookingDao = bookingDao;
        }

        @Override
        protected Void doInBackground(Booking... bookings) {
            bookingDao.delete(bookings[0]);
            return null;
        }
    }
    private static class getAllBookingsAsList extends AsyncTask<Void, Void, List<Booking>> {

        private BookingDao bookingDao;

        private getAllBookingsAsList(BookingDao bookingDao) {
            this.bookingDao = bookingDao;
        }

        @Override
        protected List<Booking> doInBackground(Void... voids) {
            return bookingDao.getAllBookingsAsList();
        }
    }
}
