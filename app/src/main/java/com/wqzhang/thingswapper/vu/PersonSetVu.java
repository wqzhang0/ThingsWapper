package com.wqzhang.thingswapper.vu;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.wqzhang.thingswapper.R;

/**
 * Created by wqzhang on 17-1-13.
 */

public class PersonSetVu implements Vu {
    private final String TAG = "PersonSetVu";
    View view;
    RelativeLayout recyclerView;


    @Override
    public void init(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.persion_setting_layout, container, false);

        recyclerView = (RelativeLayout) view.findViewById(R.id.user_info_tittle);
    }

    @Override
    public View getView() {
        return view;
    }

    public RelativeLayout getRecyclerView() {
        return recyclerView;
    }
}
