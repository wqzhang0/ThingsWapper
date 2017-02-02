package com.wqzhang.thingswapper.events;

/**
 * Created by wqzhang on 17-1-30.
 */

public class DataCacheChange {
    public final static int TYPE_CONTEXT_CHANGE = 1;
    public final static int TYPE_NOTIFY_CHANGE = 2;
    public final static int TYPE_NOTYFLY_COUNTS_CHANGE = 3;
    public final static int TYPE_NOTYFLY_DATE_CHANGE = 4;
    public final static int TYPE_REMOVE_NOTIFY_DATE_CHANGE = 5;
    //是否提醒  根据时间选择开关进行判断
    public final static int TYPE_IS_REMINDER = 6;

    //是否重复  根据重复选择开关 进行判断
    public final static int TYPE_IS_REPEAT = 7;

    //取消事件
    public final static int TYPE_CANCEL = 8;

    //取消事件
    public final static int TYPE_NOTIFLY_DATE_CANCEL = 9;
    //取消事件
    public final static int TYPE_NOTIFLY_COUNTS_CANCEL = 10;


    private boolean isDetermine;

    private int type;

    private String notifityCounts;


    public DataCacheChange(int type) {
        this.type = type;
    }

    public DataCacheChange(int type, boolean determine) {
        this.type = type;
        this.isDetermine = determine;
    }

    public DataCacheChange(int type, String notifityCounts) {
        this.type = type;
        this.notifityCounts = notifityCounts;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isDetermine() {
        return isDetermine;
    }

    public void setDetermine(boolean determine) {
        isDetermine = determine;
    }

    public String getNotifityCounts() {
        return notifityCounts;
    }

    public void setNotifityCounts(String notifityCounts) {
        this.notifityCounts = notifityCounts;
    }
}
