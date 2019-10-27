package com.fredrikpedersen.eatingwithfriends_gradedassignment.ui.bookings;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.fredrikpedersen.eatingwithfriends_gradedassignment.R;
import com.fredrikpedersen.eatingwithfriends_gradedassignment.database.models.Booking;
import com.fredrikpedersen.eatingwithfriends_gradedassignment.database.models.Friend;
import com.fredrikpedersen.eatingwithfriends_gradedassignment.ui.OnItemClickListener;
import com.fredrikpedersen.eatingwithfriends_gradedassignment.util.DateFormater;

import java.util.List;

public class BookingAdapter extends ListAdapter<Booking, BookingAdapter.BookingViewHolder> {
    private static final String TAG = "BookingAdapter";

    private OnItemClickListener listener;
    private View.OnLongClickListener longClickListener;

    BookingAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Booking> DIFF_CALLBACK = new DiffUtil.ItemCallback<Booking>() {
        @Override
        public boolean areItemsTheSame(@NonNull Booking oldItem, @NonNull Booking newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Booking oldItem, @NonNull Booking newItem) {
            //TODO THERE IS A BUG HERE. ALL CONTENTS ARE BEING UPDATED EVERY TIME A NEW ITEM IS ADDED!
            //TODO Bug comes with my way off adding temp bookings due to new Friend-objects being created each time.
            //TODO Make sure this works properly after all other functionality is implemented
            Log.d(TAG, "areContentsTheSame: "  + oldItem.getDate().equals(newItem.getDate()) + " " +  oldItem.getTime().equals(newItem.getTime()));
            if (oldItem.getDate().equals(newItem.getDate()) && oldItem.getTime().equals(newItem.getTime())) {
                return oldItem.getRestaurant().getRestaurantName().equals(newItem.getRestaurant().getRestaurantName()) &&
                        oldItem.getRestaurant().getAddress().equals(newItem.getRestaurant().getAddress()) &&
                        oldItem.getFriends().equals(newItem.getFriends());
            } else {
                return false;
            }
        }
    };

    @NonNull
    @Override
    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.booking_item, parent, false);
        return new BookingViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingViewHolder holder, int position) {
        Booking currentBooking = getItem(position);
        StringBuilder sb = new StringBuilder();
        List<Friend> friends = currentBooking.getFriends();
        if (friends != null) {
            for (Friend friend : friends) {
                sb.append(friend.getFirstName()).append(" ").append(friend.getLastName()).append("\n");
            }
        }

        holder.textViewRestaurantName.setText(currentBooking.getRestaurant().getRestaurantName());
        holder.textViewAddress.setText(currentBooking.getRestaurant().getAddress());
        holder.textViewDateTime.setText(DateFormater.formatDateText(currentBooking.getDate()) + " " + currentBooking.getTime());
        holder.textViewFriends.setText(sb.toString());
    }

    Booking getBookingFromPosition(int position) {
        return getItem(position);
    }

    /* ----- ViewHolder ----- */

    class BookingViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewRestaurantName;
        private TextView textViewAddress;
        private TextView textViewDateTime;
        private TextView textViewFriends;

        BookingViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewRestaurantName = itemView.findViewById(R.id.text_view_restaurant_name);
            textViewAddress = itemView.findViewById(R.id.text_view_address);
            textViewDateTime = itemView.findViewById(R.id.text_view_date_time);
            textViewFriends = itemView.findViewById(R.id.text_view_friends);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(getItem(position));
                }
            });
        }
    }

    void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    void setOnLongClickListener(View.OnLongClickListener longClickListener) {
        this.longClickListener = longClickListener;
    }
}
