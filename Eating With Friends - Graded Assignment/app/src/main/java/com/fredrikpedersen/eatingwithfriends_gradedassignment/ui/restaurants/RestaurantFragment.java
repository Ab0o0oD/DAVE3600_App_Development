package com.fredrikpedersen.eatingwithfriends_gradedassignment.ui.restaurants;

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
import com.fredrikpedersen.eatingwithfriends_gradedassignment.activities.AddEditFriendActivity;
import com.fredrikpedersen.eatingwithfriends_gradedassignment.activities.AddEditRestaurantActivity;
import com.fredrikpedersen.eatingwithfriends_gradedassignment.database.models.Restaurant;
import com.fredrikpedersen.eatingwithfriends_gradedassignment.util.BookingChecker;
import com.fredrikpedersen.eatingwithfriends_gradedassignment.util.StaticHolder;

import java.util.Objects;

import static android.app.Activity.RESULT_OK;

public class RestaurantFragment extends Fragment {

    private static final String TAG = "FriendsFragment";
    private RestaurantViewModel restaurantViewModel;
    private RestaurantAdapter restaurantAdapter;

    public RestaurantFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_restaurants, container, false);

        restaurantAdapter = new RestaurantAdapter();
        initializeViews(view);

        restaurantViewModel = ViewModelProviders.of(this).get(RestaurantViewModel.class);
        restaurantViewModel.getAllRestaurantsAsLiveData().observe(this, restaurantAdapter::submitList);
        restaurantAdapter.setOnItemClickListener(this::touchToEdit);

        return view;
    }

    private void initializeViews(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_restaurants);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(restaurantAdapter);
        swipeToDelete().attachToRecyclerView(recyclerView);
    }

    private void touchToEdit(Object item) {
        Restaurant restaurant = (Restaurant)item;
        Intent intent = new Intent(getActivity(), AddEditRestaurantActivity.class);
        intent.putExtra(StaticHolder.EXTRA_ID, restaurant.getId());
        intent.putExtra(StaticHolder.EXTRA_RESTAURANT_NAME, restaurant.getRestaurantName());
        intent.putExtra(StaticHolder.EXTRA_RESTAURANT_ADDRESS, restaurant.getAddress());
        intent.putExtra(StaticHolder.EXTRA_RESTAURANT_PHONE, restaurant.getPhoneNumber());
        intent.putExtra(StaticHolder.EXTRA_RESTAURANT_TYPE, restaurant.getType());

        startActivityForResult(intent, StaticHolder.EDIT_RESTAURANT_REQUEST);
    }

    private ItemTouchHelper swipeToDelete() {
        return new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Restaurant restaurantFromPosition = restaurantAdapter.getRestaurantFromPosition(viewHolder.getAdapterPosition());
                if (!BookingChecker.isRestaurantPartOfBooking(Objects.requireNonNull(getActivity()).getApplication(),restaurantFromPosition)) {
                    restaurantViewModel.delete(restaurantFromPosition);
                    Toast.makeText(getActivity(), "Restaurant Deleted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Restaurant is part of a booking and cant be deleted", Toast.LENGTH_SHORT).show();
                    restaurantAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == StaticHolder.EDIT_RESTAURANT_REQUEST && resultCode == RESULT_OK) {
            Toast.makeText(getActivity(), "Restaurant updated!", Toast.LENGTH_SHORT).show();
        }
    }
}
