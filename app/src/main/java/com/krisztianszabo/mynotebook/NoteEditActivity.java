package com.krisztianszabo.mynotebook;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import com.krisztianszabo.mynotebook.model.Note;
import com.krisztianszabo.mynotebook.model.NoteDatabase;

import java.util.zip.GZIPOutputStream;

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
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

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
                        NoteDatabase.getDb(getApplicationContext()).noteDao().insert(this.noteBeingEdited);
                    }
                } else {
                    NoteDatabase.getDb(getApplicationContext()).noteDao().update(this.noteBeingEdited);
                }
                NavUtils.navigateUpFromSameTask(this);
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
        NoteDatabase.getDb(this).noteDao().delete(noteBeingEdited);
        finish();
    }

    public void cancel(MenuItem menuItem) {
        finish();
    }
}
