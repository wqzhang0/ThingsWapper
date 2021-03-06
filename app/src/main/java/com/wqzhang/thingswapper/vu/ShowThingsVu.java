package com.wqzhang.thingswapper.vu;

import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Scroller;

import com.wqzhang.thingswapper.R;
import com.wqzhang.thingswapper.adapter.ToDoThingsRecyclerAdapter;
import com.wqzhang.thingswapper.dao.BusinessProcessImpl;
import com.wqzhang.thingswapper.event.PullFreshScrollingEvent;
import com.wqzhang.thingswapper.ui.SlidePullLinearLayout;
import com.wqzhang.thingswapper.ui.TodoThingsRecyclerListView;

/**
 * Created by wqzhang on 17-1-5.
 */

public class ShowThingsVu implements Vu {

    private TodoThingsRecyclerListView recyclerView;
    private LinearLayoutManager mLayoutManager;
    private LinearLayout topLinearLayout, bottomLinearLayout;
    Scroller mScroller;
    View view;

    @Override
    public void init(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.show_all_reminders_fragment_main, container, false);

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


    public void onScrolling(PullFreshScrollingEvent event) {
        switch (event.getType()) {
            case PullFreshScrollingEvent.TYPE_SCROLL_TO:
//                Log.d("Slide ListView", "TYPE_SCROLL_TO");
                view.scrollTo(event.getEndX(), event.getEndY());
                break;
            case PullFreshScrollingEvent.TYPE_START_SCROLL:
                mScroller.startScroll(event.getStartX(), event.getStartY(), event.getEndX(), event.getEndY());
                break;
            case PullFreshScrollingEvent.TYPE_START_SCROLL_WITH_DURATION:
                mScroller.startScroll(event.getStartX(), event.getStartY(), event.getEndX(), event.getEndY(), event.getDuration());
                break;
            case PullFreshScrollingEvent.TYPE_CHANGE_VIEW:
//                Log.d("Slide ListView", "TYPE_CHANGE_VIEW");
                mScroller.startScroll(0, -300, 0, 300, 600);
                int type = (int) recyclerView.getTag(R.id.showThingType);
                if (type == 0) {
                    //当前显示的是已完成界面,需要切换至未完成界面
                    ((ToDoThingsRecyclerAdapter) recyclerView.getAdapter()).setData(BusinessProcessImpl.getInstance().listOnlineUserNotDoneThingsOrderByCreateTimeDescWithReminderTime());
                    recyclerView.setTag(R.id.showThingType, 1);
                } else {
                    //已完成
                    ((ToDoThingsRecyclerAdapter) recyclerView.getAdapter()).setData(BusinessProcessImpl.getInstance().listOnlineUserFinshThingsOrderByFinshTimeDescWithReminderTime());
                    recyclerView.setTag(R.id.showThingType, 0);
                }
                recyclerView.getAdapter().notifyDataSetChanged();
                //定位到第一条, 防止滑动过快 导致切换后继续滑动
                recyclerView.smoothScrollToPosition(0);

//                linearLayout.scrollToPosition(0);
                break;
            default:
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
