package com.example.notesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NoteEditActivity extends AppCompatActivity {
    Bundle extras;
    EditText title;
    EditText subtitle;
    EditText description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_edit);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setLogo(R.drawable.notes_icon);
        getSupportActionBar().setTitle("Edit Note");
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        title = findViewById(R.id.titleInput);
        subtitle = findViewById(R.id.subtitleInput);
        description = findViewById(R.id.descriptionInput);

        extras = getIntent().getExtras();
        title.setText(extras.getString("Title"));
        subtitle.setText(extras.getString("Subtitle"));
        description.setText(extras.getString("Description"));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.save){
            String newTitle = title.getText().toString();
            String newSubTitle = subtitle.getText().toString();
            String newDescription = description.getText().toString();
            long modifiedTime = System.currentTimeMillis();
            int noteId = extras.getInt("Id");
            DBHelper dbHelper = new DBHelper(getApplicationContext());
            dbHelper.updateNote(noteId, newTitle, newSubTitle, newDescription, modifiedTime);
            Toast.makeText(NoteEditActivity.this, "Note Updated", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}