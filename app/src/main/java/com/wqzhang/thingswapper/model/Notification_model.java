package com.wqzhang.thingswapper.model;

import java.util.Date;

/**
 * Created by wqzhang on 16-12-27.
 * id	用户id	相关事件id	是否提醒	提醒时间	提醒频率	提醒频率间隔	提醒次数	结束时间	是否同步
   id	userId	todothingsId	isNotify	reminderDate	remindFrequency	remindFrequencyInterval	remindCount	endDate	isSynchronize

 */

public class Notification_model {
    //id
    private int id;
    //用户id
    private int userId;
    //相关事件id
    private int todothingsId;
    private boolean isNotify;
    //提醒时间
    private Date reminderDate;
    //提醒频率
    private int remindFrequency;
    //提醒频率间隔
    private int remindFrequencyInterval;
    //提醒次数
    private int remindCount;
    //结束时间
    private Date endDate;
    //是否同步
    private boolean isSynchronize;

    public Notification_model(){}
}
