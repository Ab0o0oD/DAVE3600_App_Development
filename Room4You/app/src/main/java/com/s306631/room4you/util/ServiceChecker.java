package com.s306631.room4you.util;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.s306631.room4you.R;

public class ServiceChecker {

    private static final String TAG = "ServiceChecker";

    private Activity activity;

    public ServiceChecker(Activity context) {
        this.activity = context;
    }

    public boolean isServiceOk() {
        Log.d(TAG, "isServiceOk: Checking Gooogle Services Version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(activity);

        if (available == ConnectionResult.SUCCESS) {
            Log.d(TAG, "isServiceOk: Google Play Services is working!");
            return true;

        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            Log.d(TAG, "isServiceOk: A fixable error occured");
            Log.d(TAG, "isServiceOk: ");

            //Displays a dialog prompting the user to install Google Play services
            GoogleApiAvailability.getInstance().getErrorDialog(activity, available, activity.getResources().getInteger(R.integer.ERROR_DIALOG_REQUEST)).show();
        } else {
            Toast.makeText(activity, "You can't access map functionality", Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}
