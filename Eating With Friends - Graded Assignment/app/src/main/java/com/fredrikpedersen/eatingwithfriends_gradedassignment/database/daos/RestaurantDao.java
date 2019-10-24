package com.fredrikpedersen.eatingwithfriends_gradedassignment.database.daos;

import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.fredrikpedersen.eatingwithfriends_gradedassignment.database.models.Restaurant;

import java.util.List;

@Dao
public interface RestaurantDao {

    @Insert
    long insert(Restaurant restaurant);

    @Update
    int update(Restaurant restaurant);

    @Delete
    void delete(Restaurant restaurant);

    @Query("SELECT * FROM " + Restaurant.TABLE_NAME + " ORDER BY " + Restaurant.COLUMN_ID + " DESC")
    LiveData<List<Restaurant>> getAllRestaurantsAsLiveData();

    @Query("SELECT * FROM " + Restaurant.TABLE_NAME + " ORDER BY " + Restaurant.COLUMN_ID + " DESC")
    List<Restaurant> getAllRestaurantsAsList();

    @Query("SELECT * FROM " + Restaurant.TABLE_NAME)
    Cursor selectAllCursor();

    @Query("SELECT * FROM " + Restaurant.TABLE_NAME + " WHERE " + Restaurant.COLUMN_ID + " = :id")
    Cursor selectById(long id);

    @Query("DELETE FROM " + Restaurant.TABLE_NAME + " WHERE " + Restaurant.COLUMN_ID + " = :id")
    int deleteById(long id);
}
