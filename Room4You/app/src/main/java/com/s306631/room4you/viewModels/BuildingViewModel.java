package com.s306631.room4you.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.s306631.room4you.activities.AddDeleteBuildingActivity;
import com.s306631.room4you.models.Building;
import com.s306631.room4you.repository.BuildingRepository;

import java.util.List;

public class BuildingViewModel extends AndroidViewModel {

    private static final String TAG = "BuildingViewModel";

    private BuildingRepository buildingRepository;

    public BuildingViewModel(@NonNull Application application) {
        super(application);
        buildingRepository = new BuildingRepository();
    }

    public List<Building> getAllBuildingsAsList() {
        return buildingRepository.getBuildingsFromWebService();
    }

    public void postBuilding(AddDeleteBuildingActivity context, Building building) {
        buildingRepository.postBuilding(context, building);
    }
}
