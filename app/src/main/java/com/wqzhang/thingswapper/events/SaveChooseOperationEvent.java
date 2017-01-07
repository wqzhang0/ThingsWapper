package com.wqzhang.thingswapper.events;

import java.util.Date;

/**
 * Created by wqzhang on 17-1-5.
 */

public class SaveChooseOperationEvent {
    public final static int TYPE_SAVE_CONTEXT = 1;
    public final static int TYPE_SAVE_NOTIFY_TYPE = 2;
    public final static int TYPE_SAVE_NOTYFLY_COUNTS = 3;
    public final static int TYPE_SAVE_NOTYFLY_DATE = 4;

    private int type;
    private Date date;
    private String notifityCounts;
    private String content;
    private int notifyType;

    private boolean isDetermine;

    private SaveChooseOperationEvent() {
    }

    public SaveChooseOperationEvent(int type, int notifyType) {
        this.type = type;
        this.notifyType = notifyType;
    }

    public SaveChooseOperationEvent(int type, String notifityCounts, boolean isDetermine) {
        this.type = type;
        this.notifityCounts = notifityCounts;
        this.isDetermine = isDetermine;
    }

    public SaveChooseOperationEvent(int type, Date date, boolean isDetermine) {
        this.type = type;
        this.date = date;
        this.isDetermine = isDetermine;
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

    public boolean isDetermine() {
        return isDetermine;
    }
}
