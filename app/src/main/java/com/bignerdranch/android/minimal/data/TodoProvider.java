package com.bignerdranch.android.minimal.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.bignerdranch.android.minimal.data.TodoContract.TodoEntry;

/**
 * Created by liubin on 2017/6/15.
 */

public class TodoProvider extends ContentProvider {

    private static final String LOG_TAG = "TodoProvider";
    private static final int TODOS = 100;
    private static final int TODO_ID = 101;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);


    static {

        sUriMatcher.addURI(TodoContract.CONTENT_AUTHORITY, TodoContract.PATH_TODO, TODOS);

        sUriMatcher.addURI(TodoContract.CONTENT_AUTHORITY, TodoContract.PATH_TODO + "/#", TODO_ID);
    }


    private TodoDbHelper mDbHelper;




    @Override
    public boolean onCreate() {

        mDbHelper = new TodoDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        SQLiteDatabase database = mDbHelper.getReadableDatabase();

        Cursor cursor;

        int match = sUriMatcher.match(uri);

        switch (match) {

            case TODOS:

                cursor = database.query(TodoEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;

            case TODO_ID:
                selection = TodoEntry._ID+ "= ? ";

                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};

                cursor = database.query(TodoEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);

                break;
            default:

                throw new IllegalArgumentException("Cannot query unknown URI " + uri);

        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        long id = database.insert(TodoEntry.TABLE_NAME, null, values);

        if (id == -1) {
            Log.i(LOG_TAG, "failed to insert row for " + uri);
            return null;
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Get writeable database
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        // Track the number of rows that were deleted
        int rowsDeleted;

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case TODOS:
                // Delete all rows that match the selection and selection args
                rowsDeleted = database.delete(TodoEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case TODO_ID:
                // Delete a single row given by the ID in the URI
                selection = TodoEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                rowsDeleted = database.delete(TodoEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }

        // If 1 or more rows were deleted, then notify all listeners that the data at the
        // given URI has changed
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        // Return the number of rows deleted
        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
