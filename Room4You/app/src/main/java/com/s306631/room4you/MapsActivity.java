package com.s306631.room4you;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    public static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String TAG = "MapsActivity";

    private GoogleMap mMap;
    private boolean permissionsGranted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        if (isPermissionsGranted()) {
            initializeMap();
        } else {
            Toast.makeText(this, "Can't find your location without permissions, sorry", Toast.LENGTH_LONG).show();
        }
    }

    /* ---------- Map Methods ---------- */

    public void initializeMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(MapsActivity.this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (permissionsGranted) {
            mMap = googleMap;

            LatLng osloMet = new LatLng(59.919390, 10.735208);
            mMap.addMarker(new MarkerOptions().position(osloMet).title("Marker at OsloMet P35"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(osloMet));
        }
    }

    /* ---------- Permission and Service Checks ---------- */

    public boolean isPermissionsGranted() {
        getLocationPermission();
        return isServiceOk() && permissionsGranted;
    }

    public boolean isServiceOk() {
        Log.d(TAG, "isServiceOk: Checking Gooogle Services Version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MapsActivity.this);

        if (available == ConnectionResult.SUCCESS) {
            Log.d(TAG, "isServiceOk: Google Play Services is working!");
            return true;

        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            Log.d(TAG, "isServiceOk: A fixable error occured");
            Log.d(TAG, "isServiceOk: ");

            //Displays a dialog prompting the user to install Google Play services
            GoogleApiAvailability.getInstance().getErrorDialog(MapsActivity.this, available, getResources().getInteger(R.integer.ERROR_DIALOG_REQUEST)).show();
        } else {
            Toast.makeText(this, "You can't access map functionality", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    private void getLocationPermission() {
        Log.d(TAG, "getLocationPermission: getting location permissions");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
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