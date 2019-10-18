package com.fredrikpedersen.eatingwithfriends_gradedassignment.ui.notifications;

import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.fredrikpedersen.eatingwithfriends_gradedassignment.R;

public class NotificationsFragment extends Fragment {

    private Button buttonSendSms;

    public NotificationsFragment() {}

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);

        initializeViews(view);
        setOnlicks();

        return view;
    }

    private void initializeViews(View view) {
        buttonSendSms = view.findViewById(R.id.button_send_sms);
    }

    private void setOnlicks() {
        buttonSendSms.setOnClickListener(v -> sendSms());
    }

    private void sendSms() {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage("+15555215556", null, "Hello There", null, null);
            Toast.makeText(getActivity(), "SMS Send Successfully", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Sending SMS Failed", Toast.LENGTH_SHORT).show();
        }
    }
}