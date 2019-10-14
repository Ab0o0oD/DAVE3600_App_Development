package com.fredrikpedersen.mvvmarchitectureexample.Database.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "note_table") //Default tablename will be the classname unless set here
public class Note {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String description;
    //Can use @ColumnInfo(name="whatever") to set custom column names
    private int priority;

    public Note(String title, String description, int priority) {
        this.title = title;
        this.description = description;
        this.priority = priority;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getPriority() {
        return priority;
    }
}
