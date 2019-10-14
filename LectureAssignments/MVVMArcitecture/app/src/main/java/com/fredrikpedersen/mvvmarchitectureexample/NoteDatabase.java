package com.fredrikpedersen.mvvmarchitectureexample;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

//Can pass several entities to the database by passing an array like this: @Database(entities = {Note.class, Ex.Class})
@Database(entities = Note.class, version = 1)
public abstract class NoteDatabase extends RoomDatabase {

    //Use the Singleton pattern for databases
    private static NoteDatabase instance;

    public abstract NoteDAO noteDAO();

    //Synchronized makes sure only one thread can access the database at the same time
    public static synchronized NoteDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), NoteDatabase.class, "note_database")
                    .fallbackToDestructiveMigration() //Makes sure the database is recreated from scratch if it is changed
                    .build();
        }
        return instance;
    }
}
