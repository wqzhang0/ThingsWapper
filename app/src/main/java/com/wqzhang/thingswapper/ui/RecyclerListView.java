package com.wqzhang.thingswapper.ui;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import static android.view.accessibility.AccessibilityEvent.INVALID_POSITION;

/**
 * Created by wqzhang on 16-11-30.
 */

public class RecyclerListView extends android.support.v7.widget.RecyclerView {
    private String TAG = "RecyclerListView";

    SlideContentView slideContentView = null;

    public RecyclerListView(Context context) {
        super(context);
    }

    public RecyclerListView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RecyclerListView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
//
//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent e) {
//        if(e.getAction() == MotionEvent.ACTION_DOWN){
//            return true;
//        }
//        return super.onInterceptTouchEvent(e);
//    }

    int position = -1;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "Touch " + event.getAction());
        int X = 0, Y = 0;


        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                X = (int) event.getX();
                Y = (int) event.getY();
                Log.d(TAG, "X " + X + "Y" + Y);
                View targetItemView = this.findChildViewUnder(X, Y);

                //先重置上一次的操作
                if (position != INVALID_POSITION) {
                    RecyclerAdapter.ViewHolder viewHolder = (RecyclerAdapter.ViewHolder) this.findViewHolderForAdapterPosition(position);
                    if (viewHolder != null) {
                        slideContentView = viewHolder.slide_content_view;
                        slideContentView.shrink();

                    }
                }

                position = this.getChildAdapterPosition(targetItemView);
                Log.d(TAG, "position=" + position);
                if (position != INVALID_POSITION) {
                    RecyclerAdapter.ViewHolder viewHolder = (RecyclerAdapter.ViewHolder) this.findViewHolderForAdapterPosition(position);
                    slideContentView = viewHolder.slide_content_view;

                }
                break;
            default:
                break;
        }

        if (slideContentView != null) {
            boolean isScroll = slideContentView.onRequeirTouchEvent(event);
            if (isScroll) {
                return true;
            }
        } else {
            Log.d(TAG, "slideContentView == null");
        }

        return super.onTouchEvent(event);
    }
}
