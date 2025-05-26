package com.example.passmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "passwords.db";
    private static final String TABLE_USERS = "users";
    private static final String TABLE_PASSWORDS = "passwords";
    private static final String COL_USER_ID = "ID";
    private static final String COL_USER_USERNAME = "USERNAME";
    private static final String COL_USER_PASSWORD = "PASSWORD";
    private static final String COL_PASS_ID = "ID";
    private static final String COL_PASS_SERVICE = "SERVICE";
    private static final String COL_PASS_USERNAME = "USERNAME";
    private static final String COL_PASS_PASSWORD = "PASSWORD";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_USERS + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, USERNAME TEXT, PASSWORD TEXT)");
        db.execSQL("CREATE TABLE " + TABLE_PASSWORDS + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, SERVICE TEXT, USERNAME TEXT, PASSWORD TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PASSWORDS);
        onCreate(db);
    }

    public boolean addUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_USER_USERNAME, username);
        contentValues.put(COL_USER_PASSWORD, password);
        long result = db.insert(TABLE_USERS, null, contentValues);
        return result != -1; // Возвращает true, если добавление прошло успешно
    }

    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE USERNAME=? AND PASSWORD=?", new String[]{username, password});
        return cursor.getCount() > 0;
    }

    public boolean addPassword(String service, String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_PASS_SERVICE, service);
        contentValues.put(COL_PASS_USERNAME, username);
        contentValues.put(COL_PASS_PASSWORD, password);
        long result = db.insert(TABLE_PASSWORDS, null, contentValues);
        return result != -1; // Возвращает true, если добавление прошло успешно
    }
    public List<Password> getAllPasswords() {
        List<Password> passwordList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_PASSWORDS, null);

        if (cursor.moveToFirst()) {
            do {
                String service = cursor.getString(cursor.getColumnIndex(COL_PASS_SERVICE));
                String username = cursor.getString(cursor.getColumnIndex(COL_PASS_USERNAME));
                String password = cursor.getString(cursor.getColumnIndex(COL_PASS_PASSWORD));

                Password passwordEntry = new Password(service, username, password);
                passwordList.add(passwordEntry);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return passwordList;
    }
}