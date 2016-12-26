package com.wqzhang.thingswapper.ui;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

import static android.view.accessibility.AccessibilityEvent.INVALID_POSITION;

/**
 * Created by wqzhang on 16-11-30.
 */

public class RecyclerListView extends android.support.v7.widget.RecyclerView {
    private String TAG = "RecyclerListView";

    private Context mContext;
    Scroller mScroller;

    SlideContentView slideContentView = null;
    static boolean isBootom = false;
    static int slidePosition = -1;
    private int HEAD = 0;
    private int FOOT = 1;


    public RecyclerListView(Context context) {
        super(context);
        mContext = context;
        mScroller = new Scroller(context);
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
    int lastY = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        Log.d(TAG, "Touch " + event.getAction());
        int X = 0, Y = 0;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                slidePosition = -1;
                X = (int) event.getX();
                Y = (int) event.getY();
                Log.d(TAG, "X " + X + "Y" + Y);
                View targetItemView = this.findChildViewUnder(X, Y);

                //先重置上一次的操作
                if (position != INVALID_POSITION) {
                    ViewHolder viewHolder = this.findViewHolderForAdapterPosition(position);
                    if (viewHolder != null) {
                        if (viewHolder instanceof RecyclerAdapter.ViewHolder) {
                            slideContentView = ((RecyclerAdapter.ViewHolder) viewHolder).slide_content_view;
                            slideContentView.shrink();
                        }
                    }
                }
                position = this.getChildAdapterPosition(targetItemView);
                Log.d(TAG, "position=" + position);
                if (position != INVALID_POSITION) {
                    ViewHolder viewHolder = this.findViewHolderForAdapterPosition(position);
                    if (viewHolder != null) {
                        if (viewHolder instanceof RecyclerAdapter.ViewHolder) {
//                                viewHolder = (RecyclerAdapter.ViewHolder) this.findViewHolderForAdapterPosition(position);
                            slideContentView = ((RecyclerAdapter.ViewHolder) viewHolder).slide_content_view;
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:

                RecyclerAdapter recyclerAdapter = (RecyclerAdapter) getAdapter();
                recyclerAdapter.showFooterView();
                recyclerAdapter.showHeaderView();
                Log.d(TAG, "ActionMove");
//                if (isBootom) {
//                    this.setTranslationY(0 - 110);
//                }
                if (slidePosition == FOOT) {
                    Log.d("onScroll", String.valueOf(lastY - event.getRawY()));
                    Log.d("onScroll", "getY" + String.valueOf(event.getRawY()));
                    Log.d("onScroll", "getTranslationY" + String.valueOf(getTranslationY()));

                    this.setTranslationY(-10 + getTranslationY());
//                    this.setTranslationY(event.getRawY() - lastY + getTranslationY());
//                    lastY = (int) event.getRawY();

                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                this.setTranslationY(0);

            default:
                break;
        }

        if (isBootom) {
            event.setLocation(event.getX(), event.getY() / 2);
        }
//        if (slideContentView != null) {
//            boolean isScroll = slideContentView.onRequeirTouchEvent(event);
//            if (isScroll) {
//                return true;
//            }
//        } else {
//            Log.d(TAG, "slideContentView == null");
//        }


        return super.onTouchEvent(event);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
//        Log.d("onScrolled", l + "|" + t + "|" + oldl + "|" + oldt);
        super.onScrollChanged(l, t, oldl, oldt);
    }

    @Override
    public void onScrollStateChanged(int state) {
        Log.d("onScrolled", "state" + state);
        super.onScrollStateChanged(state);
    }

    @Override
    public void onScrolled(int dx, int dy) {
        if (isBootom) {
            dy = dy / 8;
        }
//        super.onScrolled(0, 0);


//        Log.d("onScrolled", "dy" + dy);

        RecyclerAdapter recyclerAdapter = (RecyclerAdapter) getAdapter();
        if (!canScrollVertically(-1)) {
            Log.d("onScrolled", "滑动至顶部");
            slidePosition = HEAD;
//            recyclerAdapter.hiddenFooterView();
//            recyclerAdapter.showHeaderView();

//            recyclerAdapter.notifyDataSetChanged();
//                    onScrolledToTop();
        } else if (!canScrollVertically(1)) {
            Log.d("onScrolled", "滑动至底部");
            slidePosition = FOOT;
//            recyclerAdapter.showFooterView();
//            recyclerAdapter.hiddenHeaderView();
//            recyclerAdapter.notifyDataSetChanged();
//                    onScrolledToBottom();
        } else if (dy < 0) {
//            Log.d("onScrolled", "下滑");
//                    onScrolledUp();
        } else if (dy > 0) {
//            Log.d("onScrolled", "上滑");

            if (isBootom) ;
//                    onScrolledDown();
        }
    }
}
