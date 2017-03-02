package com.wqzhang.thingswapper.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wqzhang.thingswapper.R;
import com.wqzhang.thingswapper.adapter.ToDoThingsRecyclerAdapter;
import com.wqzhang.thingswapper.dao.BusinessProcess;
import com.wqzhang.thingswapper.dao.greendao.ToDoThing;
import com.wqzhang.thingswapper.event.PullFreshScrollingEvent;
import com.wqzhang.thingswapper.listener.BottomLayoutOnScrolledListener;
import com.wqzhang.thingswapper.listener.TopLayoutOnScrolledListener;
import com.wqzhang.thingswapper.ui.CustomerItemDecoration;
import com.wqzhang.thingswapper.ui.TodoThingsRecyclerListView;
import com.wqzhang.thingswapper.vu.ShowThingsVu;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import static android.widget.AbsListView.OnScrollListener.SCROLL_STATE_IDLE;

/**
 * Created by wqzhang on 16-12-1.
 */

public class ShowThingsFragment extends BasePartenerFragment<ShowThingsVu> {
    private String TAG = "ShowThingsFragment";

    private RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            //在这里进行是否滑动至底部或者滑动至顶部的逻辑判断
            //在 结束后进行判断  而不是在滑动过程过程中进行判断

            int lastPosition = -1;
            int firstPosition = -1;
            boolean canPullUp = false;
            boolean canPullDown = false;
            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();

            //firstChildView getTop 为0   但是recyclerView.getTop() 会根据不同尺寸手机 多出不同像素
//                Log.d("onScrollStateChanged", "onScrollStateChanged" + newState);
//            if (newState == SCROLL_STATE_TOUCH_SCROLL) {
//                lastPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
//                View lastChildView = layoutManager.getChildAt(layoutManager.getChildCount() - 1);
////e
//                int recyclerBottom = (int) (recyclerView.getBottom() - recyclerView.getPaddingBottom() - recyclerView.getTranslationY()) - recyclerView.getTop();
//                int lastChildViewBottom = lastChildView.getBottom();
//
//                //空数据时 会有一个空的Item页面 填充recyclerView  大小相等,
//
//                //为了解决默认可以上拉
////                if (lastChildViewBottom == recyclerBottom || lastPosition == recyclerView.getAdapter().getItemCount() - 1) {
////                    TodoThingsRecyclerListView.setScrolledState(TodoThingsRecyclerListView.PULL_UP);
////                }
//
//                //为了解决 当item 不能铺满整个RecyclerView 时  也允许上下拖动
//                if (lastChildViewBottom < recyclerBottom || lastPosition == recyclerView.getAdapter().getItemCount() - 1) {
//                    TodoThingsRecyclerListView.setScrolledState(TodoThingsRecyclerListView.PULL_DOUBLE);
//                }
//            }
            if (newState == SCROLL_STATE_IDLE) {
                //不滑动时
                if (layoutManager instanceof LinearLayoutManager) {
                    lastPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                    firstPosition = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
//                        View firstChildView = layoutManager.getChildAt(firstPosition);
                    RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(firstPosition);
                    if (viewHolder != null) {
                        View firstChildView = viewHolder.itemView;
                        int firstChildViewTop = firstChildView.getTop();
                        if (firstChildViewTop == 0 && firstPosition == 0) {
                            //顶部
                            canPullUp = true;
                            TodoThingsRecyclerListView.setScrolledState(TodoThingsRecyclerListView.PULL_DOWN);
                        }
                        View lastChildView = layoutManager.getChildAt(layoutManager.getChildCount() - 1);
//                        View lastChildView = recyclerView.findViewHolderForAdapterPosition(layoutManager.getChildCount() - 1).itemView;
                        int recyclerBottom = (int) (recyclerView.getBottom() - recyclerView.getPaddingBottom() - recyclerView.getTranslationY()) - recyclerView.getTop();
                        int lastChildViewBottom = lastChildView.getBottom();

                        if ((lastChildViewBottom == recyclerBottom || lastPosition == recyclerView.getAdapter().getItemCount() - 1) && (lastPosition == (layoutManager.getItemCount() - 1))) {
//                            Toast.makeText(getActivity(), "到达尾点", Toast.LENGTH_SHORT).show();
                            canPullDown = true;
                            TodoThingsRecyclerListView.setScrolledState(TodoThingsRecyclerListView.PULL_UP);
                        }
                        if (canPullUp && canPullDown) {
                            TodoThingsRecyclerListView.setScrolledState(TodoThingsRecyclerListView.PULL_DOUBLE);
                        }
                        if (!canPullUp && !canPullDown) {
                            TodoThingsRecyclerListView.setScrolledState(TodoThingsRecyclerListView.NOT_ALLOW_PULL);
                        }
                    }

                }
            }
        }
    };

