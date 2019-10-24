package com.fredrikpedersen.eatingwithfriends_gradedassignment.ui.restaurants;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.fredrikpedersen.eatingwithfriends_gradedassignment.database.models.Restaurant;
import com.fredrikpedersen.eatingwithfriends_gradedassignment.repository.RestaurantRepository;

import java.util.List;

public class RestaurantViewModel extends AndroidViewModel {

    private RestaurantRepository repository;
    private LiveData<List<Restaurant>> allRestaurantsLiveData;

    public RestaurantViewModel(@NonNull Application application) {
        super(application);
        repository = new RestaurantRepository(application);
        allRestaurantsLiveData = repository.getAllRestaurantsAsLiveData();
    }

    public void insert(Restaurant restaurant) {
        repository.insert(restaurant);
    }
    public void update(Restaurant restaurant) {
        repository.update(restaurant);
    }
    public void delete(Restaurant restaurant) {
        repository.delete(restaurant);
    }
    LiveData<List<Restaurant>> getAllRestaurantsAsLiveData() {
        return allRestaurantsLiveData;
    }
    public List<Restaurant> getAllRestaurantsAsList() {
        return repository.getAllRestaurantsAsList();
    }
}
