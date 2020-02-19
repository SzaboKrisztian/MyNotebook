package com.krisztianszabo.mynotebook;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.krisztianszabo.mynotebook.model.Note;
import com.krisztianszabo.mynotebook.model.NoteDao;
import com.krisztianszabo.mynotebook.model.NoteDatabase;
import com.krisztianszabo.mynotebook.view.NotesListAdapter;

import java.util.List;

public class NoteListActivity extends AppCompatActivity {

    RecyclerView notesList;
    NotesListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NoteDao dao = NoteDatabase.getDb(getApplicationContext()).noteDao();

        final List<Note> notes = dao.getAll();

        notesList = findViewById(R.id.notesList);
        notesList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NotesListAdapter(notes);
        notesList.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NoteDatabase.destroyInstance();
    }

    public void newNote(View view) {
        startActivity(new Intent(this, NoteEditActivity.class));
    }
}
