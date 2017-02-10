package com.wqzhang.thingswapper.tools;

/**
 * Created by wqzhang on 16-12-19.
 */

public class Common {
    //完成情况
    public static final int STATUS_TO_BE_DONE = 1;
    public static final int STATUS_FINSH = 2;
    public static final int STATUS_DELETE = 3;


    //提醒类型
    public static final int REMINDER_TYPE_NONE = 0;
    public static final int REMINDER_TYPE_VERTICAL = 1;
    public static final int REMINDER_TYPE_ALARM = 2;
    public static final int REMINDER_TYPE_EMAIL = 4;

    //NotifyReceiver 提醒类型
    public static final String NOTIFY_NEW_MEG = "NOTIFY_NEW_MEG";
    public static final String NOTIFY_TYPE = "NOTIFY_TYPE";

    //Intent key
    public static final String INTENT_KEY_BUNDLE_KEY = "VALUES";
    public static final String INTENT_PARCELABLE_KEY = "MODEL";

}
