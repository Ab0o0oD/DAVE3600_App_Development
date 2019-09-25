package com.fredrikpedersen.fragmenttesting;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.fragment.app.Fragment;

public class VisFilFragment extends Fragment {
    private String currentURL;
    public void init(String url) {
        currentURL = url;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.visfil_layout, container, false);
        if (currentURL != null) {
            WebView wv = (WebView) v.findViewById(R.id.webside);
            wv.loadUrl("file:///android_asset/" +currentURL);
        }
        return v;
    }
    public void updateUrl(String url) {
        currentURL = url;
        WebView wv = (WebView) getView().findViewById(R.id.webside);
        wv.loadUrl("file:///android_asset/" +currentURL);
    }
}
