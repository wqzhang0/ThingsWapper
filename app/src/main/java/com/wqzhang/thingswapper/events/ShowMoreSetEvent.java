package com.wqzhang.thingswapper.events;

import com.wqzhang.thingswapper.exceptions.CustomerException;

/**
 * Created by wqzhang on 17-1-5.
 */

public class ShowMoreSetEvent {
    //隐藏,时间选择,提醒次数选择
    public final static int HIDE = 1;
    public final static int SHOW_DATE = 2;
    public final static int SHOW_COUNT = 3;
    private int showType;

    private ShowMoreSetEvent() {
    }

    public ShowMoreSetEvent(int type) {
        if (type == HIDE) {
            this.showType = HIDE;
        } else if (type == SHOW_DATE) {
            this.showType = SHOW_DATE;
        } else if (type == SHOW_COUNT) {
            this.showType = SHOW_COUNT;
        }
    }

    public int getShowType() {
        return showType;
    }
}
