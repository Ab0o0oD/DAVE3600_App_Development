package com.s306631.room4you;

import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.s306631.room4you.models.Booking;
import com.s306631.room4you.models.Building;
import com.s306631.room4you.models.Room;
import com.s306631.room4you.viewModels.BookingViewModel;
import com.s306631.room4you.viewModels.BuildingViewModel;
import com.s306631.room4you.viewModels.RoomViewModel;

import java.util.List;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final String TAG = "MapActivity";


    private RoomViewModel roomViewModel;
    private BuildingViewModel buildingViewModel;
    private BookingViewModel bookingViewModel;

    private List<Room> roomList;
    private List<Building> buildingList;
    private List<Booking> bookingList;

    private GoogleMap mMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        buildingViewModel = ViewModelProviders.of(this).get(BuildingViewModel.class);
        roomViewModel = ViewModelProviders.of(this).get(RoomViewModel.class);
        bookingViewModel = ViewModelProviders.of(this).get(BookingViewModel.class);

        buildingList = buildingViewModel.getAllBuildingsAsList();
        roomList = roomViewModel.getAllRoomsAsList();
        bookingList = bookingViewModel.getAllBookingsAsList();

        initializeMap();
    }

    /* ---------- Map Methods ---------- */

    public void moveMap(LatLng coordinates, float zoom, String title) {
        Log.d(TAG, "moveMap: moving the map to: lat: " + coordinates.latitude + ", lng: " + coordinates.longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinates, zoom));
        mMap.addMarker(new MarkerOptions().position(coordinates).title(title));
    }

    public void initializeMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(MapActivity.this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Building primaryBuilding = buildingList.get(0);

        moveMap(primaryBuilding.getCoordinates(), getResources().getInteger(R.integer.DEFAULT_ZOOM), primaryBuilding.getBuildingName());
    }
}
