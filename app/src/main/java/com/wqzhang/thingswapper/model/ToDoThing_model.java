package com.wqzhang.thingswapper.model;

import com.wqzhang.thingswapper.db.DatebaseHelper;
import com.wqzhang.thingswapper.util.Common;

import java.util.Date;

/**
 * Created by wqzhang on 16-12-19.
 * id      用户id	内容	            提醒方式	        创建时间	    状态	    是否同步
 * id      userId	reminderContext	reminderType	createDate	Status	isSynchronize
 */

public class ToDoThing_model {

    private int id;
    private int userId;
    private String reminderContext;
    private int reminderType;
    private Date createDate;
    private int Status = Common.STATUS_TO_BE_DONE;
    private boolean isSynchronize = true;


    public ToDoThing_model() {
        User_model user = DatebaseHelper.getInstance().readUserInfo();
        if (user == null) {
            user = new User_model();
        }
        this.userId = user.getId();
        this.createDate = new Date(System.currentTimeMillis());
    }

    public ToDoThing_model(String reminderContext, Date reminderTime, int reminderType, int Status) {
        new ToDoThing_model();

        this.reminderContext = reminderContext;
        if (reminderContext == null) {
//            reminderTime = userModel.getPersonalizedSetting().getReminderTime();
        }
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
        return isSynchronize;
    }

    public void setChange(boolean change) {
        isSynchronize = change;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
