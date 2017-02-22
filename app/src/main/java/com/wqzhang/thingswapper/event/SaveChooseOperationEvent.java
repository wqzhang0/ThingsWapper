package com.wqzhang.thingswapper.event;

import java.util.Date;

/**
 * Created by wqzhang on 17-1-5.
 */

public class SaveChooseOperationEvent {
    public final static int TYPE_SAVE_CONTEXT = 1;
    public final static int TYPE_SAVE_NOTIFY_TYPE = 2;
    public final static int TYPE_SAVE_NOTYFLY_COUNTS = 3;
    public final static int TYPE_SAVE_NOTYFLY_DATE = 4;
    public final static int TYPE_REMOVE_NOTIFY_DATE = 5;
    //是否提醒  根据时间选择开关进行判断
    public final static int TYPE_IS_REMINDER = 6;

    //是否重复  根据重复选择开关 进行判断
    public final static int TYPE_IS_REPEAT = 7;

    private int type;
    private Date date;
    private String notifityCounts;
    private String content;
    private int notifyType;
    private boolean determine;

    private SaveChooseOperationEvent() {
    }

    public SaveChooseOperationEvent(int type, int notifyType) {
        this.type = type;
        this.notifyType = notifyType;
    }

    public SaveChooseOperationEvent(int type, String content) {
        this.type = type;
        this.content = content;
    }

    public SaveChooseOperationEvent(int type, String notifityCounts, boolean determine) {
        this.type = type;
        this.notifityCounts = notifityCounts;
        this.determine = determine;
    }

    public SaveChooseOperationEvent(int type, Date date, boolean determine) {
        this.type = type;
        this.date = date;
        this.determine = determine;
    }

    public SaveChooseOperationEvent(int type, boolean determine) {
        this.type = type;
        this.determine = determine;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getNotifityCounts() {
        return notifityCounts;
    }

    public void setNotifityCounts(String notifityCounts) {
        this.notifityCounts = notifityCounts;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getNotifyType() {
        return notifyType;
    }

    public boolean getDetermine() {
        return determine;
    }
}
