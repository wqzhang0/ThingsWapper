package com.wqzhang.thingswapper;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wqzhang.thingswapper.UI.DecorationTest;
import com.wqzhang.thingswapper.UI.RecyclerAdapter;

/**
 * Created by wqzhang on 16-12-1.
 */

public class ToDoFragment extends Fragment {
    private String TAG = "ToDoFragment";
    private static RecyclerView recyclerView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.to_do_fragment, container, false);
        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(getActivity());
//        线性布局
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
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
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
//        recyclerView.addItemDecoration(decorationTest);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(recyclerAdapter);
//        return super.onCreateView(inflater, container, savedInstanceState);


        return view;
    }

}