//        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

//        表格布局 可以自定义 表格行数 水平/垂直 布局  reverseLayout 决定从左到右还是从右至左
//        GridLayoutManager  gridLayoutManager = new GridLayoutManager(this,3);
//        gridLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//        gridLayoutManager.setReverseLayout(true);

//        瀑布布局
//        StaggeredGridLayoutManager staggeredGridLayoutManager
//                = new StaggeredGridLayoutManager(4, RecyclerView.VERTICAL);


    @Override
    protected void onBind() {
        super.onBind();
        ToDoThingsRecyclerAdapter toDoThingsRecyclerAdapter = new ToDoThingsRecyclerAdapter(getActivity());

        bus.register(this);
        ArrayList<ToDoThing> toDoThings = BusinessProcess.getInstance().listNotDoneThingsOrderByCreateTimeDesc();
        toDoThingsRecyclerAdapter.setData(toDoThings);
        vu.getRecyclerView().setTag(R.id.showThingType, 1);
        vu.getRecyclerView().setAdapter(toDoThingsRecyclerAdapter);
//        vu.getRecyclerView().addItemDecoration(new CustomerItemDecoration());

        //默认可以上拉
        TodoThingsRecyclerListView.setScrolledState(TodoThingsRecyclerListView.PULL_DOWN);
        int lastPosition = ((LinearLayoutManager) vu.getRecyclerView().getLayoutManager()).findLastVisibleItemPosition();
        if (lastPosition == vu.getRecyclerView().getAdapter().getItemCount() - 1) {
            TodoThingsRecyclerListView.setScrolledState(TodoThingsRecyclerListView.PULL_DOUBLE);
        }
//        if (!ViewCompat.canScrollVertically(vu.getRecyclerView(), 1)) {
//            TodoThingsRecyclerListView.setScrolledState(TodoThingsRecyclerListView.PULL_DOUBLE);
//        }
        vu.getRecyclerView().addOnScrollListener(onScrollListener);

        TopLayoutOnScrolledListener topLayoutOnScrolledListener = new TopLayoutOnScrolledListener(vu.getTopLinearLayout());
        BottomLayoutOnScrolledListener bottomLayoutOnScrolledListener = new BottomLayoutOnScrolledListener(vu.getBottomLinearLayout());

        vu.getRecyclerView().addOnScrolledListener(topLayoutOnScrolledListener);
        vu.getRecyclerView().addOnScrolledListener(bottomLayoutOnScrolledListener);

    }


    @Override
    protected Class<ShowThingsVu> getVuClass() {
        return ShowThingsVu.class;
    }

    public static ShowThingsFragment newInstance() {
        return new ShowThingsFragment();
    }

    @Subscribe
    public void onScrolling(PullFreshScrollingEvent event) {
        vu.onScrolling(event);
    }

    @Override
    protected void afterOnResume() {
        super.afterOnResume();
        ArrayList<ToDoThing> toDoThings = BusinessProcess.getInstance().listNotDoneThingsOrderByCreateTimeDesc();
        ToDoThingsRecyclerAdapter adapter = (ToDoThingsRecyclerAdapter) vu.getRecyclerView().getAdapter();
        adapter.setData(toDoThings);

    }

    @Override
    protected void afterOnStart() {
        super.afterOnStart();
    }

    @Override
    protected void beforeOnStop() {
        super.beforeOnStop();
//        bus.unregister(this);
    }

    @Override
    protected void beforOnDestory() {
        super.beforOnDestory();
        bus.unregister(this);
        vu.getRecyclerView().removeOnScrollListener(onScrollListener);

    }
}
