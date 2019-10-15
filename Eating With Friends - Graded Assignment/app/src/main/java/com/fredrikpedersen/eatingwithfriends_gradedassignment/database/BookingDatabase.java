package com.fredrikpedersen.eatingwithfriends_gradedassignment.database;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.fredrikpedersen.eatingwithfriends_gradedassignment.database.daos.BookingDao;
import com.fredrikpedersen.eatingwithfriends_gradedassignment.database.daos.FriendDao;
import com.fredrikpedersen.eatingwithfriends_gradedassignment.database.models.Booking;
import com.fredrikpedersen.eatingwithfriends_gradedassignment.database.models.Friend;

import java.util.ArrayList;
import java.util.List;

@Database(entities = {Booking.class, Friend.class}, version = 1, exportSchema = false)
@TypeConverters(Converters.class)
public abstract class BookingDatabase extends RoomDatabase {
    private static final String TAG = "BookingDatabase";

    //Use the Singleton pattern for databases to prevent instantiating it more than once
    private static BookingDatabase instance;

    //Room generates necessary code for these methods when the database is instantiated
    public abstract BookingDao bookingDao();
    public abstract FriendDao friendDao();

    public static synchronized BookingDatabase getInstance(Context context) {
        Log.d(TAG, "getInstance: TRYING TO CREATE DATABASE");
        if (instance == null) {
            Log.d(TAG, "getInstance: SEEDING DATABASE");
            instance = seedDatabase(context);
        }
        return instance;
    }

    private static BookingDatabase seedDatabase(final Context cotext) {
        return Room.databaseBuilder(cotext, BookingDatabase.class, "booking_database")
                .fallbackToDestructiveMigration()
                .addCallback(roomCallBack)
                .build();
    }

    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback() {

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new SeedDbAsyncTask(instance).execute();
        }
    };

    //Class used to seed the DB with some hardcoded values
    private static class SeedDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private BookingDao bookingDao;
        private FriendDao friendDao;

        private SeedDbAsyncTask(BookingDatabase db) {

            bookingDao = db.bookingDao();
            friendDao = db.friendDao();
        }

        private List<Friend> createFriends() {
            List<Friend> seedFriends = new ArrayList<>();
            seedFriends.add(new Friend("Anders", "87654321"));
            seedFriends.add(new Friend("Martina", "12345678"));
            seedFriends.add(new Friend("Nikita", "73738012"));
            seedFriends.add(new Friend("Signe", "12398765"));
            return seedFriends;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            List<Friend> seedFriends = createFriends();
            for (Friend friend : seedFriends) {
                friendDao.insert(friend);
            }

            bookingDao.insert(new Booking("Casa Mia", "Casaveien 99", "29 01 2019", "10:45", seedFriends));
            bookingDao.insert(new Booking("Hells Kitchen", "Highway 666", "14 10 2019", "12:30", seedFriends));
            bookingDao.insert(new Booking("Datatorget Gourmet", "Pilestredet 35", "27 10 2019", "23:59", seedFriends));
            return null;
        }
    }
}
