package com.wqzhang.thingswapper.model;

import java.util.Date;

/**
 * Created by wqzhang on 16-12-19.
 * 用户个性化设置
 */

public class PersonalizedSetting {
    //震动1
    public final int REMINDER_TYPE_SHOCK = 1;
    //铃声2
    public final int REMINDER_TYPE_RING = 2;
    //邮件4
    public final int REMINDER_TYPE_EMAIL = 4;
    public final int REMINDER_TYPE_DEFAULT = REMINDER_TYPE_SHOCK & REMINDER_TYPE_RING;

    private int id;
    private int userId;
    //提醒类型
    private int reminderType = REMINDER_TYPE_DEFAULT;
    //提醒时间
    private Date reminderTime;
    private boolean isSynchronize;
    private boolean isDefault;


    public int getReminderType() {
        return reminderType;
    }

    public void setReminderType(int reminderType) {
        this.reminderType = reminderType;
    }

    public Date getReminderTime() {
        return reminderTime;
    }

    public void setReminderTime(Date reminderTime) {
        this.reminderTime = reminderTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public boolean isSynchronize() {
        return isSynchronize;
    }

    public void setSynchronize(boolean synchronize) {
        isSynchronize = synchronize;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }
}
