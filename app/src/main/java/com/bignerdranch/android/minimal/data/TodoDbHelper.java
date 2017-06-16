package com.bignerdranch.android.minimal.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static android.R.attr.version;

/**
 * Created by liubin on 2017/6/15.
 */

public class TodoDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "shelter.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TAG = "TodoDbHelper";

    public TodoDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String SQL_CREATE_TODOS_TABLE = "create table " + TodoContract.TodoEntry.TABLE_NAME + "(" +
                TodoContract.TodoEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TodoContract.TodoEntry.COLUMN_TODO_TITLE + ", " +
                TodoContract.TodoEntry.COLUMN_TODO_REMINDER + ", " +
                TodoContract.TodoEntry.COLUMN_TODO_COLOR + ", " +
                TodoContract.TodoEntry.COLUMN_TODO_DATE + ", " +
                TodoContract.TodoEntry.COLUMN_TODO_UUID +
                ")";

        Log.i(TAG, SQL_CREATE_TODOS_TABLE);

        db.execSQL(SQL_CREATE_TODOS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
