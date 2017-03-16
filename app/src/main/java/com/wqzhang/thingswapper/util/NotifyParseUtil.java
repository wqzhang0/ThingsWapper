package com.wqzhang.thingswapper.util;

import android.os.Bundle;

import com.wqzhang.thingswapper.dao.greendao.Notification;
import com.wqzhang.thingswapper.dao.greendao.ToDoThing;
import com.wqzhang.thingswapper.exception.CustomerException;
import com.wqzhang.thingswapper.model.AlarmDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wqzhang on 17-1-20.
 */

public class NotifyParseUtil {
    static Bundle parseDoubleListToBundle(ArrayList<Long> ids, ArrayList<String> contents) throws CustomerException {
        if (ids.size() != contents.size()) {
            throw new CustomerException("传入ids 和contents 长度不一致");
        }
        Bundle bundle = new Bundle();

        long[] tmpIds = new long[ids.size()];

        for (int i = 0; i < ids.size(); i++) {
            tmpIds[i] = ids.get(i).longValue();
        }

        bundle.putLongArray("ids", tmpIds);
        bundle.putStringArrayList("contents", contents);

        return bundle;
    }

    static ArrayList<String> parseContents(Bundle bundle) {
        ArrayList<String> contents = bundle.getStringArrayList("contents");
        return contents;
    }

    static ArrayList<Long> parseIds(Bundle bundle) {
        long[] tmpIds = bundle.getLongArray("ids");
        ArrayList<Long> ids = new ArrayList<>();
        for (int i = 0; i < tmpIds.length; i++) {
            ids.add(tmpIds[i]);
        }
        return ids;
    }

    static Bundle parseMapToBundle(Map<Long, String> map) throws CustomerException {
        ArrayList<Long> ids = new ArrayList<>();
        ArrayList<String> contents = new ArrayList<>();
        for (Map.Entry<Long, String> entry : map.entrySet()) {
            ids.add(entry.getKey());
            contents.add(entry.getValue());
        }
        return parseDoubleListToBundle(ids, contents);
    }

    static Bundle parseTodoThingToBundle(List<ToDoThing> toDoThings) throws CustomerException {
        Map<Long, String> map = new HashMap<>();
        for (ToDoThing thing : toDoThings) {
            map.put(thing.getId(), thing.getReminderContext());
        }
        return parseMapToBundle(map);
    }

    static Map<Long, String> parseNotifyContents(Bundle bundle) {

        Map<Long, String> map = new HashMap<>();
        ArrayList<Long> ids = parseIds(bundle);
        ArrayList<String> contents = parseContents(bundle);
        for (int i = 0; i < ids.size(); i++) {
            map.put(ids.get(i), contents.get(i));
        }
        return map;
    }

    public static Integer getNotifyType(List<ToDoThing> toDoThings) {
        if (toDoThings == null || toDoThings.size() == 0) return Common.REMINDER_TYPE_NONE;
        //查找需要提醒事项里 提醒的种类
        // REMINDER_TYPE_VERTICAL = 1;
        // REMINDER_TYPE_ALARM = 2;
        // REMINDER_TYPE_EMAIL = 4;
        boolean tmpVertical = false;
        boolean tmpAlarm = false;
        boolean tmpEmail = false;
        for (ToDoThing toDoThing : toDoThings) {
            int reminderType = toDoThing.getReminderType();
            if (reminderType == Common.REMINDER_TYPE_VERTICAL) {
                tmpVertical = true;
            } else if (reminderType == Common.REMINDER_TYPE_ALARM) {
                tmpAlarm = true;
            } else if (reminderType == Common.REMINDER_TYPE_EMAIL) {
                tmpEmail = true;
            } else if (reminderType == (Common.REMINDER_TYPE_VERTICAL | Common.REMINDER_TYPE_ALARM)) {
                tmpVertical = true;
                tmpAlarm = true;
            } else if (reminderType == (Common.REMINDER_TYPE_VERTICAL | Common.REMINDER_TYPE_EMAIL)) {
                tmpVertical = true;
                tmpEmail = true;
            } else if (reminderType == (Common.REMINDER_TYPE_ALARM | Common.REMINDER_TYPE_EMAIL)) {
                tmpAlarm = true;
                tmpEmail = true;
            } else if (reminderType == (Common.REMINDER_TYPE_ALARM | Common.REMINDER_TYPE_EMAIL | Common.REMINDER_TYPE_VERTICAL)) {
                tmpVertical = true;
                tmpAlarm = true;
                tmpEmail = true;
            }
        }
        Integer finalType = 0;
        if (tmpVertical) {
            finalType |= Common.REMINDER_TYPE_VERTICAL;
        }
        if (tmpAlarm) {

            finalType |= Common.REMINDER_TYPE_ALARM;
        }
        if (tmpEmail) {
            finalType |= Common.REMINDER_TYPE_EMAIL;
        }
        return finalType;
    }

    public static boolean isVertical(int type) {
        boolean isVertical = false;
        //震动 type = 1
        if (type == 1 || type == 3 || type == 5 || type == 7) {
            isVertical = true;
        }
        return isVertical;
    }

