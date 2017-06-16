package com.bignerdranch.android.minimal.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.os.ParcelUuid;
import android.provider.BaseColumns;

/**
 * Created by liubin on 2017/6/15.
 */

public final class TodoContract {

    public TodoContract() {}


    public static final String CONTENT_AUTHORITY = "com.bignerdranch.android.minimal";

    public  static final Uri BASE_CONTENT_URI= Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_TODO = "todos";

    public static final class TodoEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_TODO);

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TODO;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TODO;


        public final static String TABLE_NAME = "todos";

        public final static String _ID = BaseColumns._ID;

        public final static String COLUMN_TODO_TITLE = "title";
        public final static String COLUMN_TODO_REMINDER = "reminder";
        public final static String COLUMN_TODO_COLOR= "color";
        public final static String COLUMN_TODO_DATE = "date";
        public final static String COLUMN_TODO_UUID = "id";

    }

}
