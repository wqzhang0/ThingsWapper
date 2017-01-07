package com.wqzhang.thingswapper.ui;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.wqzhang.thingswapper.adapters.ToDoThingsRecyclerAdapter;
import com.wqzhang.thingswapper.events.PullFreshScrollingEvent;
import com.wqzhang.thingswapper.listener.abs.OnScrolledListener;
import com.wqzhang.thingswapper.listener.impl.AllowPullStateListenerImpl;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import static android.view.accessibility.AccessibilityEvent.INVALID_POSITION;

/**
 * Created by wqzhang on 16-11-30.
 */

public class TodoThingsRecyclerListView extends android.support.v7.widget.RecyclerView implements AllowPullStateListenerImpl {
    private String TAG = "RecyclerListView";

    private Context mContext;
    SlideContentView slideContentView = null;
    private EventBus bus;


    private static int scrolledState = -1;
    public final static int PULL_UP = Integer.parseInt("000001", 2);
    public final static int PULL_UP_COMPLETE = Integer.parseInt("000010", 2);
    public final static int PULL_DOWN = Integer.parseInt("000100", 2);
    public final static int PULL_DOWN_COMPLETE = Integer.parseInt("001000", 2);
    public final static int RESET = Integer.parseInt("010000", 2);
    public final static int DO_NOTHING = Integer.parseInt("100000", 2);

    private static boolean allowPull;

    ArrayList<OnScrolledListener> onScrolledListeners = new ArrayList<>();

    public TodoThingsRecyclerListView(Context context) {
        super(context);
        this.mContext = context;
        bus = EventBus.getDefault();
    }

    public TodoThingsRecyclerListView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        bus = EventBus.getDefault();
    }

    public TodoThingsRecyclerListView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;
        bus = EventBus.getDefault();
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


                lastY = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:

                if (allowPull) {
                    int scrollValue = (int) (event.getRawY() - lastY);
                    if (scrollValue < 0) {
                        scrolledState = PULL_UP;
                    } else if (scrollValue > 0) {
                        scrolledState = PULL_DOWN;
                    } else {
                        scrolledState = DO_NOTHING;
                    }

                    if (scrolledState == PULL_DOWN) {
                        //可以下滑
                        if (scrollValue <= 200) {
                            bus.post(new PullFreshScrollingEvent(0,-scrollValue));
                        } else {
                            bus.post(new PullFreshScrollingEvent(0,-200));
                        }
                        if (scrollValue > 180) {
                            OnScrolledToDownComplete();
                            scrolledState = PULL_DOWN_COMPLETE;
                        } else {
                            OnScrolledToDown();
                        }
                        return true;
                    } else if (scrolledState == PULL_UP || scrolledState == PULL_UP_COMPLETE) {
                        //可以上滑
                        if (scrollValue >= -200) {
                            bus.post(new PullFreshScrollingEvent(0,-scrollValue));
                        } else {
                            bus.post(new PullFreshScrollingEvent(0,200));
                        }
                        if (scrollValue > -180) {
                            OnScrolledToUp();
                        } else {
                            OnScrolledToUpComplete();
                            scrolledState = PULL_UP_COMPLETE;
                        }
                        return true;
                    } else if (scrolledState == RESET) {

                    } else if (scrolledState == DO_NOTHING) {

                    }

                }
                lastY = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_UP:
//                scrolledState = RESET;
                if (scrolledState == PULL_UP_COMPLETE) {
                    Log.d(TAG, "切换至下一页");


                } else if (scrolledState == PULL_DOWN_COMPLETE) {

                    Intent intent = new Intent("com.wqzhang.thingswapper.activity.AddToDoThingActivity");
                    mContext.startActivity(intent);
                }
                bus.post(new PullFreshScrollingEvent(0, 0, 0, 0, 1000));
                OnScrolledReset();

            default:
                break;
        }

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
//        if (allowPull) {
//            dy = dy / 8;
//        }
////        super.onScrolled(0, 0);
//
//
////        Log.d("onScrolled", "dy" + dy);
//
//        ToDoThingsRecyclerAdapter toDoThingsRecyclerAdapter = (ToDoThingsRecyclerAdapter) getAdapter();
//        if (!canScrollVertically(-1)) {
//            Log.d("onScrolled", "滑动至顶部");
//            scrolledState = PULL_UP;
////            recyclerAdapter.hiddenFooterView();
////            recyclerAdapter.showHeaderView();
//
////            recyclerAdapter.notifyDataSetChanged();
////                    onScrolledToTop();
//        } else if (!canScrollVertically(1)) {
//            Log.d("onScrolled", "滑动至底部");
//            scrolledState = PULL_DOWN;
////            recyclerAdapter.showFooterView();
////            recyclerAdapter.hiddenHeaderView();
////            recyclerAdapter.notifyDataSetChanged();
////                    onScrolledToBottom();
//        } else if (dy < 0) {
////            Log.d("onScrolled", "下滑");
////                    onScrolledUp();
//        } else if (dy > 0) {
////            Log.d("onScrolled", "上滑");
//
//            if (allowPull) ;
////                    onScrolledDown();
//        }
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



    private void OnScrolledToDown() {
        for (OnScrolledListener onScrollListener : onScrolledListeners) {
            onScrollListener.onScrolledToDown();
        }
    }

    private void OnScrolledToUpComplete() {
        for (OnScrolledListener onScrollListener : onScrolledListeners) {
            onScrollListener.onScrolledToUpComplete();
        }
    }

    private void OnScrolledToUp() {
        for (OnScrolledListener onScrollListener : onScrolledListeners) {
            onScrollListener.onScrolledToUp();
        }
    }

    private void OnScrolledToDownComplete() {
        for (OnScrolledListener onScrollListener : onScrolledListeners) {
            onScrollListener.onScrolledToDownComplete();
        }
    }

    private void OnScrolledReset() {
        for (OnScrolledListener onScrollListener : onScrolledListeners) {
            onScrollListener.onScrolledReset();
        }
    }
}
