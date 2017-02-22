package com.wqzhang.thingswapper.vu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by wqzhang on 17-1-5.
 */

public interface Vu {
    void init(LayoutInflater inflater, ViewGroup container);
    View getView();
}
