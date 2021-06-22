package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class SqlDatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "db_marathon";
    private static final String TABLE_NAME = "participants";
    private static final String ID = "id";
    private static final String name = "name";
    private static final String email = "email";

    public SqlDatabaseHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String table_query = " CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "( " +
                ID + " INTEGER PRIMARY KEY, " +
                name + " text ," +
                email + " text " + ")";

        db.execSQL(table_query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    public void addParticipant(ParticipentModel participant) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(name, participant.getName());
        contentValues.put(email, participant.getEmail());

        db.insert(TABLE_NAME, null, contentValues);
        db.close();
    }

    public ParticipentModel getParticipent(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{ID, name, email}, ID + " =? ", new String[]{String.valueOf(id)}, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        ParticipentModel participent = new ParticipentModel(cursor.getString(0), cursor.getString(1), cursor.getString(2));
        db.close();
        cursor.close();
        return participent;
    }

    public List<ParticipentModel> getAllParticipents() {

        List<ParticipentModel> participentList = new ArrayList<>();
        String query = " SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                ParticipentModel participent = new ParticipentModel(cursor.getString(0), cursor.getString(1), cursor.getString(2));
                participentList.add(participent);
            } while (cursor.moveToNext());

        }
        db.close();
        cursor.close();
        return participentList;
    }

    public int updateParticipent(ParticipentModel participentModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(name, participentModel.getName());
        contentValues.put(email, participentModel.getEmail());
        return  db.update(TABLE_NAME, contentValues, " ID = ?", new String[]{String.valueOf(participentModel.getID())});

    }

    public void deleteParticipent(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "ID = ?", new String[]{id});
        db.close();
    }

    public int totalcount() {
        String query = " SELECT *  FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        System.out.println(cursor.getCount());
        return cursor.getCount();

    }
}
