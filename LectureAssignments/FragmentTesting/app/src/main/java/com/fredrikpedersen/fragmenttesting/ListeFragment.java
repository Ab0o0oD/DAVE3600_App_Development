package com.fredrikpedersen.fragmenttesting;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Collections;

public class ListeFragment extends Fragment {
    private static ArrayAdapter<String> adapter;
    private static UrlEndret listener;

    public interface UrlEndret {
        void linkEndret(String link);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity;
        activity = (Activity) context;
        try {
            listener = (UrlEndret) activity;
            System.out.println("satt lytter");
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + "må implementere UrlEndret");
        }
    }

    public ListeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.liste_layout, container, false);
        ListView lv = v.findViewById(R.id.liste);

        String[] values = new String[]{"minfil.txt","feil.html"};
        final ArrayList<String> list = new ArrayList<>();
        Collections.addAll(list, values);

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity().getBaseContext(), android.R.layout.simple_list_item_1, list);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String data = adapter.getItem(i);
                listener.linkEndret(data);
            }
        });
        return v;
    }
} //Slutt på ListeFragment
