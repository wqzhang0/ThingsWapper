package com.wqzhang.thingswapper.listener.impl;

/**
 * Created by wqzhang on 16-12-30.
 */

public interface ShowMoreSet {
    enum ShowType {
        //隐藏,时间选择,提醒次数选择
        HIDE, SHOW_DATE, SHOW_COUNT
    }

    void showMoreSetFrameLayout(ShowType showType);
}
