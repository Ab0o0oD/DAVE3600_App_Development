package com.fredrikpedersen.top10downloadedapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
        downloadUrl("http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=25/xml");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.feeds_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        String feedUrl;

        switch (id) {
            case R.id.menuFree:
                feedUrl = "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=25/xml";
                break;
            case R.id.menuPaid:
                feedUrl = "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/toppaidapplications/limit=25/xml";
                break;
            case R.id.menuSongs:
                feedUrl = "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topsongs/limit=25/xml";
                break;
            default:
                return super.onOptionsItemSelected(item); //Should always be included, is important when dealing with sub-menus
        }
        downloadUrl(feedUrl);
        return true;
    }

    private void downloadUrl(String feedUrl) {
        Log.d(TAG, "onCreate: starting AsyncTask");
        DownloadData downloadData = new DownloadData(this);
        downloadData.execute(feedUrl); //This calls the doInBackground method
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
