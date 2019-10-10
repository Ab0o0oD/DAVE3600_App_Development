package com.fredrikpedersen.top10downloadedapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class FeedAdapter extends ArrayAdapter {

    private static final String TAG = "FeedAdapter";
    private final int layoutResource;
    private final LayoutInflater layoutInflater;
    private final List<FeedEntry> applications;

    public FeedAdapter(@NonNull Context context, int resource, List<FeedEntry> applications) {
        super(context, resource);
        this.layoutInflater = LayoutInflater.from(context);
        this.layoutResource = resource;
        this.applications = applications;
    }

    @Override
    public int getCount() {
        return applications.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = layoutInflater.inflate(layoutResource, parent, false);
        }

        TextView tvName = convertView.findViewById(R.id.tvName);
        TextView tvArtist = convertView.findViewById(R.id.tvArtist);
        TextView tvSummary = convertView.findViewById(R.id.tvSummary);

        FeedEntry currentApp = applications.get(position);

        tvName.setText(currentApp.getName());
        tvArtist.setText(currentApp.getArtist());
        tvSummary.setText(currentApp.getSummary());

        return convertView;
    }
}
