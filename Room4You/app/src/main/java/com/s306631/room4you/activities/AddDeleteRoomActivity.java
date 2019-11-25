package com.s306631.room4you.activities;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.s306631.room4you.R;
import com.s306631.room4you.models.Building;
import com.s306631.room4you.models.Room;
import com.s306631.room4you.ui.fragments.MapFragment;
import com.s306631.room4you.viewModels.BuildingViewModel;
import com.s306631.room4you.viewModels.RoomViewModel;

import java.util.ArrayList;
import java.util.List;

public class AddDeleteRoomActivity extends AppCompatActivity implements Spinner.OnItemSelectedListener {

    private static final String TAG = "AddDeleteRoomActivity";

    private RelativeLayout layoutDeleteRoom, layoutRoomMap;
    private EditText editTextRoomName;
    private ImageView imageViewCoordinates;
    private TextView textViewCoordinates;
    private Button buttonAddRoom, buttonConfirmCoordinates, buttonHideMap, buttonDeleteRoom;
    private Spinner spinnerBuildingsForNewRoom, spinnerFloorsInNewRoomBuilding, spinnerBuilding, spinnerFloor, spinnerRoom;

    private BuildingViewModel buildingViewModel;
    private RoomViewModel roomViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_delete_room);

        buildingViewModel = ViewModelProviders.of(this).get(BuildingViewModel.class);
        roomViewModel = ViewModelProviders.of(this).get(RoomViewModel.class);

        initializeViews();
        fillNewRoomBuildingSpinner();

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    /* --------- Register Room --------- */
    private void registerRoom() {
        String name = getRoomNameFromView();
        Building selectedBuilding = (Building)spinnerBuildingsForNewRoom.getSelectedItem();
        int floor = (int)spinnerFloorsInNewRoomBuilding.getSelectedItem();
        String coordinates = getCoordinatesFromView();

        if (name == null || coordinates.equals("")) {
            Log.d(TAG, "registerBuilding: Something went wrong!");
            return;
        }

        Room room = new Room(selectedBuilding.getBuildingId(), name, floor, coordinates);
        roomViewModel.postRoom(this, room);
    }

    private String getRoomNameFromView() {
        String name = editTextRoomName.getText().toString();

        if (name.length() == 0) {
            editTextRoomName.setError("The building needs a name!");
            return null;
        } else if (name.length() > 30) {
            editTextRoomName.setError("Building name can't be longer than 30 character!");
            return null;
        }

        return name;
    }

    private String getCoordinatesFromView() {
        String coordinates = textViewCoordinates.getText().toString();

        if (coordinates.equals("") || coordinates.equals("No Coordinates Selected")) {
            textViewCoordinates.setText(getResources().getString(R.string.no_coordinates_selected));
            textViewCoordinates.setTextColor(Color.RED);
            return null;
        }

        return coordinates;
    }

    public void fillNewRoomBuildingSpinner() {
        List<Building> buildingList = buildingViewModel.getAllBuildingsAsList();
        ArrayAdapter<Building> buildingSpinnerAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, buildingList);
        buildingSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBuildingsForNewRoom.setAdapter(buildingSpinnerAdapter);
    }

    public void fillFloorsInNewRoomBuildingSpinner() {
        Log.d(TAG, "fillFloorsInNewRoomBuildingSpinner: CALLED");
        Building selectedBuilding = (Building)spinnerBuildingsForNewRoom.getSelectedItem();
        int floors = selectedBuilding.getFloors();

        List<Integer> floorsList = new ArrayList<>();
        for (int i = 1; i <= floors; i++) {
            floorsList.add(i);
        }

        for (Integer integer : floorsList) {
            Log.d(TAG, "fillFloorsInNewRoomBuildingSpinner: INTEGERLIST " + integer);
        }

        ArrayAdapter<Integer> floorSpinnerAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, floorsList);
        floorSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFloorsInNewRoomBuilding.setAdapter(floorSpinnerAdapter);
    }

    public void openMap() {
        Log.d(TAG, "openMap: IS CALLED");
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.layout_room_map, new MapFragment());
        ft.commit();

        layoutDeleteRoom.setVisibility(RelativeLayout.GONE);
        layoutRoomMap.setVisibility(RelativeLayout.VISIBLE);
        buttonAddRoom.setVisibility(View.INVISIBLE);
        buttonHideMap.setVisibility(View.VISIBLE);
        buttonConfirmCoordinates.setVisibility(View.VISIBLE);

        textViewCoordinates.setClickable(false);
        imageViewCoordinates.setClickable(false);
        Toast.makeText(this, "Tap the map to choose coordinates", Toast.LENGTH_SHORT).show();
    }

    private void confirmCoordinates() {
        String coordinates = getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE).getString("Chosen Coordinates", "No Coordinates Selected");
        if (coordinates.equals("No Coordinates Selected")) {
            textViewCoordinates.setTextColor(Color.RED);
        } else {
            textViewCoordinates.setTextColor(Color.BLACK);
        }

        removeMap();
        textViewCoordinates.setText(coordinates);
    }

    private void removeMap() {
        layoutRoomMap.setVisibility(RelativeLayout.GONE);
        layoutDeleteRoom.setVisibility(RelativeLayout.VISIBLE);
        buttonAddRoom.setVisibility(View.VISIBLE);
        buttonConfirmCoordinates.setVisibility(View.INVISIBLE);
        buttonHideMap.setVisibility(View.INVISIBLE);
        textViewCoordinates.setClickable(true);
        imageViewCoordinates.setClickable(true);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Log.d(TAG, "onItemSelected: CALLED");
        if (parent == spinnerBuildingsForNewRoom) {
            fillFloorsInNewRoomBuildingSpinner();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void initializeViews() {
        layoutDeleteRoom = findViewById(R.id.layout_delete_room);
        layoutRoomMap = findViewById(R.id.layout_room_map);

        editTextRoomName = findViewById(R.id.edit_text_room_name);

        imageViewCoordinates = findViewById(R.id.image_view_room_coordinates);
        imageViewCoordinates.setOnClickListener(v -> openMap());

        textViewCoordinates = findViewById(R.id.text_view_room_coordinates);
        textViewCoordinates.setOnClickListener(v -> openMap());

        buttonAddRoom = findViewById(R.id.button_add_room);
        buttonAddRoom.setOnClickListener(v -> registerRoom());

        buttonConfirmCoordinates = findViewById(R.id.button_confirm_room_coordinates);
        buttonConfirmCoordinates.setVisibility(View.INVISIBLE);
        buttonConfirmCoordinates.setOnClickListener(v -> confirmCoordinates());

        buttonHideMap = findViewById(R.id.button_hide_room_map);
        buttonHideMap.setVisibility(View.INVISIBLE);
        buttonHideMap.setOnClickListener(v -> removeMap());

        buttonDeleteRoom = findViewById(R.id.button_delete_room);

        spinnerBuildingsForNewRoom = findViewById(R.id.spinner_buildings_for_new_room);
        spinnerBuildingsForNewRoom.setOnItemSelectedListener(this);

        spinnerFloorsInNewRoomBuilding = findViewById(R.id.spinner_floors_in_new_room_building);

        spinnerBuilding = findViewById(R.id.spinner_rooms_building);
        spinnerFloor = findViewById(R.id.spinner_floors_in_building);
        spinnerRoom = findViewById(R.id.spinner_deleteable_rooms);
    }
}
