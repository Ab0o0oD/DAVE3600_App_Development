package com.fredrikpedersen.eatingwithfriends_gradedassignment.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.fredrikpedersen.eatingwithfriends_gradedassignment.database.BookingDatabase;
import com.fredrikpedersen.eatingwithfriends_gradedassignment.database.daos.RestaurantDao;
import com.fredrikpedersen.eatingwithfriends_gradedassignment.database.models.Restaurant;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class RestaurantRepository {

    private static final String TAG = "FriendRepository";

    private RestaurantDao restaurantDao;
    private LiveData<List<Restaurant>> allRestaurantsLiveData;

    public RestaurantRepository(Application application) {
        BookingDatabase database = BookingDatabase.getInstance(application);
        restaurantDao = database.restaurantDao();
        allRestaurantsLiveData = restaurantDao.getAllRestaurantsAsLiveData();
    }

    public void insert(Restaurant restaurant) {
        new RestaurantRepository.InsertRestaurantAsyncTask(restaurantDao).execute(restaurant);
    }
    public void update(Restaurant restaurant) {
        new RestaurantRepository.UpdateRestaurantAsyncTask(restaurantDao).execute(restaurant);
    }
    public void delete(Restaurant restaurant) {
        new RestaurantRepository.DeleteRestaurantAsyncTask(restaurantDao).execute(restaurant);
    }
    public LiveData<List<Restaurant>> getAllRestaurantsAsLiveData() {
        return allRestaurantsLiveData;
    }
    public List<Restaurant> getAllRestaurantsAsList() {
        try {
            return new RestaurantRepository.getAllRestaurantsAsList(restaurantDao).execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    private static class InsertRestaurantAsyncTask extends AsyncTask<Restaurant, Void, Void> {
        private RestaurantDao restaurantDao;

        private InsertRestaurantAsyncTask(RestaurantDao restaurantDao) {
            this.restaurantDao = restaurantDao;
        }

        @Override
        protected Void doInBackground(Restaurant... restaurants) {
            restaurantDao.insert(restaurants[0]);
            return null;
        }
    }
    private static class UpdateRestaurantAsyncTask extends AsyncTask<Restaurant, Void, Void> {
        private RestaurantDao restaurantDao;

        private UpdateRestaurantAsyncTask(RestaurantDao restaurantDao) {
            this.restaurantDao = restaurantDao;        }

        @Override
        protected Void doInBackground(Restaurant... restaurants) {
            restaurantDao.update(restaurants[0]);
            return null;
        }
    }
    private static class DeleteRestaurantAsyncTask extends AsyncTask<Restaurant, Void, Void> {
        private RestaurantDao restaurantDao;

        private DeleteRestaurantAsyncTask(RestaurantDao restaurantDao) {
            this.restaurantDao = restaurantDao;        }

        @Override
        protected Void doInBackground(Restaurant... restaurants) {
            restaurantDao.delete(restaurants[0]);
            return null;
        }
    }
    private static class getAllRestaurantsAsList extends AsyncTask<Void, Void, List<Restaurant>> {
        private RestaurantDao restaurantDao;

        private getAllRestaurantsAsList(RestaurantDao restaurantDao) {
            this.restaurantDao = restaurantDao;
        }

        @Override
        protected List<Restaurant> doInBackground(Void... voids) {
            return restaurantDao.getAllRestaurantsAsList();
        }
    }
}
