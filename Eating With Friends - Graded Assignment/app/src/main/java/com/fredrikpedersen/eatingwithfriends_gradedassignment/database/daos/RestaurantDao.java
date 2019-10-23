package com.fredrikpedersen.eatingwithfriends_gradedassignment.database.daos;

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
    void insert(Restaurant restaurant);

    @Update
    void update(Restaurant restaurant);

    @Delete
    void delete(Restaurant restaurant);

    @Query("DELETE FROM restaurant_table")
    void deleteAllRestaurants();

    @Query("SELECT * FROM restaurant_table ORDER BY id DESC")
    LiveData<List<Restaurant>> getAllRestaurantsAsLiveData();

    @Query("SELECT * FROM restaurant_table ORDER BY id DESC")
    List<Restaurant> getAllRestaurantsAsList();
}
