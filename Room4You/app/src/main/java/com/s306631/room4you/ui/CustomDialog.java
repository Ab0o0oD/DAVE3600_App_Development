package com.s306631.room4you.ui;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.s306631.room4you.R;

public class CustomDialog extends Dialog implements View.OnClickListener {
    private static final String TAG = "CustomDialog";

    private TextView content;
    private Button confirm, cancel;
    private OnDialogOptionSelectedListener callback;

    public void setOnDialogOptionSelectedListener(OnDialogOptionSelectedListener callback) {
        this.callback = callback;
    }

    public CustomDialog(Activity context, String message) {
        super(context);
        setContentView(R.layout.dialog_booking);

        initializeViews();
        content.setText(message);
    }

    private void initializeViews() {
        content = findViewById(R.id.text_view_dialog_message);

        confirm = findViewById(R.id.button_dialog_confirm);
        confirm.setOnClickListener(this);
        cancel = findViewById(R.id.button_dialog_dissmiss);
        cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == confirm) {
            callback.onDialogOptionSelected();
            dismiss();
        } else {
            dismiss();
        }
    }
}
