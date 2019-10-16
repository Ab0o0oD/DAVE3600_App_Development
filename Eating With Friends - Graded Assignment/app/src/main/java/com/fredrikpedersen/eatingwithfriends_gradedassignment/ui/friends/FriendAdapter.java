package com.fredrikpedersen.eatingwithfriends_gradedassignment.ui.friends;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.fredrikpedersen.eatingwithfriends_gradedassignment.R;
import com.fredrikpedersen.eatingwithfriends_gradedassignment.database.models.Friend;
import com.fredrikpedersen.eatingwithfriends_gradedassignment.ui.OnItemClickListener;

public class FriendAdapter extends ListAdapter<Friend, FriendAdapter.FriendViewHolder> {

    private OnItemClickListener listener;

    public FriendAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Friend> DIFF_CALLBACK = new DiffUtil.ItemCallback<Friend>() {
        @Override
        public boolean areItemsTheSame(@NonNull Friend oldItem, @NonNull Friend newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Friend oldItem, @NonNull Friend newItem) {
            return oldItem.getFirstName().equals(newItem.getFirstName()) &&
                    oldItem.getLastName().equals(newItem.getLastName()) &&
                    oldItem.getPhoneNumber().equals(newItem.getPhoneNumber());
        }
    };

    @NonNull
    @Override
    public FriendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_item, parent, false);
        return new FriendViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendViewHolder holder, int position) {
        Friend currentFriend = getItem(position);
        holder.textViewFirstName.setText(currentFriend.getFirstName());
        holder.textViewLastName.setText(currentFriend.getLastName());
        holder.textViewphoneNumber.setText(currentFriend.getPhoneNumber());
    }

    /* ----- ViewHolder ----- */
    class FriendViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewFirstName;
        private TextView textViewLastName;
        private TextView textViewphoneNumber;

        FriendViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewFirstName = itemView.findViewById(R.id.text_view_first_name);
            textViewLastName = itemView.findViewById(R.id.text_view_last_name);
            textViewphoneNumber = itemView.findViewById(R.id.text_view_phone_number);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(getItem(position));
                }
            });
        }
    }

    //TODO This is to be used later when you are going to be able to click an item to edit it
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


}
