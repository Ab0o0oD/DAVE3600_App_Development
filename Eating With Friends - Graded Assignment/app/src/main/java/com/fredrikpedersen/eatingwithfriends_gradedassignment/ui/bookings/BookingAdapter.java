package com.fredrikpedersen.eatingwithfriends_gradedassignment.ui.bookings;

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

import java.util.List;

public class BookingAdapter extends ListAdapter<Booking, BookingAdapter.BookingHolder> {

    private OnItemClickListener listener;

    public BookingAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Booking> DIFF_CALLBACK = new DiffUtil.ItemCallback<Booking>() {
        @Override
        public boolean areItemsTheSame(@NonNull Booking oldItem, @NonNull Booking newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Booking oldItem, @NonNull Booking newItem) {
            if (oldItem.getDate().equals(newItem.getDate()) && oldItem.getTime().equals(newItem.getTime())) {
                return oldItem.getRestaurantName().equals(newItem.getRestaurantName()) &&
                        oldItem.getAddress().equals(newItem.getAddress()) &&
                        oldItem.getFriends().equals(newItem.getFriends());
            } else {
                return false;
            }
        }
    };

    @NonNull
    @Override
    public BookingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.booking_item, parent, false);
        return new BookingHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingHolder holder, int position) {
        Booking currentBooking = getItem(position);
        holder.textViewRestaurantName.setText(currentBooking.getRestaurantName());
        holder.textViewAddress.setText(currentBooking.getAddress());
        holder.textViewDateTime.setText(currentBooking.getDate() + " " + currentBooking.getTime());
    }

    public Booking getBookingAt(int position) {
        return getItem(position);
    }

    /* ----- Inner classes and Interfaces ----- */

    class BookingHolder extends RecyclerView.ViewHolder {
        private TextView textViewRestaurantName;
        private TextView textViewAddress;
        private TextView textViewDateTime;

        public BookingHolder(@NonNull View itemView) {
            super(itemView);
            textViewRestaurantName = itemView.findViewById(R.id.text_view_restaurant_name);
            textViewAddress = itemView.findViewById(R.id.text_view_address);
            textViewDateTime = itemView.findViewById(R.id.text_view_date_time);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(getItem(position));
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Booking booking);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
