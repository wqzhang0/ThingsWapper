package com.wqzhang.thingswapper.ui;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.wqzhang.thingswapper.adapter.ToDoThingsRecyclerAdapter;
import com.wqzhang.thingswapper.listener.abs.OnScrolledListener;
import com.wqzhang.thingswapper.listener.abs.SetAllowPullStateListener;

import java.util.ArrayList;

import static android.view.accessibility.AccessibilityEvent.INVALID_POSITION;

/**
 * Created by wqzhang on 16-11-30.
 */

public class TodoThingsRecyclerListView extends android.support.v7.widget.RecyclerView implements SetAllowPullStateListener {
    private String TAG = "RecyclerListView";

    private Context mContext;
    SlideContentView slideContentView = null;

    private static int scrolledState = -1;
    public final static int PULL_UP = 0;
    public final static int PULL_UP_COMPLETE = 1;
    public final static int PULL_DOWN = 2;
    public final static int PULL_DOWN_COMPLETE = 3;

    private static boolean allowPull;

    ArrayList<OnScrolledListener> onScrolledListeners;

    public TodoThingsRecyclerListView(Context context) {
        super(context);
        this.mContext = context;
    }

    public TodoThingsRecyclerListView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
    }

    public TodoThingsRecyclerListView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;
    }


    int position = -1;
    int lastY = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        Log.d(TAG, "Touch " + event.getAction());
        int X = 0, Y = 0;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                scrolledState = -1;
                X = (int) event.getX();
                Y = (int) event.getY();
                Log.d(TAG, "X " + X + "Y" + Y);
                View targetItemView = this.findChildViewUnder(X, Y);

                //先重置上一次的操作
                if (position != INVALID_POSITION) {
                    ViewHolder viewHolder = this.findViewHolderForAdapterPosition(position);
                    if (viewHolder != null) {
                        if (viewHolder instanceof ToDoThingsRecyclerAdapter.SlideViewHolder) {
                            slideContentView = ((ToDoThingsRecyclerAdapter.SlideViewHolder) viewHolder).slide_content_view;
                            slideContentView.shrink();
                        }
                    }
                }
                position = this.getChildAdapterPosition(targetItemView);
                Log.d(TAG, "position=" + position);
                if (position != INVALID_POSITION) {
                    ViewHolder viewHolder = this.findViewHolderForAdapterPosition(position);
                    if (viewHolder != null) {
                        if (viewHolder instanceof ToDoThingsRecyclerAdapter.SlideViewHolder) {
//                                viewHolder = (RecyclerAdapter.ViewHolder) this.findViewHolderForAdapterPosition(position);
                            slideContentView = ((ToDoThingsRecyclerAdapter.SlideViewHolder) viewHolder).slide_content_view;
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:

                ToDoThingsRecyclerAdapter toDoThingsRecyclerAdapter = (ToDoThingsRecyclerAdapter) getAdapter();

                Log.d(TAG, "ActionMove");
//                if (allowPull) {
//                    this.setTranslationY(0 - 110);
//                }
                if (scrolledState == PULL_DOWN) {
//                    Log.d("onScroll", String.valueOf(lastY - event.getRawY()));
//                    Log.d("onScroll", "getY" + String.valueOf(event.getRawY()));
//                    Log.d("onScroll", "thisTranslationY" + String.valueOf(event.getRawY() - lastY));

                    if ((event.getRawY() - lastY) < 0) {
                        if (getTranslationY() > -300 || (event.getRawY() - lastY) > -300 * 4) {
                            this.setTranslationY((event.getRawY() - lastY) / 4);
                        }
                    } else {
                        this.setTranslationY(0);
                    }
                    return true;
                } else if (scrolledState == PULL_UP || scrolledState == PULL_UP_COMPLETE) {
                    if ((event.getRawY() - lastY) > 0) {
                        if (getTranslationY() < 300 || (event.getRawY() - lastY) < 300 * 4) {
                            this.setTranslationY((event.getRawY() - lastY) / 4);
                        }
                    } else {
                        this.setTranslationY(0);
                    }
                    if (getTranslationY() > 80) {
                        scrolledState = PULL_UP_COMPLETE;
                    } else {
                        scrolledState = PULL_UP;
                    }
                    return true;
                }
                lastY = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_UP:
                this.setTranslationY(0);
                if (scrolledState == PULL_UP_COMPLETE) {
                    Intent intent = new Intent("com.wqzhang.thingswapper.activity.AddToDoThingActivity");
                    mContext.startActivity(intent);
                }
            default:
                break;
        }

        if (allowPull) {
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
        if (allowPull) {
            dy = dy / 8;
        }
//        super.onScrolled(0, 0);


//        Log.d("onScrolled", "dy" + dy);

        ToDoThingsRecyclerAdapter toDoThingsRecyclerAdapter = (ToDoThingsRecyclerAdapter) getAdapter();
        if (!canScrollVertically(-1)) {
            Log.d("onScrolled", "滑动至顶部");
            scrolledState = PULL_UP;
//            recyclerAdapter.hiddenFooterView();
//            recyclerAdapter.showHeaderView();

//            recyclerAdapter.notifyDataSetChanged();
//                    onScrolledToTop();
        } else if (!canScrollVertically(1)) {
            Log.d("onScrolled", "滑动至底部");
            scrolledState = PULL_DOWN;
//            recyclerAdapter.showFooterView();
//            recyclerAdapter.hiddenHeaderView();
//            recyclerAdapter.notifyDataSetChanged();
//                    onScrolledToBottom();
        } else if (dy < 0) {
//            Log.d("onScrolled", "下滑");
//                    onScrolledUp();
        } else if (dy > 0) {
//            Log.d("onScrolled", "上滑");

            if (allowPull) ;
//                    onScrolledDown();
        }
    }

    public void addOnScrolledListener(OnScrolledListener onScrolledListener) {
        if (onScrolledListeners == null) {
            onScrolledListeners = new ArrayList<OnScrolledListener>();
        }
        for (OnScrolledListener onScrolledListener_tmp : onScrolledListeners) {
            if (onScrolledListener_tmp == onScrolledListener) {
                return;
            }
        }
        onScrolledListeners.add(onScrolledListener);
    }



    @Override
    public void setAllowPull(boolean allowPull) {
        this.allowPull = allowPull;
    }
}
