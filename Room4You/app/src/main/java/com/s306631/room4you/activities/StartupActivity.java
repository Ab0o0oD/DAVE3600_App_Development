package com.s306631.room4you.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.s306631.room4you.R;
import com.s306631.room4you.util.ServiceChecker;

public class StartupActivity extends AppCompatActivity {

    private static final String TAG = "StartupActivity";
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    public static final String INTERNET = Manifest.permission.INTERNET;

    private boolean permissionsGranted = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (isPermissionsGranted()) {
            Intent intent = new Intent(this, MapActivity.class);
            startActivity(intent);
            finish();
        }
    }

    /* ---------- Permission and Service Checks ---------- */

    public boolean isPermissionsGranted() {
        Log.d(TAG, "isPermissionsGranted: CALLED");
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
                    Toast.makeText(this, "Sorry, " + getResources().getString(R.string.app_name) + " needs your location permission to function", Toast.LENGTH_LONG).show();
                    finish();
                }

                Log.d(TAG, "onRequestPermissionsResult: permission granted");
                permissionsGranted = true;
                recreate();
            }
        }
    }
}
