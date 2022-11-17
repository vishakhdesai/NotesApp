package com.example.notesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements NoteItemAdapter.OnClickListener {
    SearchView searchView;
    ArrayList<Note> noteArrayList;
    NoteItemAdapter noteItemAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setLogo(R.drawable.notes_icon);
        getSupportActionBar().setTitle("Notes");
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        FloatingActionButton addNewNoteButton = findViewById(R.id.addNewNoteButton);
        searchView = findViewById(R.id.searchView);
        searchView.clearFocus();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                filterList(s);
                return true;
            }
        });
        addNewNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,AddNewNoteActivity.class);
                startActivity(i);
            }
        });

        DBHelper dbHelper = new DBHelper(MainActivity.this);
        noteArrayList = dbHelper.getNotes();
        RecyclerView recyclerView = findViewById(R.id.recyclerview);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        noteItemAdapter = new NoteItemAdapter(getApplicationContext(), noteArrayList, this);
        recyclerView.setAdapter(noteItemAdapter);
    }

    private void filterList(String s) {
        ArrayList<Note> filteredNotes = new ArrayList<Note>();
        for (Note note: noteArrayList) {
            if(note.getTitle().toLowerCase().contains(s)){
                filteredNotes.add(note);
            }
        }

        if (filteredNotes.isEmpty()){
            Toast.makeText(this, "No match found", Toast.LENGTH_SHORT).show();
        }
        else{
            noteItemAdapter.setFilter(filteredNotes);
        }
    }

    @Override
    public void onDeleteClickListener(Note note) {
        finish();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        Toast.makeText(this, "Note Deleted", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemViewClickListener(Note note) {
        Intent i = new Intent(getApplicationContext(), NoteViewActivity.class);
        i.putExtra("Id", note.getId());
        i.putExtra("Title", note.getTitle());
        i.putExtra("Subtitle", note.getSubTitle());
        i.putExtra("Description", note.getDescription());
        i.putExtra("Creation Time", note.getCreatedTime());
//        System.out.println(note.getCreatedTime());
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.ascending){
            Collections.sort(noteArrayList, new Comparator<Note>() {
                @Override
                public int compare(Note note, Note t1) {
                    String formattedTime1 = DateFormat.getDateTimeInstance().format(note.getCreatedTime());
                    String formattedTime2 = DateFormat.getDateTimeInstance().format(t1.getCreatedTime());
                    return formattedTime1.compareToIgnoreCase(formattedTime2);
                }
            });
            noteItemAdapter.notifyDataSetChanged();
        }
        else if(id == R.id.descending){
            Collections.sort(noteArrayList, new Comparator<Note>() {
                @Override
                public int compare(Note note, Note t1) {
                    String formattedTime1 = DateFormat.getDateTimeInstance().format(note.getCreatedTime());
                    String formattedTime2 = DateFormat.getDateTimeInstance().format(t1.getCreatedTime());
                    return formattedTime2.compareToIgnoreCase(formattedTime1);
                }
            });
            noteItemAdapter.notifyDataSetChanged();
        }
        return super.onOptionsItemSelected(item);
    }
}