package com.fredrikpedersen.eatingwithfriends_gradedassignment.services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.fredrikpedersen.eatingwithfriends_gradedassignment.util.StaticHolder;

import java.util.Calendar;
import java.util.Objects;

public class PeriodicService extends Service {

    private static final String TAG = "PeriodicService";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flag, int startId) {
        final int[] userPrefTime = splitTime(getSharedPreferences(StaticHolder.PREFERENCE, MODE_PRIVATE).getString(StaticHolder.SMS_TIMING_PREF, "12:00"));

        java.util.Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, userPrefTime[0]);
        cal.set(Calendar.MINUTE, userPrefTime[1]);
        Log.d(TAG, "onStartCommand: TIME FROM USER " + cal.getTimeInMillis());


        Intent serviceIntent = new Intent(this, ReminderService.class);
        PendingIntent pIntent = PendingIntent.getService(this, 0, serviceIntent, 0);

        AlarmManager alarm = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Objects.requireNonNull(alarm).setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pIntent);

        return super.onStartCommand(intent, flag, startId);
    }

    private int[] splitTime(String time) {
        String[] userSplit = time.split(":");
        return new int[] {Integer.parseInt(userSplit[0]), Integer.parseInt(userSplit[1])};
    }
}
