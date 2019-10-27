package com.fredrikpedersen.contentprovidertesting;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    public final Uri CONTENT_URI = Uri.parse("content://com.fredrikpedersen.eatingwithfriends_gradedassignment.contentprovider/restaurant_table");

    TextView textView;
    EditText textViewName;
    EditText textViewType;
    EditText textViewAddress;
    EditText textViewPhone;
    Button buttonGet;
    Button buttonAdd;

    ContentResolver contentResolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonGet = findViewById(R.id.button_get_restaurants);
        buttonAdd = findViewById(R.id.button_add_restaurant);
        textView = findViewById(R.id.text_view_restaurants);
        textViewName = findViewById(R.id.edit_text_restaurant_name);
        textViewType = findViewById(R.id.edit_text_restaurant_type);
        textViewAddress = findViewById(R.id.edit_text_restaurant_address);
        textViewPhone = findViewById(R.id.edit_text_restaurant_phone);
        contentResolver = getContentResolver();

        buttonGet.setOnClickListener(v -> getRestaurants());
        buttonAdd.setOnClickListener(view -> addRestaurant());
    }

    public void getRestaurants() {
        String [] projection = {"name", "address"};
        Cursor cursor = contentResolver.query(CONTENT_URI,projection,null,null,null);

        String restaurants = "";

        if(cursor.moveToFirst()) {
            do {
                restaurants = restaurants + "Name: " + (cursor.getString(1)) + " - Address: " + (cursor.getString(2)) + "\r\n";
            } while (cursor.moveToNext());
            cursor.close();
            textView.setText(restaurants);
        }
    }

    public void addRestaurant() {
        String name = textViewName.getText().toString();
        String type = textViewType.getText().toString();
        String address = textViewAddress.getText().toString();
        String phone = textViewPhone.getText().toString();

        ContentValues contentValues = new ContentValues();
        contentValues.put("restaurant_name", name);
        contentValues.put("type", type);
        contentValues.put("address", address);
        contentValues.put("phone_number",phone);

        contentResolver.insert(CONTENT_URI, contentValues);

        getRestaurants();
    }
}
