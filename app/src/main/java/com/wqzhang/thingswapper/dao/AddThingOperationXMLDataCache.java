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
    private static boolean isRepeat;
    private static boolean isReminder;


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
        return isRepeat;
    }

    public static void setIsRepeat(boolean isRepeat) {
        AddThingOperationXMLDataCache.isRepeat = isRepeat;
    }

    public static boolean isReminder() {
        return isReminder;
    }

    public static void setIsReminder(boolean isReminder) {
        AddThingOperationXMLDataCache.isReminder = isReminder;
    }

    public static void readHistory() {
        String _content = AddThingOperationXMLData.getInstall().readContent();
        int _notifyType = AddThingOperationXMLData.getInstall().readNotifyType();
        ArrayList<Date> _dates = AddThingOperationXMLData.getInstall().readNotifyTime();
        String _notifyCount = AddThingOperationXMLData.getInstall().readNotifyCounts();

        content = _content;
        notifyType = _notifyType;
        dates = _dates;
        notifyCounts = _notifyCount;
    }
}
