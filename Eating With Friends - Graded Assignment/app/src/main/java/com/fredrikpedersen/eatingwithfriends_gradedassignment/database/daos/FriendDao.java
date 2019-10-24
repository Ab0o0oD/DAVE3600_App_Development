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
    long insert(Friend friend);

    @Update
    int update(Friend friend);

    @Delete
    void delete(Friend friend);

    @Query("SELECT * FROM " + Friend.TABLE_NAME + " ORDER BY " + Friend.COLUMN_ID + " DESC")
    LiveData<List<Friend>> getAllFriendsAsLiveData();

    @Query("SELECT * FROM " + Friend.TABLE_NAME + " ORDER BY " + Friend.COLUMN_ID + " DESC")
    List<Friend> getAllFriendsAsList();
}