package com.fredrikpedersen.eatingwithfriends_gradedassignment.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.fredrikpedersen.eatingwithfriends_gradedassignment.R;
import com.fredrikpedersen.eatingwithfriends_gradedassignment.database.models.Restaurant;
import com.fredrikpedersen.eatingwithfriends_gradedassignment.ui.restaurants.RestaurantViewModel;
import com.fredrikpedersen.eatingwithfriends_gradedassignment.util.StaticHolder;

import static com.fredrikpedersen.eatingwithfriends_gradedassignment.util.StaticHolder.EXTRA_ID;

public class AddEditRestaurantActivity extends AppCompatActivity {

    private static final String TAG = "AddEditRestActivity";

    private EditText editTextRestaurantName;
    private EditText editTextRestaurantAddress;
    private EditText editTextRestaurantPhone;
    private EditText editTextRestaurantType;
    private Button buttonSave;

    private RestaurantViewModel restaurantViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_restaurant);
        initializeViews();
        restaurantViewModel = ViewModelProviders.of(this).get(RestaurantViewModel.class);
        setOnClicks();
        initializeAddOrEdit();
    }

    /* ----- Save Friend ----- */
    private void checkFriendComplete() {
        String restaurantName = editTextRestaurantName.getText().toString();
        String restaurantAddress = editTextRestaurantAddress.getText().toString();
        String restaurantPhone = editTextRestaurantPhone.getText().toString();
        String restaurantType = editTextRestaurantType.getText().toString();

        if (restaurantName.equals("") || restaurantAddress.equals("") || restaurantPhone.equals("") || restaurantType.equals("")) {
            Toast.makeText(this, "Please give data in all required fields!", Toast.LENGTH_SHORT).show();
            return;
        }

        saveRestaurant(restaurantName, restaurantAddress, restaurantPhone, restaurantType);
    }

    private void saveRestaurant(String restaurantName, String restaurantAddress, String restaurantPhone, String restaurantType) {
        Restaurant newRestaurant = new Restaurant(restaurantName, restaurantAddress, restaurantPhone, restaurantType);

        if (getIntent().hasExtra(EXTRA_ID)) {
            newRestaurant.setId(getIntent().getIntExtra(EXTRA_ID, -1));
            restaurantViewModel.update(newRestaurant);
        } else {
            restaurantViewModel.insert(newRestaurant);
        }

        setResult(RESULT_OK);
        finish();
    }



    /* ----- Initializations ----- */
    private void initializeViews() {
        editTextRestaurantName = findViewById(R.id.edit_text_restaurant_name);
        editTextRestaurantAddress = findViewById(R.id.edit_text_restaurant_address);
        editTextRestaurantPhone = findViewById(R.id.edit_text_restaurant_phonenumber);
        editTextRestaurantType = findViewById(R.id.edit_text_restaurant_type);
        buttonSave = findViewById(R.id.button_save_restaurant);

    }

    private void setOnClicks() {
        buttonSave.setOnClickListener(v -> checkFriendComplete());
    }

    private void initializeAddOrEdit() {
        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Restaurant");
            editTextRestaurantName.setText(intent.getStringExtra(StaticHolder.EXTRA_RESTAURANT_NAME));
            editTextRestaurantAddress.setText(intent.getStringExtra(StaticHolder.EXTRA_RESTAURANT_ADDRESS));
            editTextRestaurantPhone.setText(intent.getStringExtra(StaticHolder.EXTRA_RESTAURANT_PHONE));
            editTextRestaurantType.setText(intent.getStringExtra(StaticHolder.EXTRA_RESTAURANT_TYPE));

        } else {
            setTitle("Add Restaurant");
        }
    }
}
