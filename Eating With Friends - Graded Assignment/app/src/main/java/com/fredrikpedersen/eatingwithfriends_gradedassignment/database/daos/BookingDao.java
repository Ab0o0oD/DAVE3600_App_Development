package com.fredrikpedersen.eatingwithfriends_gradedassignment.database.daos;

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
    void insert(Booking booking);

    @Update
    void update(Booking booking);

    @Delete
    void delete(Booking booking);

    @Query("DELETE FROM booking_table")
    void deleteAllBookings();

    @Query("SELECT * FROM booking_table ORDER BY id DESC")
    LiveData<List<Booking>> getAllBookings();
}
