package com.wqzhang.thingswapper.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wqzhang.thingswapper.R;
import com.wqzhang.thingswapper.db.DatebaseHelper;
import com.wqzhang.thingswapper.model.ToDoThing;
import com.wqzhang.thingswapper.ui.DecorationTest;
import com.wqzhang.thingswapper.adapter.RecyclerAdapter;
import com.wqzhang.thingswapper.ui.RecyclerListView;

import java.util.ArrayList;

/**
 * Created by wqzhang on 16-12-1.
 */

public class ToDoFragment extends Fragment {
    private String TAG = "ToDoFragment";
    private static RecyclerListView recyclerView;
    private LinearLayoutManager mLayoutManager;

    private ImageView bottomImageView ;
    private ImageView topImageView ;
//    private static SwipeRefreshLayout swipeRefresh;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.to_do_fragment, container, false);
        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(getActivity());
        ArrayList<ToDoThing> toDoThings = DatebaseHelper.getInstance().readToBeDoneThings();
        recyclerAdapter.setData(toDoThings);
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


        DecorationTest decorationTest = new DecorationTest(getActivity());
        View mHeaderView = inflater.inflate(R.layout.header_footer_view, container, false);
        TextView mHeaderTittle = (TextView) mHeaderView.findViewById(R.id.tittle);
        mHeaderTittle.setText("下拉添加");

        View mFooterView = inflater.inflate(R.layout.header_footer_view, container, false);
        TextView mFooterTittle = (TextView) mHeaderView.findViewById(R.id.tittle);
        mFooterTittle.setText("上拉更换视图");

        recyclerAdapter.setHeaderView(mHeaderView);

        recyclerAdapter.setFooterView(mFooterView);

//        swipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefresh);

        recyclerView = (RecyclerListView) view.findViewById(R.id.recyclerView);
        bottomImageView = (ImageView) view.findViewById(R.id.bottomImageView);
        recyclerView.setBottomImageView(bottomImageView);
        topImageView = (ImageView) view.findViewById(R.id.topImageView);
        recyclerView.setTopImageView(topImageView);
//        recyclerView.addItemDecoration(decorationTest);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.scrollToPosition(1);

//        view.setTranslationY(100);


//        return super.onCreateView(inflater, container, savedInstanceState);
//        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                swipeRefresh.setRefreshing(false);
//            }
//        });

        return view;
    }



}
