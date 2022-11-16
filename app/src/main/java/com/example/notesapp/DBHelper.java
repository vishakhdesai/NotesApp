package com.example.notesapp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "notesDB";

    private static final int DB_VERSION = 1;

    private static final String TABLE_NAME = "notes";

    private static final String COLUMN_ID = "ID";
    private static final String COLUMN_TITLE = "TITLE";
    private static final String COLUMN_SUBTITLE = "SUBTITLE";
    private static final String COLUMN_DESCRIPTION = "DESCRIPTION";
    private static final String COLUMN_CREATION_TIME = "CREATION_TIME";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_TITLE + " TEXT ," +
                COLUMN_SUBTITLE + " TEXT ," +
                COLUMN_DESCRIPTION + " TEXT ," +
                COLUMN_CREATION_TIME + " TEXT)" ;
        sqLiteDatabase.execSQL(sql);
    }

    public void addNewNote(String title, String subtitle, String description, long creationTime){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_SUBTITLE, subtitle);
        values.put(COLUMN_DESCRIPTION, description);
        values.put(COLUMN_CREATION_TIME, creationTime);
        try {
            db.insert(TABLE_NAME, null, values);
        }
        catch (Exception e){
            System.out.println("Error");
        }

    }

    public void deleteNote(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE ID=" + id;
        db.execSQL(sql);
    }

    public ArrayList<Note> getNotes(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_NAME + ";",null);
        ArrayList<Note> notes = new ArrayList<Note>();
        res.moveToFirst();
        while(res.isAfterLast() == false){
            @SuppressLint("Range") int id = res.getInt(res.getColumnIndex(COLUMN_ID));
            @SuppressLint("Range") String title = res.getString(res.getColumnIndex(COLUMN_TITLE));
            @SuppressLint("Range") String subtitle = res.getString(res.getColumnIndex(COLUMN_SUBTITLE));
            @SuppressLint("Range") String description = res.getString(res.getColumnIndex(COLUMN_DESCRIPTION));
            @SuppressLint("Range") long creationTIme = res.getLong(res.getColumnIndex(COLUMN_CREATION_TIME));
            Note note = new Note();
            note.setId(id);
            note.setTitle(title);
            note.setSubTitle(subtitle);
            note.setDescription(description);
            note.setCreatedTime(creationTIme);
            notes.add(note);
            res.moveToNext();
        }
        return notes;
    }

    public void updateNote(int id, String newTitle, String newSubtitle, String newDescription, long modifiedTime){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, newTitle);
        values.put(COLUMN_SUBTITLE, newSubtitle);
        values.put(COLUMN_DESCRIPTION, newDescription);
        values.put(COLUMN_CREATION_TIME, modifiedTime);
        try{
            db.update(TABLE_NAME, values,"ID=?",new String[]{id+""});
        }
        catch (Exception e){
            System.out.println("Error");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
