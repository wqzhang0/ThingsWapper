package com.wqzhang.thingswapper.UI;

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

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        return true;
    }

    int position = -1;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "Touch ");
        int X = 0, Y = 0;


        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                X = (int) event.getX();
                Y = (int) event.getY();
                Log.d(TAG, "X " + X + "Y" + Y);
                View targetItemView = this.findChildViewUnder(X, Y);

                //先重置上一次的操作
                if (position != INVALID_POSITION) {
                    RecyclerAdapterTest.ViewHolder viewHolder = (RecyclerAdapterTest.ViewHolder) this.findViewHolderForAdapterPosition(position);
                    slideContentView = (SlideContentView) viewHolder.contentView;
                    slideContentView.shrink();

                }


                position = this.getChildAdapterPosition(targetItemView);
                Log.d(TAG, "position=" + position);
                if (position != INVALID_POSITION) {
                    RecyclerAdapterTest.ViewHolder viewHolder = (RecyclerAdapterTest.ViewHolder) this.findViewHolderForAdapterPosition(position);
                    slideContentView = (SlideContentView) viewHolder.contentView;
                }
                break;
            default:
                break;
        }

        if (slideContentView != null)

        {
            slideContentView.onRequeirTouchEvent(event);
        } else

        {
            Log.d(TAG, "slideContentView == null");
        }

        return super.

                onTouchEvent(event);
    }
}
