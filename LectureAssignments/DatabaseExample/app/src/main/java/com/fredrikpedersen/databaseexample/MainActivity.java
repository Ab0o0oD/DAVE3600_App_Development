package com.fredrikpedersen.databaseexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText nameIn;
    private EditText phoneIn;
    private EditText idIn;
    private TextView print;
    private DBHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeVariables();
    }

    public void add(View view) {
        Contact contact = new Contact(nameIn.getText().toString(), phoneIn.getText().toString());
        db.addContact(contact);
        Log.d("Add: ", "Adding contacts");

    }

    public void showAll(View view) {
        String text = "";
        List<Contact> contacts = db.findAllContacs();

        for (Contact contact : contacts) {
            text = text + "Id: " + contact.get_ID() + ", Name: " + contact.getName() + ", Phone: " + contact.getPhoneNumber();
            Log.d("Name: ", text);
        }
        print.setText(text);
    }

    public void delete(View view) {
        Long contactId = (Long.parseLong(idIn.getText().toString()));
        db.deleteContact(contactId);
    }

    public void update(View view) {
        Contact contact = new Contact();
        contact.setName(nameIn.getText().toString());
        contact.setPhoneNumber(phoneIn.getText().toString());
        contact.set_ID(Long.parseLong(idIn.getText().toString()));
        db.updateContact(contact);
    }

    public void initializeVariables() {
        nameIn = findViewById(R.id.navn);
        phoneIn = findViewById(R.id.telefon);
        idIn = findViewById(R.id.min_id);
        print = findViewById(R.id.utskrift);
        db = new DBHandler(this);
    }
}
