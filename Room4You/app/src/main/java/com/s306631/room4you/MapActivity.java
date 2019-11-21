package com.s306631.room4you;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.s306631.room4you.models.Building;
import com.s306631.room4you.models.Room;
import com.s306631.room4you.repository.BuildingRepository;
import com.s306631.room4you.util.ServiceChecker;
import com.s306631.room4you.viewModels.BuildingViewModel;
import com.s306631.room4you.viewModels.RoomViewModel;

import java.util.List;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    public static final String INTERNET = Manifest.permission.INTERNET;
    private static final String TAG = "MapActivity";


    private RoomViewModel roomViewModel;
    private BuildingViewModel buildingViewModel;

    private List<Room> roomList;
    private List<Building> buildingList;

    private GoogleMap mMap;
    private boolean permissionsGranted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        buildingViewModel = ViewModelProviders.of(this).get(BuildingViewModel.class);
        roomViewModel = ViewModelProviders.of(this).get(RoomViewModel.class);

        buildingList = buildingViewModel.getAllBuildingsAsList();
        roomList = roomViewModel.getAllRoomsAsList();

        for (Building building : buildingList) {
            Log.d(TAG, "onCreate: " + building);
        }

        if (isPermissionsGranted()) {
            initializeMap();
        } else {
            Toast.makeText(this, "Can't find your location without permissions, sorry", Toast.LENGTH_LONG).show();
        }
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
        if (permissionsGranted) {
            mMap = googleMap;

            LatLng osloMet = new LatLng(59.919390, 10.735208);
            moveMap(osloMet, getResources().getInteger(R.integer.DEFAULT_ZOOM), "OsloMet P35");
        }
    }

    /* ---------- Permission and Service Checks ---------- */

    public boolean isPermissionsGranted() {
        ServiceChecker serviceChecker = new ServiceChecker(this);
        getLocationPermission();
        return serviceChecker.isServiceOk() && permissionsGranted;
    }


    private void getLocationPermission() {
        Log.d(TAG, "getLocationPermission: getting location permissions");
        String[] permissions = {FINE_LOCATION, INTERNET};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this.getApplicationContext(), INTERNET) == PackageManager.PERMISSION_GRANTED) {
            permissionsGranted = true;
        } else {
            ActivityCompat.requestPermissions(this, permissions, getResources().getInteger(R.integer.LOCATION_PERMISSION_REQUEST_CODE));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: called.");
        permissionsGranted = false;

        if (requestCode == getResources().getInteger(R.integer.LOCATION_PERMISSION_REQUEST_CODE) && grantResults.length > 0) {
            for (int grantResult : grantResults) {
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "onRequestPermissionsResult: PERMISSIONS GRANTED" + permissionsGranted);
                    Log.d(TAG, "onRequestPermissionsResult: permission failed");
                    return;
                }

                Log.d(TAG, "onRequestPermissionsResult: permission granted");
                permissionsGranted = true;
                recreate();
            }
        }
    }
}