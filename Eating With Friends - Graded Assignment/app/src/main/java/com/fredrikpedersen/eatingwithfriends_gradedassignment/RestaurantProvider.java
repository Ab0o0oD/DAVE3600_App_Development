package com.fredrikpedersen.eatingwithfriends_gradedassignment;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fredrikpedersen.eatingwithfriends_gradedassignment.database.BookingDatabase;
import com.fredrikpedersen.eatingwithfriends_gradedassignment.database.daos.RestaurantDao;
import com.fredrikpedersen.eatingwithfriends_gradedassignment.database.models.Booking;
import com.fredrikpedersen.eatingwithfriends_gradedassignment.database.models.Restaurant;

public class RestaurantProvider extends ContentProvider {

    public static final String AUTHORITY = "com.fredrikpedersen.contentproviderrestaurant";

    public static final Uri URI_RESTAURANT = Uri.parse("content://" + AUTHORITY + "/" + Restaurant.TABLE_NAME);

    private static final int CODE_RESTAURANT_DIR = 1;

    private static final int CODE_RESTAURANT_ITEM = 2;

    private static final UriMatcher MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        MATCHER.addURI(AUTHORITY, Booking.TABLE_NAME, CODE_RESTAURANT_DIR);
        MATCHER.addURI(AUTHORITY, Booking.TABLE_NAME + "/*", CODE_RESTAURANT_ITEM);
    }

    @Override
    public boolean onCreate() {
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final int code = MATCHER.match(uri);

        if (code == CODE_RESTAURANT_DIR || code == CODE_RESTAURANT_ITEM) {
            final Context context = getContext();

            if (context == null) {
                return null;

            }
            RestaurantDao restaurantDao = BookingDatabase.getInstance(context).restaurantDao();
            final Cursor cursor;

            if (code == CODE_RESTAURANT_DIR) {
                cursor = restaurantDao.selectAllCursor();
            } else {
                cursor = restaurantDao.selectById(ContentUris.parseId(uri));
            }
            cursor.setNotificationUri(context.getContentResolver(), uri);
            return cursor;
        } else {
            throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (MATCHER.match(uri)) {
            case CODE_RESTAURANT_DIR:
                return "vnd.android.cursor.dir/" + AUTHORITY + "." + Restaurant.TABLE_NAME;
            case CODE_RESTAURANT_ITEM:
                return "vnd.android.cursor.item/" + AUTHORITY + "." + Restaurant.TABLE_NAME;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        throw new UnsupportedOperationException("Not allowed to insert data to this app");
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        switch (MATCHER.match(uri)) {
            case CODE_RESTAURANT_DIR:
                throw new IllegalArgumentException("Inavlid URI, cannot update without ID" + uri);

            case CODE_RESTAURANT_ITEM:
                final Context context = getContext();
                if (context == null) {
                    return 0;
                }
                final int count = BookingDatabase.getInstance(context).restaurantDao().deleteById(ContentUris.parseId(uri));
                context.getContentResolver().notifyChange(uri, null);
                return count;

            default:
                throw new IllegalArgumentException("Unknow URI: " + uri);
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        throw new UnsupportedOperationException("Not allowed to update items in the database");
    }
}
