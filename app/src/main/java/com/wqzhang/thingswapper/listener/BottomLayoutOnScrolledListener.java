package com.wqzhang.thingswapper.listener;

import android.widget.LinearLayout;

import com.wqzhang.thingswapper.listener.abs.OnScrolledListener;

/**
 * Created by wqzhang on 16-12-29.
 */

public class BottomLayoutOnScrolledListener extends OnScrolledListener {
    LinearLayout layout;

    public BottomLayoutOnScrolledListener(LinearLayout layout) {
        this.layout = layout;
    }

    @Override
    public void onSrolledToUp() {
        super.onSrolledToUp();
    }

    @Override
    public void onSrolledToUpComplete() {
        super.onSrolledToUpComplete();
    }

    @Override
    public void onSrolledToDown() {
        super.onSrolledToDown();
    }

    @Override
    public void onSrolledToDownComplete() {
        super.onSrolledToDownComplete();
    }

    @Override
    public void reset() {
        super.reset();
    }
}
