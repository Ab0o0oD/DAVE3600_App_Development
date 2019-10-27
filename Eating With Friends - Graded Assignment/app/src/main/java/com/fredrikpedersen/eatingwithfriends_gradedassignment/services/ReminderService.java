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
import com.fredrikpedersen.eatingwithfriends_gradedassignment.database.models.Friend;
import com.fredrikpedersen.eatingwithfriends_gradedassignment.repository.BookingRepository;
import com.fredrikpedersen.eatingwithfriends_gradedassignment.util.StaticHolder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

//TODO THIS CLASS IS A BIT OF A MESS, CLEAN UP LATER!
public class ReminderService extends Service {

    private static final String TAG = "ReminderService";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        boolean smsFunctionality = getSharedPreferences(StaticHolder.PREFERENCE, MODE_PRIVATE).getBoolean(StaticHolder.SMS_FUNCTIONALITY_PREF, true);
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

        BookingRepository bookingRepository = new BookingRepository(getApplication());
        List<Booking> allBookings = bookingRepository.getAllBookingsAsList();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        //Iterates through all bookings, checks if there are any bookings today and sends notification to user and sms to friends if there are.
        for (Booking booking : allBookings) {
            if (booking.getDate().equals(currentDate)) {
                buildNotification(pIntent, notificationManager);

                if (smsFunctionality) {
                    if (booking.getFriends() != null) {
                        for (Friend friend : booking.getFriends()) {
                            Log.d(TAG, "onStartCommand: SENDING SMS TO " + friend.getFirstName());
                            sendSms(friend.getPhoneNumber());
                        }
                    }
                }
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void buildNotification(PendingIntent pIntent, NotificationManager notificationManager) {
        String contentText = "Remember your restaurant booking(s) today!";
        Notification notification = new NotificationCompat.Builder(this, "channel_id")
                .setContentTitle("You have a booking today!")
                .setContentText(contentText)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pIntent)
                .build();

        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        Objects.requireNonNull(notificationManager).notify(0, notification);
    }

    private void sendSms(String phonenumber) {
        String message  = getSharedPreferences(StaticHolder.PREFERENCE, MODE_PRIVATE).getString(StaticHolder.SMS_MESSAGE_PREF, getResources().getString(R.string.default_message));
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phonenumber, null, message, null, null);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Eating With Friends does not have permission to send SMS. Turn off SMS service or give permission", Toast.LENGTH_SHORT).show();
        }
    }
}
