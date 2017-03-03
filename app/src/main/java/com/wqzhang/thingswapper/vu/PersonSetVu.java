package com.wqzhang.thingswapper.vu;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wqzhang.thingswapper.R;

/**
 * Created by wqzhang on 17-1-13.
 */

public class PersonSetVu implements Vu {
    private final String TAG = "PersonSetVu";
    View view;
    RecyclerView recyclerView;


    @Override
    public void init(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.persion_setting_layout, container, false);

    }

    @Override
    public View getView() {
        return view;
    }


}