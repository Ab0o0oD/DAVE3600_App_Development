package com.fredrikpedersen.eatingwithfriends_gradedassignment.ui.friends;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fredrikpedersen.eatingwithfriends_gradedassignment.R;
import com.fredrikpedersen.eatingwithfriends_gradedassignment.database.models.Friend;

import java.util.Objects;

public class FriendsFragment extends Fragment {

    private static final String TAG = "FriendsFragment";
    private FriendViewModel friendViewModel;
    private FriendAdapter friendAdapter;

    public FriendsFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friends, container, false);

        Objects.requireNonNull(getActivity()).setTitle("Friends");
        friendAdapter = new FriendAdapter();
        initializeViews(view);

        friendViewModel = ViewModelProviders.of(this).get(FriendViewModel.class);
        friendViewModel.getAllFriends().observe(this, friendAdapter::submitList);

        //TODO THIS IS FOR TESTING PURPOSES. TO BE REMOVED!
        Button addFriend = view.findViewById(R.id.button_add_friend_test);
        addFriend.setOnClickListener(v -> {
            for (int i = 0; i < 7; i++) {
                friendViewModel.insert(new Friend("BF", "F"+i, "12345678"));
            }
        });

        return view;
    }

    private void initializeViews(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_friend);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(friendAdapter);
        swipeToDelete().attachToRecyclerView(recyclerView);
    }

    private ItemTouchHelper swipeToDelete() {
        return new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                friendViewModel.delete(friendAdapter.getFriendFromPosition(viewHolder.getAdapterPosition()));
                Toast.makeText(getActivity(), "Friend Deleted", Toast.LENGTH_SHORT).show();
            }
        });
    }
}