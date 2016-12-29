package com.wqzhang.thingswapper.listener.impl;

/**
 * Created by wqzhang on 16-12-29.
 */

public interface OnScrollingListenerImpl {

    void scrollTo(int x, int y);

    void startScroll(int startX, int startY, int dx, int dy);

    void startScroll(int startX, int startY, int dx, int dy, int duration);

}
