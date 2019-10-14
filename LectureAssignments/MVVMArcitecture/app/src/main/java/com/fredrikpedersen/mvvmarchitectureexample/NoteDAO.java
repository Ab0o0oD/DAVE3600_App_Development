package com.fredrikpedersen.mvvmarchitectureexample;

//DAO = Data Access Object
//DAOs needs to be Abstract Classes or Interfaces
//Using room makes SQL statements typesafe and prevents errors at runtime, giving them at compiletime instead

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NoteDAO {

    @Insert
    void insert(Note note);

    @Update
    void update(Note note);

    @Delete
    void delete(Note note);

    @Query("DELETE FROM note_table")
    void deleteAllNotes();

    @Query("SELECT * FROM note_table ORDER BY priority DESC")
    LiveData<List<Note>> gettAllNotes();


}
