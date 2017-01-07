package com.wqzhang.thingswapper.vus;

import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.wqzhang.thingswapper.R;

/**
 * Created by wqzhang on 17-1-5.
 */

public class MainVu implements Vu {
    private View view;
    private TextView settingTextView, poolTextView, showThingsTextView;
    private Toolbar toolbar;
    private FrameLayout containerView;

    @Override
    public void init(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.activity_main, container, false);
        settingTextView = (TextView) view.findViewById(R.id.setting);
        showThingsTextView = (TextView) view.findViewById(R.id.show_things);
        poolTextView = (TextView) view.findViewById(R.id.pool);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        containerView = (FrameLayout) view.findViewById(R.id.main_content);
    }

    @Override
    public View getView() {
        return view;
    }

    public Toolbar getToolBar() {
        return toolbar;
    }


    public int getContainerId() {
        return containerView.getId();
    }

    public TextView getSettingTextView() {
        return settingTextView;
    }

    public TextView getPoolTextView() {
        return poolTextView;
    }


    public FrameLayout getContainerView() {
        return containerView;
    }

    public TextView getShowThingsTextView() {
        return showThingsTextView;
    }
}
