package com.wqzhang.thingswapper.ui;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import com.wqzhang.thingswapper.adapters.ToDoThingsRecyclerAdapter;
import com.wqzhang.thingswapper.events.PullFreshScrollingEvent;
import com.wqzhang.thingswapper.listener.abs.OnScrolledListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import static android.view.accessibility.AccessibilityEvent.INVALID_POSITION;

/**
 * Created by wqzhang on 16-11-30.
 */

public class TodoThingsRecyclerListView extends android.support.v7.widget.RecyclerView {
    private String TAG = "RecyclerListView";

    private Context mContext;
    SlideContentView slideContentView = null;
    private EventBus bus;


    public final static int NOT_ALLOW_PULL = -1;
    public final static int PULL_UP = Integer.parseInt("000001", 2);
    public final static int PULL_UP_COMPLETE = Integer.parseInt("000010", 2);
    public final static int PULL_DOWN = Integer.parseInt("000100", 2);
    public final static int PULL_DOWN_COMPLETE = Integer.parseInt("001000", 2);
    public final static int PULL_DOUBLE = Integer.parseInt("010000", 2);


    private static int scrolledState = NOT_ALLOW_PULL;

//    public final static int RESET = Integer.parseInt("010000", 2);
//    public final static int DO_NOTHING = Integer.parseInt("100000", 2);


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
    boolean isSlideContentView = false;
    boolean allowCheckSlideContent = true;
    int X = 0, Y = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        Log.d(TAG, "Touch " + event.getAction());
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                X = (int) event.getX();
                Y = (int) event.getY();
                lastY = (int) event.getRawY();
                isSlideContentView = false;
                allowCheckSlideContent = true;
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
                            slideContentView = ((ToDoThingsRecyclerAdapter.SlideViewHolder) viewHolder).slide_content_view;
                            slideContentView.onRequeirTouchEvent(event);
                        }
                    }
                } else {
                    slideContentView = null;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (allowCheckSlideContent) {
                    int deltaY = (int) event.getY() - Y;
                    int touchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
                    if (deltaY == 0) deltaY = 1;
                    if (Math.abs(deltaY) > touchSlop) {
                        allowCheckSlideContent = false;
                    } else {
                        if (slideContentView != null) {
                            if (slideContentView.onRequeirTouchEvent(event)) {
                                isSlideContentView = true;
                                allowCheckSlideContent = false;
                            }
                        }
                    }
                    return super.onTouchEvent(event);
                }

                if (isSlideContentView) {
                    slideContentView.onRequeirTouchEvent(event);
                    return super.onTouchEvent(event);
                }

                int scrollValue = (int) (event.getRawY() - lastY);
                if ((scrolledState == PULL_DOWN || scrolledState == PULL_DOWN_COMPLETE || scrolledState == PULL_DOUBLE)) {
                    if (scrollValue <= 0) {
                        //如果向上滑动  则取消此次可下拉拖动的效果
                        OnScrolledReset();
                        bus.post(new PullFreshScrollingEvent(0, 0));
                        if (scrolledState != PULL_DOUBLE) {
                            scrolledState = NOT_ALLOW_PULL;
                        }
                    } else {
                        //可以下滑
                        if (scrollValue <= 200) {
                            bus.post(new PullFreshScrollingEvent(0, -scrollValue));
                        } else if (scrollValue <= 1000) {
                            bus.post(new PullFreshScrollingEvent(0, -scrollValue / 5 - 160));
                        } else {
                            bus.post(new PullFreshScrollingEvent(0, -360));
                        }
                        if (scrollValue > 200) {
                            OnScrolledToDownComplete();
                            scrolledState = PULL_DOWN_COMPLETE;
                        } else {
                            if (scrolledState != PULL_DOUBLE) {
                                scrolledState = PULL_DOWN;
                            }
                            OnScrolledToDown();
                        }
                        return true;
                    }
                }
                if ((scrolledState == PULL_UP || scrolledState == PULL_UP_COMPLETE || scrolledState == PULL_DOUBLE)) {
                    if (scrollValue >= 0) {
                        //如果向下滑动  则取消此次可上拉拖动的效果
                        OnScrolledReset();
                        bus.post(new PullFreshScrollingEvent(0, 0));
                        if (scrolledState != PULL_DOUBLE) {
                            scrolledState = NOT_ALLOW_PULL;
                        }
                    } else {
                        //可以上滑
                        if (scrollValue >= -200) {
                            bus.post(new PullFreshScrollingEvent(0, -scrollValue));
                        } else if (scrollValue >= -1000) {
                            bus.post(new PullFreshScrollingEvent(0, -scrollValue / 5 + 160));
                        } else {
                            bus.post(new PullFreshScrollingEvent(0, 360));
                        }
                        if (scrollValue > -200) {
                            if (scrolledState != PULL_DOUBLE) {
                                scrolledState = PULL_UP;
                            }
                            OnScrolledToUp();
                        } else {
                            OnScrolledToUpComplete();
                            scrolledState = PULL_UP_COMPLETE;
                        }
                        return true;
                    }

                }

                lastY = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_UP:
                if (slideContentView != null) {
                    if (slideContentView.onRequeirTouchEvent(event)) {
                        isSlideContentView = true;
                    }
                }
                if (isSlideContentView) {
                    return super.onTouchEvent(event);
                }


                if (scrolledState == PULL_UP_COMPLETE) {
                    Log.d(TAG, "切换至下一页");
                    bus.post(new PullFreshScrollingEvent(PullFreshScrollingEvent.TYPE_CHANGE_VIEW));
                } else if (scrolledState == PULL_DOWN_COMPLETE) {
                    Intent intent = new Intent("com.wqzhang.thingswapper.activity.AddToDoThingActivity");
                    mContext.startActivity(intent);
                    bus.post(new PullFreshScrollingEvent(0, 0, 0, 0));
                } else {
                    bus.post(new PullFreshScrollingEvent(0, 0, 0, 0));
                }
                OnScrolledReset();
            default:
                break;
        }

        return super.onTouchEvent(event);

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

    public static void setScrolledState(int scrolledState) {
        TodoThingsRecyclerListView.scrolledState = scrolledState;
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


    private void scrolledStateChange(int newScrolledState) {
        oldScrolledState = scrolledState;
        scrolledState = newScrolledState;
    }

    private void scrolledStateRollBack() {
        scrolledState = oldScrolledState;
    }

    static int oldScrolledState;
}
