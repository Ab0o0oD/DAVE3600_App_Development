package com.fredrikpedersen.eatingwithfriends_gradedassignment.services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import java.util.Calendar;
import java.util.Objects;

public class PeriodicService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flag, int startId) {
        java.util.Calendar cal = Calendar.getInstance();
        Intent serviceIntent = new Intent(this, ReminderService.class);
        PendingIntent pIntent = PendingIntent.getService(this, 0, serviceIntent, 0);

        AlarmManager alarm = (AlarmManager)getSystemService(Context.ALARM_SERVICE);

        Objects.requireNonNull(alarm).setInexactRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 86400 * 1000, pIntent);

        return super.onStartCommand(intent, flag, startId);
    }
}
