package com.wqzhang.thingswapper.fragments;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.wqzhang.thingswapper.adapters.ToDoThingsRecyclerAdapter;
import com.wqzhang.thingswapper.dao.BusinessProcess;
import com.wqzhang.thingswapper.dao.greendao.ToDoThing;
import com.wqzhang.thingswapper.listener.BottomLayoutOnScrolledListener;
import com.wqzhang.thingswapper.listener.TopLayoutOnScrolledListener;
import com.wqzhang.thingswapper.listener.abs.OnScrolledListener;
import com.wqzhang.thingswapper.ui.TodoThingsRecyclerListView;
import com.wqzhang.thingswapper.vus.ShowThingsVu;

import java.util.ArrayList;

import static android.widget.AbsListView.OnScrollListener.SCROLL_STATE_IDLE;

/**
 * Created by wqzhang on 16-12-1.
 */

public class ShowThingsFragment extends BasePartenerFragment<ShowThingsVu> {
    private String TAG = "ShowThingsFragment";


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

//        bottomImageView = (ImageView) view.findViewById(R.id.bottomImageView);
//        recyclerView.setBottomImageView(bottomImageView);
//        topImageView = (ImageView) view.findViewById(R.id.topImageView);
//        recyclerView.setTopImageView(topImageView);
//        recyclerView.addItemDecoration(decorationTest);


//        int touchSlop= ViewConfiguration.get(getActivity()).getScaledTouchSlop();


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


    @Override
    public void onResume() {
        Log.d(TAG,"onResume");
        super.onResume();
    }

    @Override
    public void onStart() {
        Log.d(TAG,"onStart");
        super.onStart();
    }


    @Override
    protected void onBind() {
        super.onBind();
        ToDoThingsRecyclerAdapter toDoThingsRecyclerAdapter = new ToDoThingsRecyclerAdapter(getActivity());

        ArrayList<ToDoThing> toDoThings = BusinessProcess.getInstance().readAllThings();
        toDoThingsRecyclerAdapter.setData(null);
        vu.getRecyclerView().setAdapter(toDoThingsRecyclerAdapter);

        //默认可以上拉
        TodoThingsRecyclerListView.setScrolledState(TodoThingsRecyclerListView.PULL_DOWN);

        vu.getRecyclerView().addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //在这里进行是否滑动至底部或者滑动至顶部的逻辑判断
                //在 结束后进行判断  而不是在滑动过程过程中进行判断

                //firstChildView getTop 为0   但是recyclerView.getTop() 会根据不同尺寸手机 多出不同像素
                Log.d("onScrollStateChanged", "onScrollStateChanged" + newState);
                if (newState == SCROLL_STATE_IDLE) {
                    //不滑动时
                    int lastPosition = -1;
                    int firstPosition = -1;
                    boolean canPullUp = false;
                    boolean canPullDown = false;
                    boolean canDoublePull = false;
                    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                    if (layoutManager instanceof LinearLayoutManager) {
                        lastPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                        firstPosition = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
                        View firstChildView = layoutManager.getChildAt(firstPosition);
                        int firstChildViewTop = firstChildView.getTop();
                        if (firstChildViewTop == 0 && firstPosition == 0) {
                            //顶部
                            canPullUp = true;
                            TodoThingsRecyclerListView.setScrolledState(TodoThingsRecyclerListView.PULL_DOWN);
                        }
                        View lastChildView = layoutManager.getChildAt(layoutManager.getChildCount() - 1);
                        int recyclerBottom = (int) (recyclerView.getBottom() - recyclerView.getPaddingBottom() - recyclerView.getTranslationY()) - recyclerView.getTop();
                        int lastChildViewBottom = lastChildView.getBottom();

                        if (lastChildViewBottom == recyclerBottom && (lastPosition == (layoutManager.getItemCount() - 1))) {
//                            Toast.makeText(getActivity(), "到达尾点", Toast.LENGTH_SHORT).show();
                            canPullDown = true;
                            TodoThingsRecyclerListView.setScrolledState(TodoThingsRecyclerListView.PULL_UP);
                        }
                        if (canPullUp && canPullDown) {
                            canDoublePull = true;
                            TodoThingsRecyclerListView.setScrolledState(TodoThingsRecyclerListView.PULL_DOUBLE);
                        }
                        if (!canPullUp && !canPullDown) {
                            TodoThingsRecyclerListView.setScrolledState(TodoThingsRecyclerListView.NOT_ALLOW_PULL);
                        }
                    }
                }
            }
        });

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

}
