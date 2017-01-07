package com.wqzhang.thingswapper.vus;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by wqzhang on 17-1-5.
 */

public interface AdapterVu {
    void init(LayoutInflater inflater, ViewGroup container, int viewType);

    View getView();
}
