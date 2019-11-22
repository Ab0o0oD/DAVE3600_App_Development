package com.s306631.room4you.ui;

import android.app.Activity;
import android.app.Dialog;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.s306631.room4you.R;

public class CustomDialog extends Dialog implements View.OnClickListener {
    private static final String TAG = "CustomDialog";

    TextView content, confirm, cancel;

    public CustomDialog(Activity context, String message) {
        super(context);
        setContentView(R.layout.dialog_booking);

        initializeViews();
        content.setText(message);
    }

    private void initializeViews() {
        content = findViewById(R.id.text_view_dialog_message);

        confirm = findViewById(R.id.text_view_dialog_confirm);
        confirm.setOnClickListener(this);
        cancel = findViewById(R.id.text_view_dialog_dissmiss);
        cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == confirm) {
            Log.d(TAG, "onClick: Start a new activity");
        } else {
            dismiss();
        }
    }
}