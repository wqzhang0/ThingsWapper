package com.wqzhang.thingswapper.fragments;

import com.wqzhang.thingswapper.adapters.ToDoThingsRecyclerAdapter;
import com.wqzhang.thingswapper.dao.BusinessProcess;
import com.wqzhang.thingswapper.dao.greendao.ToDoThing;
import com.wqzhang.thingswapper.listener.BottomLayoutOnScrolledListener;
import com.wqzhang.thingswapper.listener.TopLayoutOnScrolledListener;
import com.wqzhang.thingswapper.vus.ShowThingsVu;

import java.util.ArrayList;

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
    protected void onBind() {
        super.onBind();
        ToDoThingsRecyclerAdapter toDoThingsRecyclerAdapter = new ToDoThingsRecyclerAdapter(getActivity());

        ArrayList<ToDoThing> toDoThings = BusinessProcess.getInstance().readAllThings();
        toDoThingsRecyclerAdapter.setData(toDoThings);
        vu.getRecyclerView().setAdapter(toDoThingsRecyclerAdapter);


        toDoThingsRecyclerAdapter.setAllowPullStateListenerImpl(vu.getRecyclerView());

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
