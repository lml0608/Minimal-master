package com.bignerdranch.android.minimal.listener;

/**
 * Created by zengzhi on 2017/6/16.
 */

public interface onMoveAndSwipedListener {

    boolean onItemMove(int fromPosition, int toPosition);

    void onItemDismiss(int position);
}
