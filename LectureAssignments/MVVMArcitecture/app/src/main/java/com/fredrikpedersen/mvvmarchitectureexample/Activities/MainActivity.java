package com.fredrikpedersen.mvvmarchitectureexample.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.fredrikpedersen.mvvmarchitectureexample.Database.Models.Note;
import com.fredrikpedersen.mvvmarchitectureexample.R;
import com.fredrikpedersen.mvvmarchitectureexample.Views.Models.NoteViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private NoteViewModel noteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        noteViewModel.getAllNotes().observe(this, notes -> {
            //Update RecyclerView
            Toast.makeText(MainActivity.this, "OnChanged", Toast.LENGTH_SHORT).show();

        });
    }
}
