package com.fredrikpedersen.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
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
        getJSON task = new getJSON(this);
        task.execute(new String[]{"http://student.cs.hioa.no/~s306631/jsonout.php"});
    }

    private static class getJSON extends AsyncTask<String, Void, String> {

        //NB! Remember to make inner AsyncTask classes static and hold a reference to the activity!
        //This is not covered in the course, and will not be included in any of Torun's examples!
        private WeakReference<MainActivity> activityReference;

        getJSON(MainActivity context) {
            activityReference = new WeakReference<>(context);
        }

        @Override
        protected String doInBackground(String... urls) {
            String retur = "";
            String s;
            String output = "";

            for (String url : urls) {
                try {
                    URL urlen = new URL(urls[0]);
                    HttpURLConnection conn = (HttpURLConnection) urlen.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setRequestProperty("Accept", "application/json");

                    if (conn.getResponseCode() != 200) {
                        throw new RuntimeException("Failed : HTTP error code : "
                                + conn.getResponseCode());
                    }

                    BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
                    System.out.println("Output from Server .... \n");

                    while ((s = br.readLine()) != null) {
                        output = output + s;
                    }

                    conn.disconnect();

                    try {
                        JSONArray mat = new JSONArray(output);
                        for (int i = 0; i < mat.length(); i++) {
                            JSONObject jsonobject = mat.getJSONObject(i);
                            String name = jsonobject.getString("name");
                            retur = retur + name + "\n";
                        }

                        return retur;

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    return retur;

                } catch (Exception e) {
                    return "Noe gikk feil";
                }
            }
            return retur;
        }

        @Override
        protected void onPostExecute(String ss) {
            MainActivity activity = activityReference.get();
            activity.textView.setText(ss);
        }
    }
}
