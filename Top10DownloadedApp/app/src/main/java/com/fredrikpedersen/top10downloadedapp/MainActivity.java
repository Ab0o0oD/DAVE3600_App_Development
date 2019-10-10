package com.fredrikpedersen.top10downloadedapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ListView listApps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();

        Log.d(TAG, "onCreate: starting AsyncTask");
        DownloadData downloadData = new DownloadData(this);
        downloadData.execute("http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=200/xml"); //This calls the doInBackground method
        Log.d(TAG, "onCreate: done");
    }

    private void initializeViews() {
        listApps = findViewById(R.id.xmlListView);
    }

    //Inner static class for downloading data in a separate thread
    private static class DownloadData extends AsyncTask<String, Void, String> {

        private static final String TAG = "DownloadData";
        private WeakReference<MainActivity> activityReference;

        DownloadData (MainActivity context) {
            activityReference = new WeakReference<>(context);
        }

        @Override
        protected void onPostExecute(String s) { //Is called when the task is done
            super.onPostExecute(s);
            Log.d(TAG, "onPostExecute: parameter is " + s);

            MainActivity activity = activityReference.get();

            if (activity == null || activity.isFinishing()) {
                return;
            }

            ParseApplications parseApplications = new ParseApplications();
            parseApplications.parse(s);

            FeedAdapter feedAdapter = new FeedAdapter(activity, R.layout.list_record, parseApplications.getApplications());
            activity.listApps.setAdapter(feedAdapter);
        }

        /*Can take in several parameters, but it is usually more convenient to create several
        instances of the class to do several tasks at once */
        @Override
        protected String doInBackground(String... strings) {
            Log.d(TAG, "doInBackground: starts with " + strings[0]);
            String rssFeed = downloadXML(strings[0]);
            if (rssFeed == null) {
                Log.e(TAG, "doInBackground: Error downloading");
            }
            return rssFeed;
        }

        private String downloadXML(String urlPath) {
            StringBuilder xmlResult = new StringBuilder();

            try {
                URL url = new URL(urlPath);
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                int response = connection.getResponseCode();
                Log.d(TAG, "downloadXML: The Response code was " + response);

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                String line;
                while ((line = reader.readLine()) != null) {
                    xmlResult.append(line).append("\n");
                }
                reader.close();

                return xmlResult.toString();

            } catch (MalformedURLException e) {
                Log.e(TAG, "downloadXML: Invalid URL " + e.getMessage());
            } catch (IOException e) {
                Log.e(TAG, "downloadXML: IO Exception reading data: " + e.getMessage());
            } catch (SecurityException e) {
                Log.e(TAG, "downloadXML: Security Exception. " + e.getMessage());
            }

            return null;
        }
    }
}
