package com.s306631.room4you.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.s306631.room4you.models.Room;
import com.s306631.room4you.repository.RoomRepository;

import java.util.List;

public class RoomViewModel extends AndroidViewModel {

    private static final String TAG = "RoomViewModel";

    private RoomRepository roomRepository;

    public RoomViewModel(@NonNull Application application) {
        super(application);
        roomRepository = new RoomRepository();
    }

    public List<Room> getAllRoomsAsList() {
        return roomRepository.getRoomsFromWebService();
    }
}
