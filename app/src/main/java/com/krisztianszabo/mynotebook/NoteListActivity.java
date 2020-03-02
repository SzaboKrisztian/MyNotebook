package com.krisztianszabo.mynotebook;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.krisztianszabo.mynotebook.model.Note;
import com.krisztianszabo.mynotebook.model.NoteDatabase;
import com.krisztianszabo.mynotebook.view.NotesListAdapter;

import java.util.ArrayList;
import java.util.List;

public class NoteListActivity extends AppCompatActivity {

    private int scrollPosition;
    private RecyclerView notesList;
    private NotesListAdapter adapter;
    private LinearLayoutManager layoutManager;
    private NoteDatabase db;
    private List<Note> notes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = NoteDatabase.getInstance();

        notesList = findViewById(R.id.notesList);
        layoutManager = new LinearLayoutManager(this);
        notesList.setLayoutManager(layoutManager);
        adapter = new NotesListAdapter(notes);
        notesList.setAdapter(adapter);
        db.setChangeListener(notes, adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.note_list_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        super.onResume();
        layoutManager.scrollToPosition(scrollPosition);
    }

    @Override
    protected void onPause() {
        super.onPause();
        scrollPosition = layoutManager.findFirstVisibleItemPosition();
    }

    public void newNote(MenuItem menuItem) {
        startActivity(new Intent(this, NoteEditActivity.class));
    }
}
