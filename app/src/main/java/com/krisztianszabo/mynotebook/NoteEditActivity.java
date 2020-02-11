package com.krisztianszabo.mynotebook;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.krisztianszabo.mynotebook.model.Note;
import com.krisztianszabo.mynotebook.model.NoteDatabase;

public class NoteEditActivity extends AppCompatActivity {

    public static final String NOTE_KEY = "NOTE";
    private Note noteBeingEdited;
    private TextView titleInput;
    private TextView contentInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_edit);
        this.titleInput = findViewById(R.id.titleInput);
        this.contentInput = findViewById(R.id.contentInput);

        Intent intent = getIntent();
        if (intent.hasExtra(NOTE_KEY)) {
            this.noteBeingEdited = (Note) intent.getSerializableExtra(NOTE_KEY);
            titleInput.setText(this.noteBeingEdited.getTitle());
            contentInput.setText(this.noteBeingEdited.getContent());
        } else {
            this.noteBeingEdited = new Note();
            (findViewById(R.id.deleteButton)).setVisibility(View.GONE);
        }
    }

    // TODO: change the save into a back button (perhaps use the application bar's back?)
    public void save(View view) {
        this.noteBeingEdited.setTitle(titleInput.getText().toString());
        this.noteBeingEdited.setContent(contentInput.getText().toString());
        if (this.noteBeingEdited.isNew()) {
            NoteDatabase.getDb(getApplicationContext()).noteDao().insert(this.noteBeingEdited);
        } else {
            NoteDatabase.getDb(getApplicationContext()).noteDao().update(this.noteBeingEdited);
        }
        startActivity(new Intent(this, NoteListActivity.class));
    }

    // TODO: and then get rid of the back button
    public void cancel(View view) {
        startActivity(new Intent(this, NoteListActivity.class));
    }

    // TODO: implement the delete in the listview, by some different mechanism
    public void delete(View view) {
        NoteDatabase.getDb(getApplicationContext()).noteDao().delete(this.noteBeingEdited);
        startActivity(new Intent(this, NoteListActivity.class));
    }
}
