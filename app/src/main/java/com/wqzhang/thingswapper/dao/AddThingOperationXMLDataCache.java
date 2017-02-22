package com.wqzhang.thingswapper.dao;


import java.util.ArrayList;
import java.util.Date;

/**
 * Created by wqzhang on 17-1-30.
 * 用户未保存的数据CACHE类
 * 调用历史数据的时候 同意从这个类里面调用   避免从AddThingOperationXMLData 里面读取
 */

public class AddThingOperationXMLDataCache {
    private static String content;
    private static int notifyType;
    private static ArrayList<Date> dates;
    private static String notifyCounts;
    private static boolean repeat;
    private static boolean reminder;


    public static String getContent() {
        return content;
    }

    public static void setContent(String content) {
        AddThingOperationXMLDataCache.content = content;
    }

    public static int getNotifyType() {
        return notifyType;
    }

    public static void setNotifyType(int notifyType) {
        AddThingOperationXMLDataCache.notifyType = notifyType;
    }

    public static ArrayList<Date> getDates() {
        return dates;
    }

    public static void setDates(ArrayList<Date> dates) {
        AddThingOperationXMLDataCache.dates = dates;
    }

    public static String getNotifyCounts() {
        return notifyCounts;
    }

    public static void setNotifyCounts(String notifyCounts) {
        AddThingOperationXMLDataCache.notifyCounts = notifyCounts;
    }

    public static boolean isRepeat() {
        return repeat;
    }

    public static boolean isReminder() {
        return reminder;
    }

    public static void setRepeat(boolean repeat) {
        AddThingOperationXMLDataCache.repeat = repeat;
    }


    public static void setReminder(boolean reminder) {
        AddThingOperationXMLDataCache.reminder = reminder;
    }

    public static void readHistory() {
        String content = AddThingOperationXMLData.getInstall().readContent();
        int notifyType = AddThingOperationXMLData.getInstall().readNotifyType();
        ArrayList<Date> dates = AddThingOperationXMLData.getInstall().readNotifyTime();
        String notifyCount = AddThingOperationXMLData.getInstall().readNotifyCounts();

        AddThingOperationXMLDataCache.content = content;
        AddThingOperationXMLDataCache.notifyType = notifyType;
        AddThingOperationXMLDataCache.dates = dates;
        notifyCounts = notifyCount;
    }
}
