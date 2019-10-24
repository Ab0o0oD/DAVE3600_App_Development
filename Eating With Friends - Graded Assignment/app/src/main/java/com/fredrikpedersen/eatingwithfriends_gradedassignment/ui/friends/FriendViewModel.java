package com.fredrikpedersen.eatingwithfriends_gradedassignment.ui.friends;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.fredrikpedersen.eatingwithfriends_gradedassignment.database.models.Friend;
import com.fredrikpedersen.eatingwithfriends_gradedassignment.repository.FriendRepository;

import java.util.List;

public class FriendViewModel extends AndroidViewModel {

    private FriendRepository repository;
    private LiveData<List<Friend>> allFriends;


    public FriendViewModel(@NonNull Application application) {
        super(application);
        repository = new FriendRepository(application);
        allFriends = repository.getAllFriendsAsLiveData();
    }

    public void insert(Friend friend) {
        repository.insert(friend);
    }
    public void update(Friend friend) {
        repository.update(friend);
    }
    public void delete(Friend friend) {
        repository.delete(friend);
    }
    LiveData<List<Friend>> getAllFriendsAsLiveData() {
        return allFriends;
    }
    public List<Friend> getAllFriendsAsList() {
        return repository.getAllFriendsAsList();
    }
}