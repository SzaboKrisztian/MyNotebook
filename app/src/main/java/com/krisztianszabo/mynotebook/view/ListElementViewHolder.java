package com.krisztianszabo.mynotebook.view;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.krisztianszabo.mynotebook.NoteEditActivity;
import com.krisztianszabo.mynotebook.R;
import com.krisztianszabo.mynotebook.model.Note;
import com.krisztianszabo.mynotebook.model.NoteDatabase;

import java.util.List;

public class ListElementViewHolder extends RecyclerView.ViewHolder {

    private List<Note> notes;
    private ConstraintLayout rowLayout;
    private TextView noteTitle;
    private TextView deleteButton;
    private RecyclerView.Adapter adapter;

    public ListElementViewHolder(@NonNull View itemView, List<Note> notes, RecyclerView.Adapter adapter) {
        super(itemView);

        this.adapter = adapter;
        this.notes = notes;
        rowLayout = (ConstraintLayout) itemView;
        noteTitle = rowLayout.findViewById(R.id.noteTitle);
        deleteButton = rowLayout.findViewById(R.id.deleteButton);
    }

    public void initViewHolder(final int position) {
        noteTitle.setText(notes.get(position).getTitle());

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NoteDatabase.getInstance().deleteNote(notes.get(position));
                notes.remove(position);
                deleteButton.setVisibility(View.GONE);
                adapter.notifyDataSetChanged();
            }
        });

        rowLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), NoteEditActivity.class);
                intent.putExtra(NoteEditActivity.NOTE_KEY, notes.get(position));
                v.getContext().startActivity(intent);
            }
        });

        rowLayout.setOnLongClickListener(new View.OnLongClickListener() {
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
}
