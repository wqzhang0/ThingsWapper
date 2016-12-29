package com.wqzhang.thingswapper.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Scroller;

import com.wqzhang.thingswapper.R;
import com.wqzhang.thingswapper.adapter.ToDoThingsRecyclerAdapter;
import com.wqzhang.thingswapper.dao.BusinessProcess;
import com.wqzhang.thingswapper.dao.greendao.ToDoThing;
import com.wqzhang.thingswapper.listener.BottomLayoutOnScrolledListener;
import com.wqzhang.thingswapper.listener.TopLayoutOnScrolledListener;
import com.wqzhang.thingswapper.listener.impl.OnScrollingListenerImpl;
import com.wqzhang.thingswapper.ui.SlidePullLinearLayout;
import com.wqzhang.thingswapper.ui.TodoThingsRecyclerListView;

import java.util.ArrayList;

/**
 * Created by wqzhang on 16-12-1.
 */

public class ToDoFragment extends Fragment implements OnScrollingListenerImpl {
    private String TAG = "ToDoFragment";
    private static TodoThingsRecyclerListView recyclerView;
    private LinearLayoutManager mLayoutManager;
    Scroller mScroller;
    View view;


    Handler handler;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.to_do_fragment, container, false);

        ArrayList<ToDoThing> toDoThings = BusinessProcess.getInstance().readAllThings();
        ToDoThingsRecyclerAdapter toDoThingsRecyclerAdapter = new ToDoThingsRecyclerAdapter(getActivity());
        toDoThingsRecyclerAdapter.setData(toDoThings);
//        线性布局
        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);


//        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

//        表格布局 可以自定义 表格行数 水平/垂直 布局  reverseLayout 决定从左到右还是从右至左
//        GridLayoutManager  gridLayoutManager = new GridLayoutManager(this,3);
//        gridLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//        gridLayoutManager.setReverseLayout(true);

//        瀑布布局
//        StaggeredGridLayoutManager staggeredGridLayoutManager
//                = new StaggeredGridLayoutManager(4, RecyclerView.VERTICAL);


//        DecorationTest decorationTest = new DecorationTest(getActivity());
//        View mHeaderView = inflater.inflate(R.layout.header_footer_view, container, false);
//        TextView mHeaderTittle = (TextView) mHeaderView.findViewById(R.id.tittle);
//        mHeaderTittle.setText("下拉添加");
//
//        View mFooterView = inflater.inflate(R.layout.header_footer_view, container, false);
//        TextView mFooterTittle = (TextView) mHeaderView.findViewById(R.id.tittle);
//        mFooterTittle.setText("上拉更换视图");
//
//        recyclerAdapter.setHeaderView(mHeaderView);
//
//        recyclerAdapter.setFooterView(mFooterView);

//        swipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefresh);

        recyclerView = (TodoThingsRecyclerListView) view.findViewById(R.id.recyclerView);
//        bottomImageView = (ImageView) view.findViewById(R.id.bottomImageView);
//        recyclerView.setBottomImageView(bottomImageView);
//        topImageView = (ImageView) view.findViewById(R.id.topImageView);
//        recyclerView.setTopImageView(topImageView);
//        recyclerView.addItemDecoration(decorationTest);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(toDoThingsRecyclerAdapter);
//        recyclerView.scrollToPosition(1);

        toDoThingsRecyclerAdapter.setAllowPullStateListenerImpl(recyclerView);

//        int touchSlop= ViewConfiguration.get(getActivity()).getScaledTouchSlop();


        mScroller = new Scroller(view.getContext());
        ((SlidePullLinearLayout) view).setScroller(mScroller);
//        recyclerView.postInvalidate();

//        view.setTranslationY(100);


//        return super.onCreateView(inflater, container, savedInstanceState);
//        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                swipeRefresh.setRefreshing(false);
//            }
//        });


//        view.scrollTo(0,0-300);
        // binderOnScrolledListeners

        LinearLayout topLinearLayout = (LinearLayout) view.findViewById(R.id.topLinearLayout);
        LinearLayout bottomLinearLayout = (LinearLayout) view.findViewById(R.id.bottomLinearLayout);
        TopLayoutOnScrolledListener topLayoutOnScrolledListener = new TopLayoutOnScrolledListener(topLinearLayout);
        BottomLayoutOnScrolledListener bottomLayoutOnScrolledListener = new BottomLayoutOnScrolledListener(bottomLinearLayout);


        recyclerView.addOnScrolledListener(topLayoutOnScrolledListener);
        recyclerView.addOnScrolledListener(bottomLayoutOnScrolledListener);

        recyclerView.setOnScrollingListener(this);
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    default:
                        mScroller.startScroll(0, 0, 0, 500, 3000);
                        view.postInvalidate();
                }
            }
        };
        return view;
    }


    @Override
    public void scrollTo(int x, int y) {
        view.scrollTo(x, y);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy) {

    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
//        mScroller.startScroll(0,-600,0,600,800);
//        view.setAlpha((float) 0.4);
//        mScroller.startScroll(0,-1400,0,1400,800);
//        view.scrollBy(0,1000);
        mScroller.startScroll(startX, view.getScrollY(), dx, 0 - view.getScrollY(), 800);
        view.postInvalidate();
    }

    @Override
    public void onAttach(Activity activity) {
        //当Fragment与Activity发生关联时调用
        Log.d(TAG, "onAttach   activity");
        super.onAttach(activity);
    }
}
