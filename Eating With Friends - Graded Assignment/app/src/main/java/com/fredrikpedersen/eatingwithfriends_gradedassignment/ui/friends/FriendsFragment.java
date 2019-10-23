package com.fredrikpedersen.eatingwithfriends_gradedassignment.ui.friends;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fredrikpedersen.eatingwithfriends_gradedassignment.R;
import com.fredrikpedersen.eatingwithfriends_gradedassignment.util.StaticHolder;
import com.fredrikpedersen.eatingwithfriends_gradedassignment.activities.AddEditFriendActivity;
import com.fredrikpedersen.eatingwithfriends_gradedassignment.database.models.Friend;

import java.util.Objects;

import static android.app.Activity.RESULT_OK;

public class FriendsFragment extends Fragment {

    private static final String TAG = "FriendsFragment";
    private FriendViewModel friendViewModel;
    private FriendAdapter friendAdapter;

    public FriendsFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friends, container, false);

        friendAdapter = new FriendAdapter();
        initializeViews(view);

        friendViewModel = ViewModelProviders.of(this).get(FriendViewModel.class);
        friendViewModel.getAllFriends().observe(this, friendAdapter::submitList);
        friendAdapter.setOnItemClickListener(this::touchToEdit);

        return view;
    }

    private void initializeViews(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_friend);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(friendAdapter);
        swipeToDelete().attachToRecyclerView(recyclerView);
    }

    private void touchToEdit(Object item) {
        Friend friend = (Friend)item;
        Intent intent = new Intent(getActivity(), AddEditFriendActivity.class);
        intent.putExtra(StaticHolder.EXTRA_ID, friend.getId());
        intent.putExtra(StaticHolder.EXTRA_FIRST_NAME, friend.getFirstName());
        intent.putExtra(StaticHolder.EXTRA_LAST_NAME, friend.getLastName());
        intent.putExtra(StaticHolder.EXTRA_FRIEND_PHONE, friend.getPhoneNumber());

        startActivityForResult(intent, StaticHolder.EDIT_FRIEND_REQUEST);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == StaticHolder.EDIT_FRIEND_REQUEST && resultCode == RESULT_OK) {
            Toast.makeText(getActivity(), "Friend updated!", Toast.LENGTH_SHORT).show();
        }
    }
}