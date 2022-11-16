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

public class AddNewNoteActivity extends AppCompatActivity {
    EditText title;
    EditText subtitle;
    EditText description;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_note);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setLogo(R.drawable.notes_icon);
        getSupportActionBar().setTitle("New Note");
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        title = findViewById(R.id.titleInput);
        subtitle = findViewById(R.id.subtitleInput);
        description = findViewById(R.id.descriptionInput);
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
            String iTitle = title.getText().toString();
            String iSubtitle = subtitle.getText().toString();
            String iDescription = description.getText().toString();
            long createdTime = System.currentTimeMillis();

            DBHelper dbHelper = new DBHelper(getApplicationContext());
            dbHelper.addNewNote(iTitle,iSubtitle,iDescription,createdTime);
            Toast.makeText(getApplicationContext(),"Note saved",Toast.LENGTH_SHORT).show();
            finish();
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }
}