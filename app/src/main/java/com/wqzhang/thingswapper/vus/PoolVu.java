package com.wqzhang.thingswapper.vus;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wqzhang.thingswapper.R;
import com.wqzhang.thingswapper.adapters.ChartsRecyclerAdapter;
import com.wqzhang.thingswapper.model.ChartDataModel;

import java.util.ArrayList;

/**
 * Created by wqzhang on 17-1-13.
 */

public class PoolVu implements Vu {
    private final String TAG = "PoolVu";
    View view;
    RecyclerView recyclerView;


    @Override
    public void init(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.pool_show_main_layout, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.charts_recyclerview);

    }

    @Override
    public View getView() {
        return view;
    }

    public void setToDayResultData(ArrayList<ChartDataModel> toDayResultData) {
        ((ChartsRecyclerAdapter) recyclerView.getAdapter()).setToDayResultData(toDayResultData);
    }

    public void setWeekNewThings(ArrayList<ChartDataModel> weekNewThings) {
        ((ChartsRecyclerAdapter) recyclerView.getAdapter()).setWeekNewThings(weekNewThings);
    }

    public void setWeekFinshThings(ArrayList<ChartDataModel> weekFinshThings) {
        ((ChartsRecyclerAdapter) recyclerView.getAdapter()).setWeekFinshThings(weekFinshThings);
    }



    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

}
