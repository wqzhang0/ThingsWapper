package com.wqzhang.thingswapper.vu;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wqzhang.thingswapper.R;
import com.wqzhang.thingswapper.adapter.ChartsRecyclerAdapter;
import com.wqzhang.thingswapper.model.ChartDataDTO;

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

    public void setToDayResultData(ArrayList<ChartDataDTO> toDayResultData) {
        ((ChartsRecyclerAdapter) recyclerView.getAdapter()).setToDayResultData(toDayResultData);
    }

    public void setWeekNewThings(ArrayList<ChartDataDTO> weekNewThings) {
        ((ChartsRecyclerAdapter) recyclerView.getAdapter()).setWeekNewThings(weekNewThings);
    }

    public void setWeekFinshThings(ArrayList<ChartDataDTO> weekFinshThings) {
        ((ChartsRecyclerAdapter) recyclerView.getAdapter()).setWeekFinshThings(weekFinshThings);
    }



    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

}
