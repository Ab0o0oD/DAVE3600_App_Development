package com.fredrikpedersen.eatingwithfriends_gradedassignment.database;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.fredrikpedersen.eatingwithfriends_gradedassignment.database.daos.BookingDao;
import com.fredrikpedersen.eatingwithfriends_gradedassignment.database.daos.FriendDao;
import com.fredrikpedersen.eatingwithfriends_gradedassignment.database.models.Booking;
import com.fredrikpedersen.eatingwithfriends_gradedassignment.database.models.Friend;

@Database(entities = {Booking.class, Friend.class}, version = 3, exportSchema = false)
@TypeConverters(Converters.class)
public abstract class BookingDatabase extends RoomDatabase {
    private static final String TAG = "BookingDatabase";

    //Use the Singleton pattern for databases to prevent instantiating it more than once
    private static BookingDatabase instance;

    //Room generates necessary code for these methods when the database is instantiated
    public abstract BookingDao bookingDao();
    public abstract FriendDao friendDao();

    public static synchronized BookingDatabase getInstance(Context context) {
        if (instance == null) {
            instance = createDatabase(context);
        }
        return instance;
    }

    private static BookingDatabase createDatabase(final Context cotext) {
        return Room.databaseBuilder(cotext, BookingDatabase.class, "booking_database")
                .fallbackToDestructiveMigration()
                .build();
    }
}
