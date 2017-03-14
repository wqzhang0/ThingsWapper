package com.wqzhang.thingswapper.fragment;

import android.support.v7.widget.LinearLayoutManager;

import com.wqzhang.thingswapper.adapter.ChartsRecyclerAdapter;
import com.wqzhang.thingswapper.dao.BusinessProcessImpl;
import com.wqzhang.thingswapper.vu.PoolVu;

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
        vu.setToDayResultData(BusinessProcessImpl.getInstance().countTodayThings());
        vu.setWeekNewThings(BusinessProcessImpl.getInstance().countRecentWeekNewThings());
        vu.setWeekFinshThings(BusinessProcessImpl.getInstance().countRecentWeekFinshThings());
        super.onBind();
    }

    @Override
    protected void afterOnResume() {
        super.afterOnResume();
    }

    public void resetData(){
        vu.setToDayResultData(BusinessProcessImpl.getInstance().countTodayThings());
        vu.setWeekNewThings(BusinessProcessImpl.getInstance().countRecentWeekNewThings());
        vu.setWeekFinshThings(BusinessProcessImpl.getInstance().countRecentWeekFinshThings());
    }
}
