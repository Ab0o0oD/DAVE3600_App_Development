package com.fredrikpedersen.contentprovidertest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public final static String PROVIDER="com.fredrikpedersen.contentproviderbok";
    public static final Uri CONTENT_URI= Uri.parse("content://"+ PROVIDER + "/bok");
    public static final String TITTEL="Tittel";
    public static final String ID="_id";
    EditText tittel;
    TextView visbok;
    TextView id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        visbok = findViewById(R.id.vis);
        tittel=findViewById(R.id.tittel);
        id = findViewById(R.id.edit_text_bok_id);
        visalle();
    }

    public void leggtil(View v){
        ContentValues values = new ContentValues();
        String inn=tittel.getText().toString();
        values.put(TITTEL, inn);
        Uri uri = getContentResolver().insert( CONTENT_URI, values);
        tittel.setText("");
        visalle();
    }

    public void visalle() {
        String tekst;
        tekst = "";
        Cursor cur = getContentResolver().query(CONTENT_URI, null, null, null, null);
        if (cur.moveToFirst()) {
            do {
                tekst = tekst + (cur.getString(0)) + " " + (cur.getString(1)) + "\r\n";
            }
            while (cur.moveToNext());
            cur.close();
            visbok.setText(tekst);
        }
    }

    public void slett(View v) {
        String delete = CONTENT_URI + "/" + String.valueOf(id.getText());
        getContentResolver().delete(Uri.parse(delete), null, null);
        visalle();
    }

    public void oppdater(View v) {
        String update = CONTENT_URI + "/" + String.valueOf(id.getText());
        ContentValues cv = new ContentValues();
        cv.put("Tittel", String.valueOf(tittel.getText()));
        getContentResolver().update(Uri.parse(update), cv,null, null);
        visalle();
    }
}
