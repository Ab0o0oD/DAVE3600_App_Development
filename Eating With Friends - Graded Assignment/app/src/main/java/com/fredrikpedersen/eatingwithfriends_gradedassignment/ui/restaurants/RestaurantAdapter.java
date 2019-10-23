package com.fredrikpedersen.eatingwithfriends_gradedassignment.ui.restaurants;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.fredrikpedersen.eatingwithfriends_gradedassignment.R;
import com.fredrikpedersen.eatingwithfriends_gradedassignment.database.models.Restaurant;
import com.fredrikpedersen.eatingwithfriends_gradedassignment.ui.OnItemClickListener;

public class RestaurantAdapter extends ListAdapter<Restaurant, RestaurantAdapter.RestaurantViewHolder> {

    private OnItemClickListener listener;

    RestaurantAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Restaurant> DIFF_CALLBACK = new DiffUtil.ItemCallback<Restaurant>() {
        @Override
        public boolean areItemsTheSame(@NonNull Restaurant oldItem, @NonNull Restaurant newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Restaurant oldItem, @NonNull Restaurant newItem) {
            return oldItem.getRestaurantName().equals(newItem.getRestaurantName()) &&
                    oldItem.getAddress().equals(newItem.getAddress()) &&
                    oldItem.getPhoneNumber().equals(newItem.getPhoneNumber()) &&
                    oldItem.getType().equals(newItem.getType());
        }
    };

    @NonNull
    @Override
    public RestaurantAdapter.RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_item, parent, false);
        return new RestaurantAdapter.RestaurantViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantAdapter.RestaurantViewHolder holder, int position) {
        Restaurant currentRestaurant = getItem(position);
        holder.textViewRestaurantName.setText(currentRestaurant.getRestaurantName());
        holder.textViewRestaurantAddress.setText(currentRestaurant.getAddress());
        holder.textViewPhoneNumber.setText(currentRestaurant.getPhoneNumber());
        holder.textViewType.setText(currentRestaurant.getType());

    }

    Restaurant getRestaurantFromPosition(int position) {
        return getItem(position);
    }

    /* ----- ViewHolder ----- */
    class RestaurantViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewRestaurantName;
        private TextView textViewRestaurantAddress;
        private TextView textViewPhoneNumber;
        private TextView textViewType;

        RestaurantViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewRestaurantName = itemView.findViewById(R.id.text_view_restaurant_recycler_name);
            textViewRestaurantAddress = itemView.findViewById(R.id.text_view_restaurant_address);
            textViewPhoneNumber = itemView.findViewById(R.id.text_view_restaurant_phonenumber);
            textViewType = itemView.findViewById(R.id.text_view_restaurant_type);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(getItem(position));
                }
            });
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
