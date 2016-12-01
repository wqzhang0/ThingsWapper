package com.wqzhang.thingswapper;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.wqzhang.thingswapper.UI.DecorationTest;
import com.wqzhang.thingswapper.UI.RecyclerAdapter;


public class MainActivity extends Activity implements AdapterView.OnItemClickListener {

    private static RecyclerView recyclerView;
    private String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.to_do_list_view);
        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(this);
//        线性布局
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

//        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

//        表格布局 可以自定义 表格行数 水平/垂直 布局  reverseLayout 决定从左到右还是从右至左
//        GridLayoutManager  gridLayoutManager = new GridLayoutManager(this,3);
//        gridLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//        gridLayoutManager.setReverseLayout(true);

//        瀑布布局
//        StaggeredGridLayoutManager staggeredGridLayoutManager
//                = new StaggeredGridLayoutManager(4, RecyclerView.VERTICAL);


        DecorationTest decorationTest = new DecorationTest(this);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
//        recyclerView.addItemDecoration(decorationTest);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(recyclerAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Log.d(TAG, "onItemClick");
    }



}