    public static boolean isEmail(int type) {
        //邮件提醒类型 type = 4;
        boolean isEmail = false;
        if (type == 4 || type == 5 || type == 6 || type == 7) {
            isEmail = true;
        }
        return isEmail;
    }

    public static boolean isAlarm(int type) {
        //闹铃提醒 type = 2;
        boolean isAlarm = false;

        if (type == 2 || type == 3 || type == 6 || type == 7) {
            isAlarm = true;
        }
        return isAlarm;
    }

    /**
     * 获得不需要重复提醒的事项的需要提醒的最近一次时间
     *
     * @param noRepeatNotificationArrayList
     * @return
     */
    public static AlarmDTO getRecentNoRepeatNotifys(ArrayList<Notification> noRepeatNotificationArrayList, Long userId) {
        ArrayList<Long> notificationIds = new ArrayList<>();
        ArrayList<ToDoThing> toDoThings = new ArrayList<>();
        AlarmDTO alarmModel = null;

        Notification firstNotification = noRepeatNotificationArrayList.get(0);
        for (Notification notifycation : noRepeatNotificationArrayList) {
            //查找出与排序第一条相同的提醒时间
            //并且事项处于未被提醒状态
            if (notifycation.getReminderDate().equals(firstNotification.getReminderDate())) {
                if (notifycation.getToDoThingIds().get(0).getToDoThing().getUserId() == userId) {
                    toDoThings.add(notifycation.getToDoThingIds().get(0).getToDoThing());
                    notificationIds.add(notifycation.getId());
                }
            }
        }
        Integer reminderType = NotifyParseUtil.getNotifyType(toDoThings);
        alarmModel = new AlarmDTO(toDoThings, noRepeatNotificationArrayList.get(0).getReminderDate(), reminderType, notificationIds);
        return alarmModel;
    }

    /**
     * 获取重复提醒通知事件的 最近的提醒时间
     *
     * @param repeatNotificationArrayList
     * @return
     */
    public static AlarmDTO getRecentRepeatNotifys(ArrayList<Notification> repeatNotificationArrayList, Long userId) {
        ArrayList<Long> notificationIds = new ArrayList<>();
        ArrayList<ToDoThing> toDoThings = new ArrayList<>();
        AlarmDTO alarmModel = null;

        Notification firstNotification = repeatNotificationArrayList.get(0);
        for (Notification notifycation : repeatNotificationArrayList) {
            //查找出与排序第一条相同的提醒时间
            if (notifycation.getNextRemindDate().equals(firstNotification.getNextRemindDate())) {
                if (notifycation.getToDoThingIds().get(0).getToDoThing().getUserId() == userId) {
                    //并且事项处于未被提醒状态
                    toDoThings.add(notifycation.getToDoThingIds().get(0).getToDoThing());
                    notificationIds.add(notifycation.getId());
                }
            }
        }
        Integer reminderType = NotifyParseUtil.getNotifyType(toDoThings);
        alarmModel = new AlarmDTO(toDoThings, repeatNotificationArrayList.get(0).getNextRemindDate(), reminderType, notificationIds);
        return alarmModel;
    }

    /**
     * 当需要重复的和不需要重复 提醒的事项,都设置了在同一时间提醒
     *
     * @param noRepeatNotificationArrayList
     * @param repeatNotificationArrayList
     * @return
     */
    public static AlarmDTO getRecentNotifys(ArrayList<Notification> noRepeatNotificationArrayList, ArrayList<Notification> repeatNotificationArrayList, Long userId) {
        ArrayList<Long> notificationIds = new ArrayList<>();
        ArrayList<ToDoThing> toDoThings = new ArrayList<>();
        AlarmDTO alarmModel = null;
        if (noRepeatNotificationArrayList.get(0).getReminderDate().getTime() < repeatNotificationArrayList.get(0).getNextRemindDate().getTime()) {
            Notification firstNotification = noRepeatNotificationArrayList.get(0);
            for (Notification notifycation : noRepeatNotificationArrayList) {
                //查找出与排序第一条相同的提醒时间
                if (notifycation.getReminderDate().equals(firstNotification.getReminderDate())) {
                    if (notifycation.getToDoThingIds().get(0).getToDoThing().getUserId() == userId) {
                        toDoThings.add(notifycation.getToDoThingIds().get(0).getToDoThing());
                        notificationIds.add(notifycation.getId());
                    }
                }
            }
            for (Notification notifycation : repeatNotificationArrayList) {
                //查找出与排序第一条相同的提醒时间
                if (notifycation.getNextRemindDate().equals(firstNotification.getNextRemindDate())) {
                    if (notifycation.getToDoThingIds().get(0).getToDoThing().getUserId() == userId) {
                        //并且事项处于未被提醒状态
                        toDoThings.add(notifycation.getToDoThingIds().get(0).getToDoThing());
                        notificationIds.add(notifycation.getId());
                    }
                }
            }
            Integer reminderType = NotifyParseUtil.getNotifyType(toDoThings);
            alarmModel = new AlarmDTO(toDoThings, noRepeatNotificationArrayList.get(0).getReminderDate(), reminderType, notificationIds);

        }
        return alarmModel;
    }
}
