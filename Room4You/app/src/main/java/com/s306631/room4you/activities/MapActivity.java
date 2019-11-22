package com.s306631.room4you.activities;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.s306631.room4you.R;
import com.s306631.room4you.models.Booking;
import com.s306631.room4you.models.Building;
import com.s306631.room4you.models.Room;
import com.s306631.room4you.ui.CustomDialog;
import com.s306631.room4you.ui.OnDialogOptionSelectedListener;
import com.s306631.room4you.viewModels.BookingViewModel;
import com.s306631.room4you.viewModels.BuildingViewModel;
import com.s306631.room4you.viewModels.RoomViewModel;

import java.util.ArrayList;
import java.util.List;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback, Spinner.OnItemSelectedListener,
        GoogleMap.OnMapClickListener, GoogleMap.OnMarkerClickListener, OnCompleteListener, OnDialogOptionSelectedListener {

    private static final String TAG = "MapActivity";

    private Spinner buildingSpinner, floorSpinner, roomSpinner;
    private ImageView gpsIcon, buildingIcon;

    private RoomViewModel roomViewModel;
    private BuildingViewModel buildingViewModel;

    private List<Room> roomList;
    private List<Building> buildingList;

    private Room selectedRoom;


    private GoogleMap mMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        buildingViewModel = ViewModelProviders.of(this).get(BuildingViewModel.class);
        roomViewModel = ViewModelProviders.of(this).get(RoomViewModel.class);

        buildingList = buildingViewModel.getAllBuildingsAsList();
        roomList = roomViewModel.getAllRoomsAsList();

        initializeViews();
        fillBuildingSpinner();
        initializeMap();
    }

    /* ---------- Map Methods ---------- */

    public void moveMap(LatLng coordinates, float zoom) {
        Log.d(TAG, "moveMap: moving the map to: lat: " + coordinates.latitude + ", lng: " + coordinates.longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinates, zoom));
    }

    public void moveToCurrentBuilding() {
        Building currentBuilding = (Building)buildingSpinner.getSelectedItem();
        moveMap(currentBuilding.getCoordinates(), getResources().getInteger(R.integer.DEFAULT_ZOOM));
    }

    public void addRoomMarkers(List<Room> selectedBuildingRooms) {
        mMap.clear();
        for (Room room : selectedBuildingRooms) {
            mMap.addMarker(new MarkerOptions().position(room.getCoordinates()).title(room.getRoomName()).snippet(String.valueOf(room.getRoomId())));
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        CustomDialog bookingDialog = new CustomDialog(this, getResources().getString(R.string.booking_dialog_pt1) + " " + marker.getTitle() + getResources().getString(R.string.booking_dialog_pt2));
        bookingDialog.setOnDialogOptionSelectedListener(this);
        bookingDialog.show();

        for (Room room : roomList) {
            if ((room.getRoomId() == Integer.parseInt(marker.getSnippet()))) {
                selectedRoom = room;
            }
        }

        return false;
    }

    private void getUserLocation() {
        FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        final Task location = fusedLocationProviderClient.getLastLocation();
        location.addOnCompleteListener(this);
    }

    @Override
    public void onComplete(@NonNull Task task) {
        Location currentLocation = (Location)task.getResult();
        moveMap(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), getResources().getInteger(R.integer.DEFAULT_ZOOM));
    }

    /* ---------- UI-Elements ---------- */

    private void fillBuildingSpinner() {
        ArrayAdapter<Building> buildingSpinnerAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, buildingList);
        buildingSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        buildingSpinner.setAdapter(buildingSpinnerAdapter);
    }

    private void fillRoomSpinner(Building selectedBuilding, int selectedFloor) {
        List<Room> selectedBuildingRooms = new ArrayList<>();

        for (Room room : roomList) {
            if (room.getBuildingId() == selectedBuilding.getBuildingId() && room.getFloor() == selectedFloor) {
                selectedBuildingRooms.add(room);
            }
        }
        ArrayAdapter<Room> roomSpinnerAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, selectedBuildingRooms);
        roomSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roomSpinner.setAdapter(roomSpinnerAdapter);
        addRoomMarkers(selectedBuildingRooms);
    }

    private void fillFloorSpinner(Building selectedBuilding) {
        int floors = selectedBuilding.getFloors();
        List<Integer> selectedBuildingFloors = new ArrayList<>();

        for (int i = 1; i <= floors; i++) {
            selectedBuildingFloors.add(i);
        }

        ArrayAdapter<Integer> floorSpinnerAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, selectedBuildingFloors);
        floorSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        floorSpinner.setAdapter(floorSpinnerAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent == buildingSpinner) {
            Building selectedBuilding = buildingList.get(position);
            fillFloorSpinner(selectedBuilding);
            fillRoomSpinner(selectedBuilding, (int)floorSpinner.getSelectedItem());
            moveMap(selectedBuilding.getCoordinates(), getResources().getInteger(R.integer.DEFAULT_ZOOM));
        } else if (parent == floorSpinner) {
            fillRoomSpinner((Building)buildingSpinner.getSelectedItem(), (int)floorSpinner.getSelectedItem());
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    @Override
    public void onDialogOptionSelected() {
        BookRoomActivity.selectedRoom = selectedRoom;
        Intent intent = new Intent(this, BookRoomActivity.class);
        startActivity(intent);
    }


    /* ---------- Initialization ---------- */

    public void initializeViews() {
        buildingSpinner = findViewById(R.id.spinner_buildings);
        buildingSpinner.setOnItemSelectedListener(this);

        floorSpinner = findViewById(R.id.spinner_floors);
        floorSpinner.setOnItemSelectedListener(this);

        roomSpinner = findViewById(R.id.spinner_rooms);

        gpsIcon = findViewById(R.id.image_view_center_position);
        gpsIcon.setOnClickListener(v -> getUserLocation());

        buildingIcon = findViewById(R.id.image_view_center_building);
        buildingIcon.setOnClickListener(v -> moveToCurrentBuilding());
    }

    public void initializeMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(MapActivity.this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMarkerClickListener(this);
        mMap.setOnMapClickListener(this);
        mMap.setMyLocationEnabled(true);
    }

    @Override
    public void onMapClick(LatLng latLng) {
        Log.d(TAG, "onMapClick: " + latLng);
    }
}
