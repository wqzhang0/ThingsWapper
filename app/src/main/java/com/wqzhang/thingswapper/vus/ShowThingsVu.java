package com.wqzhang.thingswapper.vus;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Scroller;

import com.wqzhang.thingswapper.R;
import com.wqzhang.thingswapper.adapters.ToDoThingsRecyclerAdapter;
import com.wqzhang.thingswapper.dao.BusinessProcess;
import com.wqzhang.thingswapper.dao.greendao.ToDoThing;
import com.wqzhang.thingswapper.events.PullFreshScrollingEvent;
import com.wqzhang.thingswapper.listener.BottomLayoutOnScrolledListener;
import com.wqzhang.thingswapper.listener.TopLayoutOnScrolledListener;
import com.wqzhang.thingswapper.listener.impl.OnScrollingListenerImpl;
import com.wqzhang.thingswapper.ui.SlidePullLinearLayout;
import com.wqzhang.thingswapper.ui.TodoThingsRecyclerListView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

/**
 * Created by wqzhang on 17-1-5.
 */

public class ShowThingsVu implements Vu {

    private TodoThingsRecyclerListView recyclerView;
    private LinearLayoutManager mLayoutManager;
    private LinearLayout topLinearLayout, bottomLinearLayout;
    Scroller mScroller;
    View view;
    private EventBus bus;

    @Override
    public void init(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.show_all_reminders_fragment_main, container, false);
        bus = EventBus.getDefault();
        bus.register(this);

//        线性布局
        mLayoutManager = new LinearLayoutManager(inflater.getContext());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView = (TodoThingsRecyclerListView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(mLayoutManager);


        mScroller = new Scroller(view.getContext());
        ((SlidePullLinearLayout) view).setScroller(mScroller);

        topLinearLayout = (LinearLayout) view.findViewById(R.id.topLinearLayout);
        bottomLinearLayout = (LinearLayout) view.findViewById(R.id.bottomLinearLayout);
    }

    @Override
    public View getView() {
        return view;
    }


    @Subscribe
    public void onScrolling(PullFreshScrollingEvent event) {
        switch (event.getType()) {
            case PullFreshScrollingEvent.TYPE_SCROLL_TO:
                view.scrollTo(event.getEndX(), event.getEndY());
                break;
            case PullFreshScrollingEvent.TYPE_START_SCROLL:
                mScroller.startScroll(event.getStartX(), event.getStartY(), event.getEndX(), event.getEndY());
                break;
            case PullFreshScrollingEvent.TYPE_START_SCROLL_WITH_DURATION:
                mScroller.startScroll(event.getStartX(), event.getStartY(), event.getEndX(), event.getEndY(), event.getDuration());
                break;
            case PullFreshScrollingEvent.TYPE_CHANGE_VIEW:
                mScroller.startScroll(0, -300, 0, 300, 600);
                break;

        }
        view.postInvalidate();

    }

    public TodoThingsRecyclerListView getRecyclerView() {
        return recyclerView;
    }

    public LinearLayoutManager getmLayoutManager() {
        return mLayoutManager;
    }

    public LinearLayout getTopLinearLayout() {
        return topLinearLayout;
    }

    public LinearLayout getBottomLinearLayout() {
        return bottomLinearLayout;
    }

    public Scroller getmScroller() {
        return mScroller;
    }
}