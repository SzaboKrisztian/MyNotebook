package com.krisztianszabo.mynotebook;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.krisztianszabo.mynotebook.model.Note;
import com.krisztianszabo.mynotebook.model.NoteDao;
import com.krisztianszabo.mynotebook.model.NoteDatabase;

import java.util.List;

public class NoteListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NoteDao dao = NoteDatabase.getDb(getApplicationContext()).noteDao();

        final List<Note> notes = dao.getAll();

        ListView listView = findViewById(R.id.listView);

        listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                notes));

        final Intent editNoteActivity = new Intent(this, NoteEditActivity.class);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                editNoteActivity.putExtra(NoteEditActivity.NOTE_KEY, notes.get(position));
                startActivity(editNoteActivity);
            }
        });
    }

    public void newNote(View view) {
        startActivity(new Intent(this, NoteEditActivity.class));
    }
}
