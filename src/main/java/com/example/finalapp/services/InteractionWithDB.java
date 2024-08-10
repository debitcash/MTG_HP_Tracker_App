package com.example.finalapp.services;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.finalapp.authentication.Login;
import com.example.finalapp.authentication.Signup;

public class InteractionWithDB extends SQLiteOpenHelper {
    private static final String DB_NAME = "CounterDB";
    private static final String TABLE_NAME = "UserAuth";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_GAMES = "savedGames";

    private final Activity activity;

    public InteractionWithDB(Activity activity) {
        super(activity, DB_NAME, null, 1);
        this.activity = activity;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTableQuery = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_USERNAME + " TEXT, "
                + COLUMN_PASSWORD + " TEXT, "
                + COLUMN_GAMES + " TEXT " + ")";
        sqLiteDatabase.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {}

    public void addUser(String name, String password)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, name);
        values.put(COLUMN_PASSWORD, password);

        if (nameExists(name)) {
            Toast.makeText(activity, "Username already exists", Toast.LENGTH_SHORT).show();
        }
        else
        {
            db.insert(TABLE_NAME, null, values);
            db.close();
            Intent intent = new Intent(activity, Login.class);
            activity.startActivity(intent);
        }
    }

    public boolean validateLogin(String name, String password)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_USERNAME + " = \'" + name + "\' AND " + COLUMN_PASSWORD + " = \'" + password + "\'";
        Cursor cursor = db.rawQuery(query, null);

        boolean pass = cursor.getCount() > 0;

        cursor.close();
        db.close();

        return pass;
    }

    public boolean nameExists(String name)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_USERNAME + " = \'" + name + "\'";

        Cursor cursor = db.rawQuery(query, null);

        boolean pass = cursor.getCount() > 0;
        cursor.close();

        return pass;
    }
}
