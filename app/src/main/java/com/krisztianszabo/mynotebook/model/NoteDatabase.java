package com.krisztianszabo.mynotebook.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Note.class}, version = 2, exportSchema = false)
public abstract class NoteDatabase extends RoomDatabase {

    private static NoteDatabase instance;
    public abstract NoteDao noteDao();

    public static NoteDatabase getDb(Context context) {
        if (instance == null) {
            // TODO: get rid of .allowMainThreadQueries() and implement the DB access with worker threads
            instance = Room.databaseBuilder(context.getApplicationContext(), NoteDatabase.class,
                    "mynotebook").allowMainThreadQueries().build();
        }
        return instance;
    }

    public static void destroyInstance() {
        instance = null;
    }
}