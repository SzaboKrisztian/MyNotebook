package com.krisztianszabo.mynotebook.view;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.krisztianszabo.mynotebook.R;
import com.krisztianszabo.mynotebook.model.Note;
import com.krisztianszabo.mynotebook.model.NoteDatabase;

import java.util.List;

public class NotesListAdapter extends RecyclerView.Adapter<ListElementViewHolder> {

    private List<Note> notes;

    public NotesListAdapter(List<Note> notes) {
        this.notes = notes;
    }

    @NonNull
    @Override
    public ListElementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ListElementViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_element, parent, false), notes, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ListElementViewHolder holder, final int position) {
        holder.initViewHolder(position);
    }


    @Override
    public int getItemCount() {
        return notes.size();
    }
}
