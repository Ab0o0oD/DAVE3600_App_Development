package com.fredrikpedersen.eatingwithfriends_gradedassignment.database.daos;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.fredrikpedersen.eatingwithfriends_gradedassignment.database.models.Friend;

import java.util.List;

//Using Room makes SQL statements typesafe and prevents errors at runtime, making them occur at compiletime instead

@Dao
public interface FriendDao {

    @Insert
    void insert(Friend friend);

    @Update
    void update(Friend friend);

    @Delete
    void delete(Friend friend);

    @Query("DELETE FROM friend_table")
    void deleteAllFriends();

    @Query("SELECT * FROM friend_table ORDER BY id DESC")
    LiveData<List<Friend>> getAllFriends();
}