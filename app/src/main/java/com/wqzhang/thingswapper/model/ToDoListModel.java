package com.wqzhang.thingswapper.model;

import android.widget.Toast;

import com.wqzhang.thingswapper.tools.Common;

import java.util.Date;

/**
 * Created by wqzhang on 16-12-19.
 */

public class ToDoListModel {

    private int id;
    private String reminderContext;
    private Date reminderTime;
    private int reminderType;
    private int Status = Common.STATUS_TO_BE_DONE;

    private UserModel userModel;

    public ToDoListModel() {
        if (userModel == null) {
            userModel = new UserModel();
        }
    }

    public ToDoListModel(String reminderContext, Date reminderTime, int reminderType) {
        this.reminderContext = reminderContext;
        if (reminderContext == null) {
//            reminderTime = userModel.getPersonalizedSetting().getReminderTime();
        }
        this.reminderTime = reminderTime;
        if (reminderType == 0) {
//            reminderType = userModel.getPersonalizedSetting().getReminderType();
        }
        this.reminderType = reminderType;
    }
}
