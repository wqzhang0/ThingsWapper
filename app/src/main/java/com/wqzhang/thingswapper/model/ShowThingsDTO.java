package com.wqzhang.thingswapper.model;

import com.wqzhang.thingswapper.dao.greendao.ToDoThing;

import java.util.Date;

/**
 * Created by wqzhang on 17-3-2.
 */

public class ShowThingsDTO {
    private ToDoThing toDoThing;
    private int notifyType;
    private boolean alreayNotify;
    private Date recentReminderDate;

    public ShowThingsDTO(ToDoThing toDoThing, int notifyType, boolean alreayNotify, Date recentReminderDate) {
        this.toDoThing = toDoThing;
        this.notifyType = notifyType;
        this.alreayNotify = alreayNotify;
        this.recentReminderDate = recentReminderDate;
    }

    public ToDoThing getToDoThing() {
        return toDoThing;
    }

    public void setToDoThing(ToDoThing toDoThing) {
        this.toDoThing = toDoThing;
    }

    public int getNotifyType() {
        return notifyType;
    }

    public void setNotifyType(int notifyType) {
        this.notifyType = notifyType;
    }

    public boolean isAlreayNotify() {
        return alreayNotify;
    }

    public void setAlreayNotify(boolean alreayNotify) {
        this.alreayNotify = alreayNotify;
    }

    public Date getRecentReminderDate() {
        return recentReminderDate;
    }

    public void setRecentReminderDate(Date recentReminderDate) {
        this.recentReminderDate = recentReminderDate;
    }
}
