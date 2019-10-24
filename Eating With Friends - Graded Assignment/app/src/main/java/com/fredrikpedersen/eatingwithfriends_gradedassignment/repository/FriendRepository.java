package com.fredrikpedersen.eatingwithfriends_gradedassignment.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.fredrikpedersen.eatingwithfriends_gradedassignment.database.BookingDatabase;
import com.fredrikpedersen.eatingwithfriends_gradedassignment.database.daos.FriendDao;
import com.fredrikpedersen.eatingwithfriends_gradedassignment.database.models.Friend;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class FriendRepository {

    private static final String TAG = "FriendRepository";

    private FriendDao friendDao;
    private LiveData<List<Friend>> allFriendsLiveData;

    public FriendRepository(Application application) {
        BookingDatabase database = BookingDatabase.getInstance(application);
        friendDao = database.friendDao();
        allFriendsLiveData = friendDao.getAllFriendsAsLiveData();
    }

    public void insert(Friend friend) {
        new InsertFriendAsyncTask(friendDao).execute(friend);
    }
    public void update(Friend friend) {
        new UpdateFriendAsyncTask(friendDao).execute(friend);
    }
    public void delete(Friend friend) {
        new DeleteFriendAsyncTask(friendDao).execute(friend);
    }
    public LiveData<List<Friend>> getAllFriendsAsLiveData() {
        return allFriendsLiveData;
    }
    public List<Friend> getAllFriendsAsList() {
        try {
            return new getAllFriendsAsList(friendDao).execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    private static class InsertFriendAsyncTask extends AsyncTask<Friend, Void, Void> {
        private FriendDao friendDao;

        private InsertFriendAsyncTask(FriendDao friendDao) {
            this.friendDao = friendDao;
        }

        @Override
        protected Void doInBackground(Friend... friends) {
            friendDao.insert(friends[0]);
            return null;
        }
    }
    private static class UpdateFriendAsyncTask extends AsyncTask<Friend, Void, Void> {
        private FriendDao friendDao;

        private UpdateFriendAsyncTask(FriendDao friendDao) {
            this.friendDao = friendDao;
        }

        @Override
        protected Void doInBackground(Friend... friends) {
            friendDao.update(friends[0]);
            return null;
        }
    }
    private static class DeleteFriendAsyncTask extends AsyncTask<Friend, Void, Void> {
        private FriendDao friendDao;

        private DeleteFriendAsyncTask(FriendDao friendDao) {
            this.friendDao = friendDao;
        }

        @Override
        protected Void doInBackground(Friend... friends) {
            friendDao.delete(friends[0]);
            return null;
        }
    }
    private static class getAllFriendsAsList extends AsyncTask<Void, Void, List<Friend>> {

        private FriendDao friendDao;

        private getAllFriendsAsList(FriendDao friendDao) {
            this.friendDao = friendDao;
        }

        @Override
        protected List<Friend> doInBackground(Void... voids) {
            return friendDao.getAllFriendsAsList();
        }
    }
}
