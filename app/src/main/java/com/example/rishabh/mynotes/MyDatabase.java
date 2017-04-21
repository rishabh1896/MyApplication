package com.example.rishabh.mynotes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabase extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "MyNotes.db";
    private static final int DATABASE_VERSION = 1;
    public static final String Notes_TABLE_NAME = "mynotes";
    public static final String PERSON_COLUMN_ID = "_id";
    public static final String Notes_COLUMN_Notes = "notes";
    public static final String Notes_COLUMN_Heading = "heading";
    public MyDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + Notes_TABLE_NAME + "(" +
                PERSON_COLUMN_ID + " INTEGER PRIMARY KEY, " +
                Notes_COLUMN_Notes + " TEXT, " +
                Notes_COLUMN_Heading + " TEXT) "
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Notes_TABLE_NAME);
        onCreate(db);
    }
    public boolean insertNotes(String heading, String notes) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Notes_COLUMN_Heading, heading);
        contentValues.put(Notes_COLUMN_Notes, notes);
        db.insert(Notes_TABLE_NAME, null, contentValues);
        return true;
    }
    public boolean updateNotes(Integer id, String Heading, String Notes) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Notes_COLUMN_Heading, Heading);
        contentValues.put(Notes_COLUMN_Notes, Notes);
        db.update(Notes_TABLE_NAME, contentValues, PERSON_COLUMN_ID + " = ? ", new String[] { Integer.toString(id) } );
        return true;
    }
    public Cursor getNotes(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery( "SELECT * FROM " + Notes_TABLE_NAME + " WHERE " +
                PERSON_COLUMN_ID + "=?", new String[] { Integer.toString(id) } );
        return res;
    }
    public Cursor getAllNotes() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery( "SELECT * FROM " + Notes_TABLE_NAME, null );
        return res;
    }
    public Integer deleteNotes(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(Notes_TABLE_NAME,
                PERSON_COLUMN_ID + " = ? ",
                new String[] { Integer.toString(id) });
    }
}
