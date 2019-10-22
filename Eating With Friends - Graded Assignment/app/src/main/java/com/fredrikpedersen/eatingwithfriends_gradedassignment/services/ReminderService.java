package com.fredrikpedersen.eatingwithfriends_gradedassignment.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.fredrikpedersen.eatingwithfriends_gradedassignment.R;
import com.fredrikpedersen.eatingwithfriends_gradedassignment.activities.MainActivity;
import com.fredrikpedersen.eatingwithfriends_gradedassignment.database.models.Booking;
import com.fredrikpedersen.eatingwithfriends_gradedassignment.repository.BookingRepository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class ReminderService extends Service {

    private static final String TAG = "ReminderService";
    private BookingRepository bookingRepository;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        bookingRepository = new BookingRepository(getApplication());

        NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, notificationIntent,0);

        Notification notification = new NotificationCompat.Builder(this, "channel_id")
                .setContentTitle("You have a booking today!")
                .setContentText("Sample text")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pIntent)
                .build();

        checkIfBookingToday();

        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        Objects.requireNonNull(notificationManager).notify(0, notification);

        return super.onStartCommand(intent, flags, startId);
    }


    private void sendSms(String phonenumber){
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phonenumber, null, "Løk", null, null);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Eating With Friends does not have permission to send SMS. Turn off SMS service or give permission", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkIfBookingToday() {
        List<Booking> bookings = bookingRepository.getAllBookingsAsList();
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        Log.d(TAG, "checkIfBookingToday: " + currentDate);

        for (Booking booking : bookings) {
            Log.d(TAG, "checkIfBookingToday: " + booking.getDate());
        }
    }

}
