package com.fredrikpedersen.eatingwithfriends_gradedassignment.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.fredrikpedersen.eatingwithfriends_gradedassignment.database.converters.FriendConverter;
import com.fredrikpedersen.eatingwithfriends_gradedassignment.database.converters.RestaurantConverter;
import com.fredrikpedersen.eatingwithfriends_gradedassignment.database.daos.BookingDao;
import com.fredrikpedersen.eatingwithfriends_gradedassignment.database.daos.FriendDao;
import com.fredrikpedersen.eatingwithfriends_gradedassignment.database.daos.RestaurantDao;
import com.fredrikpedersen.eatingwithfriends_gradedassignment.database.models.Booking;
import com.fredrikpedersen.eatingwithfriends_gradedassignment.database.models.Friend;
import com.fredrikpedersen.eatingwithfriends_gradedassignment.database.models.Restaurant;

@Database(entities = {Booking.class, Friend.class, Restaurant.class}, version = 6, exportSchema = false)
@TypeConverters({FriendConverter.class, RestaurantConverter.class})
public abstract class BookingDatabase extends RoomDatabase {
    private static final String TAG = "BookingDatabase";

    //Use the Singleton pattern for databases to prevent instantiating it more than once
    private static BookingDatabase instance;

    //Room generates necessary code for these methods when the database is instantiated
    public abstract BookingDao bookingDao();
    public abstract FriendDao friendDao();
    public abstract RestaurantDao restaurantDao();

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
