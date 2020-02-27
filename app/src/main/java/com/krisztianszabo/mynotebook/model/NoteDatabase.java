package com.krisztianszabo.mynotebook.model;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NoteDatabase {

    private FirebaseFirestore firestore;
    private final String COL = "notes";
    private static NoteDatabase instance = new NoteDatabase();

    private NoteDatabase() {
        this.firestore = FirebaseFirestore.getInstance();
    }

    public static NoteDatabase getInstance() {
        return instance;
    }

    public void addOrUpdateNote(Note note) {
        DocumentReference doc;
        if (note.isNew()) {
            doc = firestore.collection(COL).document();
        } else {
            doc = firestore.collection(COL).document(note.getId());
        }

        Map<String, String> map = new HashMap<>();
        map.put("title", note.getTitle());
        map.put("content", note.getContent());

        doc.set(map);
    }

    public void deleteNote(Note note) {
        DocumentReference doc = firestore.collection(COL).document(note.getId());
        doc.delete();
    }

    public Note parseNote(DocumentSnapshot doc) {
        Note result = new Note();
        result.setId(doc.getId());
        result.setTitle(doc.get("title").toString());
        result.setContent(doc.get("content").toString());
        return result;
    }
}
