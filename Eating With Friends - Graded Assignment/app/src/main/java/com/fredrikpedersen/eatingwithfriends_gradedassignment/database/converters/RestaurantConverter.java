package com.fredrikpedersen.eatingwithfriends_gradedassignment.database.converters;

import androidx.room.TypeConverter;

import com.fredrikpedersen.eatingwithfriends_gradedassignment.database.models.Restaurant;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class RestaurantConverter {

    @TypeConverter
    public static Restaurant stringToRestaurant(String data) {
        if (data == null) {
            return null;
        }

        Type objectType = new TypeToken<Restaurant>() {}.getType();
        return new Gson().fromJson(data, objectType);
    }

    @TypeConverter
    public static String restaurantToString(Restaurant restaurant) {
        return new Gson().toJson(restaurant);
    }
}
