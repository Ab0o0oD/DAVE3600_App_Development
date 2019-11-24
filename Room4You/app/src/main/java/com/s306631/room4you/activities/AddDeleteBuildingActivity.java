package com.s306631.room4you.activities;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

import com.google.android.gms.maps.model.LatLng;
import com.s306631.room4you.R;
import com.s306631.room4you.models.Building;
import com.s306631.room4you.ui.fragments.MapFragment;
import com.s306631.room4you.viewModels.BuildingViewModel;

import java.util.List;

public class AddDeleteBuildingActivity extends AppCompatActivity {

    private static final String TAG = "AddDeleteBuildingActivi";
    private RelativeLayout deleteLayout, mapLayout;
    private ImageView imageViewCoordinates;
    private EditText editTextBuildingName, editTextFloors;
    private TextView textViewCoordinates;
    private Button buttonAddBuilding, buttonDeleteBuilding, buttonConfirmCoordinates, buttonHideMap;
    private Spinner spinnerAllBuildings;

    private BuildingViewModel buildingViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_delete_building);

        buildingViewModel = ViewModelProviders.of(this).get(BuildingViewModel.class);

        initializeViews();
        fillBuildingSpinner();
    }

    private void registerBuilding() {
        String name = getBuildingNameFromView();
        int floors = getBuildingFloorsFromView();
        String coordinates = getCoordinatesFromView();

        if (name == null || floors == 0 || coordinates == null) {
            Log.d(TAG, "registerBuilding: Something went wrong!");
            return;
        }

        Building building = new Building(name, floors, coordinates);

        buildingViewModel.postBuilding(this, building);
    }

    private String getBuildingNameFromView() {
        String name = editTextBuildingName.getText().toString();

        if (name.length() == 0) {
            editTextBuildingName.setError("The building needs a name!");
            return null;
        } else if (name.length() > 30) {
            editTextBuildingName.setError("Building name can't be longer than 30 character!");
            return null;
        }

        return name;
    }

    /* --------- Add Building --------- */

    private int getBuildingFloorsFromView() {
        String floorsAsString = editTextFloors.getText().toString();
        try {
            int floors = Integer.parseInt(floorsAsString);

            if (floors <= 0 || floors >= 100) {
                editTextFloors.setError("Please submit an integer between 0 and 100");
                return 0;
            }

            return floors;
        } catch (NumberFormatException e) {
            editTextFloors.setError("Please submit an integer between 0 and 100");
            return 0;
        }
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

    private void openMap() {
        Log.d(TAG, "openMap: IS CALLED");
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.layout_map, new MapFragment());
        ft.commit();

        deleteLayout.setVisibility(RelativeLayout.GONE);
        mapLayout.setVisibility(RelativeLayout.VISIBLE);
        buttonAddBuilding.setVisibility(View.INVISIBLE);
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
        mapLayout.setVisibility(RelativeLayout.GONE);
        deleteLayout.setVisibility(RelativeLayout.VISIBLE);
        buttonAddBuilding.setVisibility(View.VISIBLE);
        buttonConfirmCoordinates.setVisibility(View.INVISIBLE);
        buttonHideMap.setVisibility(View.INVISIBLE);
        textViewCoordinates.setClickable(true);
        imageViewCoordinates.setClickable(true);
    }

    /* --------- Delete Building ---------- */

    private void fillBuildingSpinner() {
        List<Building> buildingList = buildingViewModel.getAllBuildingsAsList();
        ArrayAdapter<Building> buildingSpinnerAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, buildingList);
        buildingSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAllBuildings.setAdapter(buildingSpinnerAdapter);
    }

    private void initializeViews() {
        deleteLayout = findViewById(R.id.layout_delete);
        mapLayout = findViewById(R.id.layout_map);
        mapLayout.setVisibility(RelativeLayout.GONE);

        editTextBuildingName = findViewById(R.id.edit_text_building_name);
        editTextFloors = findViewById(R.id.edit_text_floors);

        imageViewCoordinates = findViewById(R.id.image_view_coordinates);
        imageViewCoordinates.setOnClickListener(v -> openMap());

        textViewCoordinates = findViewById(R.id.text_view_coordinates);
        textViewCoordinates.setOnClickListener(v -> openMap());

        buttonAddBuilding = findViewById(R.id.button_add_building);

        buttonAddBuilding.setOnClickListener(v -> registerBuilding());

        buttonDeleteBuilding = findViewById(R.id.button_delete_building);
        spinnerAllBuildings = findViewById(R.id.spinner_deleteable_buildings);

        buttonConfirmCoordinates = findViewById(R.id.button_confirm_coordinates);
        buttonConfirmCoordinates.setOnClickListener(v -> confirmCoordinates());
        buttonConfirmCoordinates.setVisibility(View.INVISIBLE);

        buttonHideMap = findViewById(R.id.button_hide_map);
        buttonHideMap.setOnClickListener(v -> removeMap());
        buttonHideMap.setVisibility(View.INVISIBLE);
    }

}
