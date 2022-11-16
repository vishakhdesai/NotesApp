package com.example.notesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;

public class NoteViewActivity extends AppCompatActivity {
    Bundle extras;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_view);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setLogo(R.drawable.notes_icon);
        getSupportActionBar().setTitle("Notes");
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        TextView title = findViewById(R.id.titleText);
        TextView subtitle = findViewById(R.id.subtitleText);
        TextView description = findViewById(R.id.descriptionText);
        TextView titleText = findViewById(R.id.titleTextView);

        extras = getIntent().getExtras();
        title.setText(extras.getString("Title"));
        subtitle.setText(extras.getString("Subtitle"));
        description.setText(extras.getString("Description"));
        long time = extras.getLong("Creation Time");
//        System.out.println(time);
        String formattedTime = DateFormat.getDateTimeInstance().format(time);
        titleText.setText("Last Modified On: \n" + formattedTime);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu_edit,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.edit){
            Intent i = new Intent(getApplicationContext(), NoteEditActivity.class);
            i.putExtra("Id", extras.getInt("Id"));
            i.putExtra("Title", extras.getString("Title"));
            i.putExtra("Subtitle", extras.getString("Subtitle"));
            i.putExtra("Description", extras.getString("Description"));
            i.putExtra("Creation Time", extras.getLong("Creation Time"));
            startActivity(i);
        }
        else if (id == R.id.delete){
            int noteId = extras.getInt("Id");
            DBHelper dbHelper = new DBHelper(getApplicationContext());
            dbHelper.deleteNote(noteId);
            finish();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            Toast.makeText(this, "Note Deleted", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}