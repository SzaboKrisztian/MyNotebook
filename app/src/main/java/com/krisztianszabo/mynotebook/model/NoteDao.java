package com.krisztianszabo.mynotebook.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NoteDao {
    @Query("SELECT * FROM notes;")
    List<Note> getAll();

    @Query("SELECT * FROM notes WHERE id = :id;")
    Note findById(Integer id);

    @Insert
    long insert(Note note);

    @Update
    void update(Note note);

    @Delete
    void delete(Note note);
}