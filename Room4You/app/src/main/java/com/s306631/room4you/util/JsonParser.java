package com.s306631.room4you.util;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class JsonParser {

    private static final String TAG = "JsonParser";

    public static JSONArray createJsonArray(String webservice) {

        try {
            return new JSONArray(getJson(webservice));
        } catch (JSONException e) {
            Log.d(TAG, "createJsonArray: JSONException: " + e.getMessage());
        }
        return null;

    }

    private static String getJson(String webservice) {
        String jsonLine;
        StringBuilder jsonOutput = new StringBuilder();

        try {
            URL url = new URL(webservice);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");

            if (connection.getResponseCode() != 200) {
                Log.d(TAG, "doInBackground: RUNTIME EXCEPTION");
                throw new RuntimeException("Failed : HTTP error code : " + connection.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));

            while ((jsonLine = br.readLine()) != null) {
                jsonOutput.append(jsonLine);
            }

            connection.disconnect();

            return jsonOutput.toString();

        } catch (MalformedURLException e) {
            Log.d(TAG, "getJson: MalformedURLException: " + e.getMessage());
        } catch (ProtocolException e) {
            Log.d(TAG, "getJson: ProtocolException: " + e.getMessage());
        } catch (IOException e) {
            Log.d(TAG, "getJson: IOException: " + e.getMessage());
        }

        return "";

        }
    }

