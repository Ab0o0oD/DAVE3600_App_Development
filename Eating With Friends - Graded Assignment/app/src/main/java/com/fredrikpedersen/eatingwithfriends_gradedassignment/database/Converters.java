package com.fredrikpedersen.eatingwithfriends_gradedassignment.database;

import androidx.room.TypeConverter;

import com.fredrikpedersen.eatingwithfriends_gradedassignment.database.models.Friend;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class Converters {

    @TypeConverter
    public static List<Friend> fromString(Friend friend) {
        Type listType = new TypeToken<List<String>>() {}.getType();
        return new Gson().fromJson(friend.toString(), listType);
    }


}
