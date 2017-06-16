package com.bignerdranch.android.minimal.adapter;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bignerdranch.android.minimal.AddTodoActivity;
import com.bignerdranch.android.minimal.R;
import com.bignerdranch.android.minimal.data.TodoContract;
import com.bignerdranch.android.minimal.listener.onMoveAndSwipedListener;

import java.util.Collections;
import java.util.Date;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by zengzhi on 2017/6/14.
 */

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoHolder> implements onMoveAndSwipedListener {

    private static final String TAG = "TodoAdapter";
    private Cursor mCursor;
    private Context mContext;
    private View mEmptyView;

    public TodoAdapter(Context context, View empyView) {
        this.mContext = context;
        this.mEmptyView = empyView;
    }

    @Override
    public TodoAdapter.TodoHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View rootView = LayoutInflater.from(mContext).inflate(R.layout.list_item_view_future, parent, false);
        return new TodoHolder(rootView);
    }

    @Override
    public void onBindViewHolder(TodoAdapter.TodoHolder holder, int position) {

        mCursor.moveToPosition(position);
//        TextDrawable myDrawable = TextDrawable.builder().beginConfig()
//                .textColor(Color.WHITE)
//                .useFont(Typeface.DEFAULT)
//                .toUpperCase()
//                .endConfig()
//                .buildRound(item.getmTodoText().substring(0,1),item.getmTodoColor());

//            TextDrawable myDrawable = TextDrawable.builder().buildRound(item.getToDoText().substring(0,1),holder.color);
        holder.imageView.setImageResource(R.drawable.check);
        //Log.i(TAG, mCursor.getString(mCursor.getColumnIndex(TodoContract.TodoEntry.COLUMN_TODO_UUID)));
        holder.mTitle.setText(mCursor.getString(mCursor.getColumnIndex(TodoContract.TodoEntry.COLUMN_TODO_TITLE)));
        long date = mCursor.getLong(mCursor.getColumnIndex(TodoContract.TodoEntry.COLUMN_TODO_DATE));
        Log.i(TAG, String.valueOf(date));
        if (date != 0) {
            Log.i(TAG, "x = " + new Date(date).toString());
            holder.mDate.setText(new Date(date).toString());
        } else {
            holder.mDate.setText("");
        }



    }

    @Override
    public int getItemCount() {
        if (null == mCursor) return 0;
        return mCursor.getCount();
    }

    public void removeItem(int position) {
        mCursor.move(position);
        notifyItemRemoved(position);
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
//        mCursor.swap(mCursor, fromPosition, toPosition);
//        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onItemDismiss(int position) {
        //mCursor.move(position);

        //删除数据库

        int id  = mCursor.getInt(mCursor.getColumnIndex(TodoContract.TodoEntry._ID));
        Log.i(TAG, String.valueOf(id));
        Uri mCurrentPetUri = ContentUris.withAppendedId(TodoContract.TodoEntry.CONTENT_URI, id);

        if (mCurrentPetUri != null) {
            // Call the ContentResolver to delete the pet at the given content URI.
            // Pass in null for the selection and selection args because the mCurrentPetUri
            // content URI already identifies the pet that we want.
            int rowsDeleted = mContext.getContentResolver().delete(mCurrentPetUri, null, null);

            // Show a toast message depending on whether or not the delete was successful.
            if (rowsDeleted == 0) {
                // If no rows were deleted, then there was an error with the delete.
//                Toast.makeText(this, mContext.getString(R.string.editor_delete_pet_failed),
//                        Toast.LENGTH_SHORT).show();
            } else {
//                // Otherwise, the delete was successful and we can display a toast.
//                Toast.makeText(this, getString(R.string.editor_delete_pet_successful),
//                        Toast.LENGTH_SHORT).show();
            }
        }



        notifyItemRemoved(position);
    }



    public class TodoHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView imageView;
        private TextView mTitle;
        private TextView mDate;

        public TodoHolder(View v) {
            super(v);

            imageView = (ImageView)v.findViewById(R.id.todo_litsitem_image);
            mTitle = (TextView)v.findViewById(R.id.list_item_todo_title);
            mDate =(TextView)v.findViewById(R.id.list_item_todo_time);

            v.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {

            int adaterPosition = getAdapterPosition();
            mCursor.moveToPosition(adaterPosition);
            int id  = mCursor.getInt(mCursor.getColumnIndex(TodoContract.TodoEntry._ID));
            Log.i(TAG, String.valueOf(id));
            Log.i(TAG, mCursor.getString(mCursor.getColumnIndex(TodoContract.TodoEntry.COLUMN_TODO_UUID)));
            Log.i(TAG, mCursor.getString(mCursor.getColumnIndex(TodoContract.TodoEntry.COLUMN_TODO_TITLE)));
            Intent intent = new Intent(mContext, AddTodoActivity.class);
            Uri currentTodoUri = ContentUris.withAppendedId(TodoContract.TodoEntry.CONTENT_URI, id);
            intent.setData(currentTodoUri);
            mContext.startActivity(intent);
        }
    }

    public void swapCursor(Cursor cursor) {
        mCursor = cursor;
        notifyDataSetChanged();
        mEmptyView.setVisibility(getItemCount() == 0 ? View.VISIBLE : View.GONE);
    }
}
