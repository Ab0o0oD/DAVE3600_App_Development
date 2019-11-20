package com.fredrikpedersen.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.jasontekst);

        GetJSON task = new GetJSON(this);
        task.execute("http://student.cs.hioa.no/~s306631/jsonout.php", "Hello there");
    }

    private static class GetJSON extends AsyncTask<String, Void, String> {

        //NB! Remember to make inner AsyncTask classes static and hold a reference to the activity!
        //This is not covered in the course, and will not be included in any of Torun's examples!
        private WeakReference<MainActivity> activityReference;

        GetJSON(MainActivity context) {
            activityReference = new WeakReference<>(context);
        }

        @Override
        protected String doInBackground(String... urls) {
            StringBuilder retur = new StringBuilder();
            String s;
            StringBuilder output = new StringBuilder();

            for (int i = 0; i < urls.length; i++) {

                try {
                    URL url = new URL(urls[0]);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setRequestProperty("Accept", "application/json");

                    if (connection.getResponseCode() != 200) {
                        throw new RuntimeException("Failed : HTTP error code : " + connection.getResponseCode());
                    }

                    BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));

                    while ((s = br.readLine()) != null) {
                        output.append(s);
                    }

                    Log.d("TAG", "doInBackground: output= " + output);

                    connection.disconnect();

                    try {

                        JSONArray rooms = new JSONArray(output.toString());

                        for (int j = 0; j < rooms.length(); j++) {
                            JSONObject jsonobject = rooms.getJSONObject(j);
                            String name = jsonobject.getString("Name");
                            retur.append(name).append("\n");
                        }

                        return retur.toString();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    return retur.toString();

                } catch (Exception e) {
                    return "Noe gikk feil";
                }
            }
            return retur.toString();
        }

        @Override
        protected void onPostExecute(String ss) {
            Log.d("TAG", "onPostExecute: " + ss);
            MainActivity activity = activityReference.get();
            activity.textView.setText(ss);
        }
    }
}
