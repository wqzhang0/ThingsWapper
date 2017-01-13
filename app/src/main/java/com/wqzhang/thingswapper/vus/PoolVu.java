package com.wqzhang.thingswapper.vus;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wqzhang.thingswapper.R;

/**
 * Created by wqzhang on 17-1-13.
 */

public class PoolVu implements Vu {
    View view;
    @Override
    public void init(LayoutInflater inflater, ViewGroup container) {

        view = inflater.inflate(R.layout.pool_show_main_layout,container,false);

    }

    @Override
    public View getView() {
        return view;
    }
}
