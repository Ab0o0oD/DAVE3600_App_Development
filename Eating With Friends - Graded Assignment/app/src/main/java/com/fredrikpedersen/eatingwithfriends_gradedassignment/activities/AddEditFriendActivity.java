package com.fredrikpedersen.eatingwithfriends_gradedassignment.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.fredrikpedersen.eatingwithfriends_gradedassignment.R;
import com.fredrikpedersen.eatingwithfriends_gradedassignment.database.models.Friend;
import com.fredrikpedersen.eatingwithfriends_gradedassignment.ui.friends.FriendViewModel;

import static com.fredrikpedersen.eatingwithfriends_gradedassignment.activities.MainActivity.EXTRA_ID;

public class AddEditFriendActivity extends AppCompatActivity {



    private static final String TAG = "AddEditFriendActivity";
    private EditText editTextFriendFirstName;
    private EditText editTextFriendLastName;
    private EditText editTextFriendPhoneNumber;
    private Button buttonSave;

    FriendViewModel friendViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_friend);
        initializeViews();
        friendViewModel = ViewModelProviders.of(this).get(FriendViewModel.class);
        setOnClicks();
        initializeAddOrEdit();
    }

    /* ----- Save Friend ----- */
    private void checkFriendComplete() {
        String firstName = editTextFriendFirstName.getText().toString();
        String lastName = editTextFriendLastName.getText().toString();
        String phoneNumber = editTextFriendPhoneNumber.getText().toString();

        if (firstName.equals("") || lastName.equals("") || phoneNumber.equals("")) {
            Toast.makeText(this, "Please give data in all required fields!", Toast.LENGTH_SHORT).show();
            return;
        }

        saveFriend(firstName, lastName, phoneNumber);
    }

    private void saveFriend(String firstName, String lastName, String phoneNumber) {
        Friend newFried = new Friend(firstName, lastName, phoneNumber);

        if (getIntent().hasExtra(EXTRA_ID)) {
            newFried.setId(getIntent().getIntExtra(EXTRA_ID, -1));
            friendViewModel.update(newFried);
        } else {
            friendViewModel.insert(newFried);
        }

        setResult(RESULT_OK);
        finish();
    }



    /* ----- Initializations ----- */
    private void initializeViews() {
        editTextFriendFirstName = findViewById(R.id.edit_text_friend_first_name);
        editTextFriendLastName = findViewById(R.id.edit_friend_last_name);
        editTextFriendPhoneNumber = findViewById(R.id.edit_text_friend_phone);
        buttonSave = findViewById(R.id.button_save_friend);

    }

    private void setOnClicks() {
            buttonSave.setOnClickListener(v -> checkFriendComplete());
    }

    private void initializeAddOrEdit() {
        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Friend");
            editTextFriendFirstName.setText(intent.getStringExtra(MainActivity.EXTRA_FIRST_NAME));
            editTextFriendLastName.setText(intent.getStringExtra(MainActivity.EXTRA_LAST_NAME));
            editTextFriendPhoneNumber.setText(intent.getStringExtra(MainActivity.EXTRA_PHONENUMBER));
        } else {
            setTitle("Add Friend");
        }
    }
}
