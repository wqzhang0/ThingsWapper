package com.wqzhang.thingswapper.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wqzhang.thingswapper.R;
import com.wqzhang.thingswapper.vus.PoolVu;

/**
 * Created by wqzhang on 16-12-29.
 */

public class PoolFragment extends BasePartenerFragment<PoolVu> {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.pool_show_main_layout, container, false);
        return view;
    }

    @Override
    protected Class<PoolVu> getVuClass() {
        return PoolVu.class;
    }
}
