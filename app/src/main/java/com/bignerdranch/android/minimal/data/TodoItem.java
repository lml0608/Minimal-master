package com.bignerdranch.android.minimal.data;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * Created by zengzhi on 2017/6/14.
 */

public class TodoItem implements Serializable {


    private String mTodoText;

    private boolean mIsReminder;

    private int mTodoColor;

    private Date mTodoDate;

    private UUID mTodoIdentifier;

    public TodoItem(String todoText, boolean isReminder, Date todoDate) {
        this.mTodoText = todoText;
        this.mIsReminder = isReminder;
        this.mTodoDate = todoDate;
        this.mTodoColor = 1677725;
        this.mTodoIdentifier = UUID.randomUUID();
    }

    public TodoItem(){
        this("Clean my room", true, new Date());
    }

    public String getmTodoText() {
        return mTodoText;
    }

    public void setmTodoText(String mTodoText) {
        this.mTodoText = mTodoText;
    }

    public boolean ismIsReminder() {
        return mIsReminder;
    }

    public void setmIsReminder(boolean mIsReminder) {
        this.mIsReminder = mIsReminder;
    }

    public int getmTodoColor() {
        return mTodoColor;
    }

    public void setmTodoColor(int mTodoColor) {
        this.mTodoColor = mTodoColor;
    }


    public void setmTodoDate(Date mTodoDate) {
        this.mTodoDate = mTodoDate;
    }

    public Date getmTodoDate() {
        return mTodoDate;
    }

    public UUID getmTodoIdentifier() {
        return mTodoIdentifier;
    }

}
