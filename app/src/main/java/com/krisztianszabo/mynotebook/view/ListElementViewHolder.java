package com.krisztianszabo.mynotebook.view;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.krisztianszabo.mynotebook.R;

public class ListElementViewHolder extends RecyclerView.ViewHolder {

    private ConstraintLayout rowLayout;
    private TextView noteTitle;
    private TextView deleteButton;

    public ListElementViewHolder(@NonNull View itemView) {
        super(itemView);

        rowLayout = (ConstraintLayout) itemView;
        noteTitle = rowLayout.findViewById(R.id.noteTitle);
        deleteButton = rowLayout.findViewById(R.id.deleteButton);
    }

    public ConstraintLayout getRowLayout() {
        return rowLayout;
    }

    public TextView getNoteTitle() {
        return noteTitle;
    }

    public TextView getDeleteButton() {
        return deleteButton;
    }
}
