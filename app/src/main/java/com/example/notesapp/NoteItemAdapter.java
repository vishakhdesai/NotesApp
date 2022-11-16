package com.example.notesapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.util.ArrayList;


public class NoteItemAdapter extends RecyclerView.Adapter<NoteItemAdapter.NoteViewHolder> {

    Context context;
    ArrayList<Note> noteArrayList;
    OnClickListener clickListener;

    public NoteItemAdapter(Context context, ArrayList<Note> noteArrayList, OnClickListener clickListener) {
        this.context = context;
        this.noteArrayList = noteArrayList;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoteViewHolder(LayoutInflater.from(context).inflate(R.layout.item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note = noteArrayList.get(position);
        holder.titleOutput.setText(note.getTitle());
        holder.subtitleOutput.setText(note.getSubTitle());
        String formattedTime = DateFormat.getDateTimeInstance().format(note.getCreatedTime());
        holder.createdTimeOutput.setText(formattedTime);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onItemViewClickListener(note);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                PopupMenu popupMenu = new PopupMenu(context, view);
                popupMenu.getMenu().add("DELETE");
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if(menuItem.getTitle().equals("DELETE")){
                            DBHelper dbHelper = new DBHelper(context);
                            dbHelper.deleteNote(note.getId());
                            clickListener.onDeleteClickListener(note);
                        }
                        return true;
                    }
                });
                popupMenu.show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return noteArrayList.size();
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView titleOutput;
        TextView subtitleOutput;
        TextView createdTimeOutput;
        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            titleOutput = itemView.findViewById(R.id.titleOutput);
            subtitleOutput = itemView.findViewById(R.id.subtitleOutput);
            createdTimeOutput = itemView.findViewById(R.id.timeOutput);
        }
    }

    public interface OnClickListener{
        public void onDeleteClickListener(Note note);
        public void onItemViewClickListener(Note note);
    }


}
