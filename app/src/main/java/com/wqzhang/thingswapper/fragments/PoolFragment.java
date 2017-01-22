package com.wqzhang.thingswapper.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.wqzhang.thingswapper.R;
import com.wqzhang.thingswapper.adapters.ChartsRecyclerAdapter;
import com.wqzhang.thingswapper.dao.BusinessProcess;
import com.wqzhang.thingswapper.model.ChartDataModel;
import com.wqzhang.thingswapper.vus.PoolVu;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by wqzhang on 16-12-29.
 */

public class PoolFragment extends BasePartenerFragment<PoolVu> {


    @Override
    protected Class<PoolVu> getVuClass() {
        return PoolVu.class;
    }


    public static PoolFragment newInstance() {
        return new PoolFragment();
    }

    @Override
    protected void onBind() {
//        vu.setTodayLineChartDate();
//        vu.setTodayPieChartDate();
        ChartsRecyclerAdapter chartsRecyclerAdapter = new ChartsRecyclerAdapter(getActivity());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        vu.getRecyclerView().setLayoutManager(layoutManager);

        vu.getRecyclerView().setAdapter(chartsRecyclerAdapter);

        vu.setToDayResultData(BusinessProcess.getInstance().readTodayThings());
        vu.setWeekNewThings(BusinessProcess.getInstance().readRecentWeekNewThings());
        vu.setWeekFinshThings(BusinessProcess.getInstance().readRecentWeekFinshThings());
        super.onBind();
    }
}
