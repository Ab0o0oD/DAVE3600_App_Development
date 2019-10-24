package com.fredrikpedersen.eatingwithfriends_gradedassignment.database.daos;

import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.fredrikpedersen.eatingwithfriends_gradedassignment.database.models.Booking;

import java.util.List;

@Dao
public interface BookingDao {

    @Insert
    long insert(Booking booking);

    @Update
    int update(Booking booking);

    @Delete
    void delete(Booking booking);

    @Query("SELECT * FROM " + Booking.TABLE_NAME + " ORDER BY " + Booking.COLUMN_ID + " DESC")
    LiveData<List<Booking>> getAllBookingsAsLiveData();

    @Query("SELECT * FROM " + Booking.TABLE_NAME + " ORDER BY " + Booking.COLUMN_ID + " DESC")
    List<Booking> getAllBookingsAsList();
}
