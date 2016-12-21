package com.wqzhang.thingswapper.model;

import android.widget.Toast;

import com.wqzhang.thingswapper.db.DatebaseHelper;
import com.wqzhang.thingswapper.tools.Common;

import java.util.Date;

/**
 * Created by wqzhang on 16-12-19.
 */

public class ToDoThingModel {

    private int id;
    private String reminderContext;
    private Date reminderTime;
    private int reminderType;
    private int Status = Common.STATUS_TO_BE_DONE;
    private boolean isChange = true;

    private int userId;

    public ToDoThingModel() {
        UserModel userModel = DatebaseHelper.getInstance().readUserInfo();
        if (userModel == null) {
            userModel = new UserModel();
        }
        this.userId = userModel.getId();
    }

    public ToDoThingModel(String reminderContext, Date reminderTime, int reminderType,int Status) {
        UserModel userModel = DatebaseHelper.getInstance().readUserInfo();
        if (userModel == null) {
            userModel = new UserModel();
        }
        this.userId = userModel.getId();

        this.reminderContext = reminderContext;
        if (reminderContext == null) {
//            reminderTime = userModel.getPersonalizedSetting().getReminderTime();
        }
        this.reminderTime = reminderTime;
        if (reminderType == 0) {
//            reminderType = userModel.getPersonalizedSetting().getReminderType();
        }
        this.reminderType = reminderType;
        setStatus(Status);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReminderContext() {
        return reminderContext;
    }

    public void setReminderContext(String reminderContext) {
        this.reminderContext = reminderContext;
    }

    public Date getReminderTime() {
        return reminderTime;
    }

    public void setReminderTime(Date reminderTime) {
        this.reminderTime = reminderTime;
    }

    public int getReminderType() {
        return reminderType;
    }

    public void setReminderType(int reminderType) {
        this.reminderType = reminderType;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public boolean isChange() {
        return isChange;
    }

    public void setChange(boolean change) {
        isChange = change;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
