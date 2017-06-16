package com.bignerdranch.android.minimal;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.bignerdranch.android.minimal.adapter.TodoAdapter;
import com.bignerdranch.android.minimal.data.TodoContract;
import com.bignerdranch.android.minimal.data.TodoContract.TodoEntry;
import com.bignerdranch.android.minimal.data.TodoItem;
import com.bignerdranch.android.minimal.utils.ItemTouchHelperCallback;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor>{

    private static final String TAG = "MainActivity";

    private static final int TODO_LOADER = 0;
    private TodoAdapter mAdapter;

    private View mEmptyView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mEmptyView = findViewById(R.id.todo_empty_view);

        CoordinatorLayout mCoordLayout = (CoordinatorLayout) findViewById(R.id.myCoordinatorLayout);

        final FloatingActionButton mAddFAB  = (FloatingActionButton) findViewById(R.id.add_todo_item_fab);

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.todo_recycler_view);

//        mRecyclerView.set(findViewById(R.id.toDoEmptyView));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new TodoAdapter(this, mEmptyView);

        mRecyclerView.setAdapter(mAdapter);

        ItemTouchHelper.Callback callback = new ItemTouchHelperCallback(mAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState > 0) {

                    mAddFAB.hide();
                } else {
                    mAddFAB.show();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });



        mAddFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //跳转至编辑页面

                Intent intent = new Intent(MainActivity.this, AddTodoActivity.class);
                startActivity(intent);
            }
        });

        getLoaderManager().initLoader(TODO_LOADER, null, this);

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        String[] projection = {
                TodoEntry._ID,
                TodoEntry.COLUMN_TODO_TITLE,
                TodoEntry.COLUMN_TODO_REMINDER,
                TodoEntry.COLUMN_TODO_COLOR,
                TodoEntry.COLUMN_TODO_DATE,
                TodoEntry.COLUMN_TODO_UUID };
        return new CursorLoader(this,
                TodoEntry.CONTENT_URI,
                projection,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader loader, Cursor data) {
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader loader) {
        mAdapter.swapCursor(null);
    }

    private void insertTodo() {

        ContentValues values = new ContentValues();

        values.put(TodoEntry.COLUMN_TODO_TITLE, "sbbbbb");
        values.put(TodoEntry.COLUMN_TODO_REMINDER, 1);
        values.put(TodoEntry.COLUMN_TODO_COLOR, 123654);
        values.put(TodoEntry.COLUMN_TODO_DATE, new Date().getTime());
        values.put(TodoEntry.COLUMN_TODO_UUID, UUID.randomUUID().toString());

        Uri newUri = getContentResolver().insert(TodoEntry.CONTENT_URI, values);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_add:

                //添加数据
                insertTodo();

                return true;
            default:
                return super.onOptionsItemSelected(item);

        }

    }
}
