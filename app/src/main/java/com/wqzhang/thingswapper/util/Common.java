package com.wqzhang.thingswapper.util;

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


    public static final String REPEAT_TYPE_0 = "不重复";
    public static final String REPEAT_TYPE_1 = "工作日";
    public static final String REPEAT_TYPE_2 = "每天";
    public static final String REPEAT_TYPE_3 = "每周";
    public static final String REPEAT_TYPE_4 = "每两周";
    public static final String REPEAT_TYPE_5 = "仅周末";


    //手机测试环境
    public static final String SERVICE_HOST = "http://172.20.10.2:8080/things/";
    //阿里云环境
//    public static final String SERVICE_HOST = "http://112.74.95.27:8080/maven-test/";
    //隔壁wifi测试环境
//    public static final String SERVICE_HOST = "http://192.168.1.107:8080/things/";



}
