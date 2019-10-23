package com.fredrikpedersen.eatingwithfriends_gradedassignment.database.converters;

import androidx.room.TypeConverter;

import com.fredrikpedersen.eatingwithfriends_gradedassignment.database.models.Friend;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class FriendConverter {

    @TypeConverter
    public static List<Friend> stringToFriend(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<Friend>>() {}.getType();
        return new Gson().fromJson(data, listType);
    }

    @TypeConverter
    public static String friendToString(List<Friend> friends) {
        return new Gson().toJson(friends);
    }
}
