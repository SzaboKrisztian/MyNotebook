package com.krisztianszabo.mynotebook;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.krisztianszabo.mynotebook.model.Note;
import com.krisztianszabo.mynotebook.model.NoteDao;
import com.krisztianszabo.mynotebook.model.NoteDatabase;
import com.krisztianszabo.mynotebook.view.NotesListAdapter;

import java.util.List;

public class NoteListActivity extends AppCompatActivity {

    int scrollPosition;
    RecyclerView notesList;
    NotesListAdapter adapter;
    LinearLayoutManager layoutManager;
    NoteDao database;
    List<Note> notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = NoteDatabase.getDb(getApplicationContext()).noteDao();

        notes = database.getAll();

        notesList = findViewById(R.id.notesList);
        layoutManager = new LinearLayoutManager(this);
        notesList.setLayoutManager(layoutManager);
        adapter = new NotesListAdapter(notes);
        notesList.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.note_list_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        super.onResume();
        notes.clear();
        notes.addAll(database.getAll());
        adapter.notifyDataSetChanged();
        layoutManager.scrollToPosition(scrollPosition);
    }

    @Override
    protected void onPause() {
        super.onPause();
        scrollPosition = layoutManager.findFirstVisibleItemPosition();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NoteDatabase.destroyInstance();
    }

    public void newNote(MenuItem menuItem) {
        startActivity(new Intent(this, NoteEditActivity.class));
    }
}
