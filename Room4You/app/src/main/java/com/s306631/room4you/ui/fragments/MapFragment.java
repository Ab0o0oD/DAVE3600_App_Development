package com.s306631.room4you.ui.fragments;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.s306631.room4you.R;
import com.s306631.room4you.util.CoordinatesParser;

public class MapFragment extends Fragment implements GoogleMap.OnMapClickListener  {

    private static final String TAG = "MapFragment";

    private MapView mapView;
    private GoogleMap googleMap;

    private LatLng chosenCoordinates;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);

        mapView = rootView.findViewById(R.id.mapFragment);
        mapView.onCreate(savedInstanceState);

        mapView.onResume();

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (NullPointerException e) {
            Log.d(TAG, "onCreateView: NullpointerException" + e.getMessage());
            Toast.makeText(getActivity(), "Something went wrong, can't load map. Restart the app and try again", Toast.LENGTH_SHORT).show();
        }

        mapView.getMapAsync(mMap -> {
            googleMap = mMap;
            googleMap.setMyLocationEnabled(true);
            googleMap.getUiSettings().setMyLocationButtonEnabled(false);
            googleMap.setOnMapClickListener(this);
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(59.919390, 10.735208), getResources().getInteger(R.integer.DEFAULT_ZOOM)));

        });

        return rootView;
    }

    @Override
    public void onMapClick(LatLng latLng) {
        Log.d(TAG, "onMapClick: MAP IS CLICKED AT COORDINATES: " + latLng.toString());
        googleMap.addMarker(new MarkerOptions().position(latLng));
        chosenCoordinates = latLng;

        getActivity().getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
                .edit()
                .putString("Chosen Coordinates", CoordinatesParser.stringFromCoordinates(chosenCoordinates))
                .apply();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }
}
