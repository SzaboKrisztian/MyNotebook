package com.krisztianszabo.mynotebook;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.krisztianszabo.mynotebook.model.Note;
import com.krisztianszabo.mynotebook.model.NoteDatabase;

public class NoteEditActivity extends AppCompatActivity {

    public static final String NOTE_KEY = "NOTE";
    private Note noteBeingEdited;
    private TextView titleInput;
    private TextView contentInput;
    private NoteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_edit);
        this.titleInput = findViewById(R.id.titleInput);
        this.contentInput = findViewById(R.id.contentInput);
        this.db = NoteDatabase.getInstance();

        Intent intent = getIntent();
        if (intent.hasExtra(NOTE_KEY)) {
            this.noteBeingEdited = (Note) intent.getSerializableExtra(NOTE_KEY);
            titleInput.setText(this.noteBeingEdited.getTitle());
            contentInput.setText(this.noteBeingEdited.getContent());
        } else {
            this.noteBeingEdited = new Note();
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    // TODO: Rewrite this method, it can be simplified
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.noteBeingEdited.setTitle(titleInput.getText().toString());
                this.noteBeingEdited.setContent(contentInput.getText().toString());
                if (this.noteBeingEdited.isNew()) {
                    if (!(noteBeingEdited.getTitle().isEmpty() && noteBeingEdited.getContent().isEmpty())) {
                        if (noteBeingEdited.getTitle().isEmpty()) {
                            noteBeingEdited.setTitle("Untitled note");
                        }
                        db.addOrUpdateNote(this.noteBeingEdited);
                    }
                } else {
                    db.addOrUpdateNote(this.noteBeingEdited);
                }
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.note_edit_menu, menu);
        if (noteBeingEdited.isNew()) {
            MenuItem deleteOption = menu.findItem(R.id.deleteMenu);
            if (deleteOption != null) {
                deleteOption.setVisible(false);
            }
        }
        return super.onCreateOptionsMenu(menu);
    }

    public void deleteNote(MenuItem menuItem) {
        db.deleteNote(noteBeingEdited);
        finish();
    }

    public void cancel(MenuItem menuItem) {
        finish();
    }
}
