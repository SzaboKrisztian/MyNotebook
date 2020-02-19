package com.krisztianszabo.mynotebook.view;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.krisztianszabo.mynotebook.NoteEditActivity;
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
                .inflate(R.layout.list_element, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ListElementViewHolder holder, final int position) {
        final TextView deleteButton = holder.getDeleteButton();
        holder.getNoteTitle().setText(notes.get(position).getTitle());
        deleteButton.setTag(notes.get(position).getId().toString());
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NoteDatabase.getDb(v.getContext()).noteDao().delete(notes.get(position));
                notes.remove(position);
                notifyDataSetChanged();
            }
        });
        holder.getRowLayout().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), NoteEditActivity.class);
                intent.putExtra(NoteEditActivity.NOTE_KEY, notes.get(position));
                v.getContext().startActivity(intent);
            }
        });
        holder.getRowLayout().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (deleteButton.getVisibility() == View.GONE) {
                    deleteButton.setVisibility(View.VISIBLE);
                } else {
                    deleteButton.setVisibility(View.GONE);
                }
                return true;
            }
        });
    }



    @Override
    public int getItemCount() {
        return notes.size();
    }
}
