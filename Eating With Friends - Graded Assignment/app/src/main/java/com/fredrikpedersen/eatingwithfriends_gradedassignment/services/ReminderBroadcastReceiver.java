package com.fredrikpedersen.eatingwithfriends_gradedassignment.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ReminderBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent serviceIntent = new Intent(context, PeriodicService.class);
        context.startService(serviceIntent);
    }
}
