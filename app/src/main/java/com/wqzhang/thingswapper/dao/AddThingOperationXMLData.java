package com.wqzhang.thingswapper.dao;

import com.wqzhang.thingswapper.dao.greendao.Notification;
import com.wqzhang.thingswapper.dao.greendao.ToDoThing;
import com.wqzhang.thingswapper.events.SaveChooseOperationEvent;
import com.wqzhang.thingswapper.exceptions.CustomerException;
import com.wqzhang.thingswapper.model.HistoryData;
import com.wqzhang.thingswapper.tools.Common;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by wqzhang on 17-1-7.
 */

public class AddThingOperationXMLData {
    private static EventBus bus;
    static SharedPreferencesControl sharedPreferencesControl;

    private AddThingOperationXMLData() {
    }

    private static AddThingOperationXMLData addThingOperationDataEventBus;


    public static AddThingOperationXMLData getInstall() {
        if (addThingOperationDataEventBus == null) {
            addThingOperationDataEventBus = new AddThingOperationXMLData();
            try {
                sharedPreferencesControl = SharedPreferencesControl.getInstanll();
            } catch (CustomerException e) {
                e.printStackTrace();
            }
        }

        return addThingOperationDataEventBus;
    }

    public void init() {
        bus = EventBus.getDefault();
        bus.register(this);
    }

    @Subscribe
    public void save(SaveChooseOperationEvent saveChooseOperationEvent) {
        switch (saveChooseOperationEvent.getType()) {
            case SaveChooseOperationEvent.TYPE_SAVE_CONTEXT:
                sharedPreferencesControl.getEditor().putString("NOTIFY_CONTENT", saveChooseOperationEvent.getContent()).commit();
                break;
            case SaveChooseOperationEvent.TYPE_SAVE_NOTIFY_TYPE:
                int notifyType = sharedPreferencesControl.getSharedPreferences().getInt("NOTIFY_TYPE", 0);
                int tmpType = saveChooseOperationEvent.getNotifyType();

                if ((notifyType & Math.abs(tmpType)) > 0) {
                    if (tmpType < 0) {
                        sharedPreferencesControl.getEditor().putInt("NOTIFY_TYPE", (notifyType + tmpType)).commit();
                    }
                } else {
                    sharedPreferencesControl.getEditor().putInt("NOTIFY_TYPE", (notifyType + tmpType)).commit();
                }

                break;
            case SaveChooseOperationEvent.TYPE_SAVE_NOTYFLY_COUNTS:
                sharedPreferencesControl.getEditor().putString("NOTIFY_COUNTS", saveChooseOperationEvent.getNotifityCounts()).commit();
                break;
            case SaveChooseOperationEvent.TYPE_SAVE_NOTYFLY_DATE:
                if (!saveChooseOperationEvent.isDetermine()) return;
                ArrayList<Date> dates = readNotifyTime();
                dates.add(saveChooseOperationEvent.getDate());
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < dates.size(); i++) {
                    if (i == 0) {
                        sb.append(dates.get(i).toString());
                    } else {
                        sb.append("&&" + dates.get(i).toString());
                    }
                }
                sharedPreferencesControl.getEditor().putString("NOTIFY_DATES", sb.toString()).commit();
                break;
            case SaveChooseOperationEvent.TYPE_IS_REPEAT:
                sharedPreferencesControl.getEditor().putBoolean("IS_REPEAT", saveChooseOperationEvent.isDetermine()).commit();

                break;
            case SaveChooseOperationEvent.TYPE_IS_REMINDER:
                sharedPreferencesControl.getEditor().putBoolean("IS_REMINDER", saveChooseOperationEvent.isDetermine()).commit();

                break;
            default:
                break;

        }

    }

    /**
     * 是否重复提醒
     *
     * @return
     */
    public boolean isRepeat() {
        return sharedPreferencesControl.getSharedPreferences().getBoolean("IS_REPEAT", false);
    }

    /**
     * 是否需要提醒
     *
     * @return
     */
    public boolean isReminder() {
        return sharedPreferencesControl.getSharedPreferences().getBoolean("IS_REMINDER", false);
    }

    /**
     * 清除数据
     */
    public void clearHistory() {
        sharedPreferencesControl.getEditor().clear().commit();
    }


    /**
     * 获取临时存储提醒时间的内容
     *
     * @return
     */
    public ToDoThing getToDothing() {
        ToDoThing toDoThing = new ToDoThing();
        toDoThing.setCreateDate(new Date());
        toDoThing.setIsSynchronize(false);
        toDoThing.setStatus(Common.STATUS_TO_BE_DONE);
        toDoThing.setReminderContext(readContent());
        toDoThing.setReminderType(readNotifyType());
        return toDoThing;
    }

    /**
     * 获取临时存储提醒时间的内容
     *
     * @return
     */
    public ArrayList<Notification> getNotifycation() {
        ArrayList<Notification> notificationArrayList = new ArrayList<>();

        ArrayList<Date> dates = readNotifyTime();
        int notifyType = readNotifyType();
        if (dates.size() == 0) {
            Notification notification = new Notification();
            notification.setNotifyType(Common.REMINDER_TYPE_NONE);
            notification.setIsSynchronize(false);
            notificationArrayList.add(notification);
        } else if (dates.size() == 1) {
            //只有一个时间
            //现在版本 只有提醒时间  无其他
            Notification notification = new Notification();
            notification.setNotifyType(notifyType);
            notification.setIsSynchronize(false);
            notification.setReminderDate(dates.get(0));

            notificationArrayList.add(notification);

        } else {
            //多个时间提醒
            for (Date _date : dates) {
                Notification notification = new Notification();
                notification.setNotifyType(notifyType);
                notification.setIsSynchronize(false);
                notification.setReminderDate(_date);
                notificationArrayList.add(notification);
            }
        }
        return notificationArrayList;
    }

    public String readContent() {
        return sharedPreferencesControl.getSharedPreferences().getString("NOTIFY_CONTENT", "");
    }

    public int readNotifyType() {
        int notifyType = sharedPreferencesControl.getSharedPreferences().getInt("NOTIFY_TYPE", 0);
        return notifyType;
    }

    public ArrayList<Date> readNotifyTime() {
        ArrayList<Date> dates = new ArrayList<>();
        String _str_dates = sharedPreferencesControl.getSharedPreferences().getString("NOTIFY_DATES", "");
        if (_str_dates.equals("")) {
            return dates;
        }
        String[] _str_Num = _str_dates.split("&&");
        for (String tmp : _str_Num) {
            dates.add(new Date(tmp));
        }

        return dates;
    }

    public String readNotifyCounts() {
        return sharedPreferencesControl.getSharedPreferences().getString("NOTIFY_COUNTS", "不重复");
    }

    public HistoryData readHistoryData() {
        String content = readContent();
        int notifyType = readNotifyType();
        ArrayList<Date> dates = readNotifyTime();
        String notifyCount = readNotifyCounts();
        HistoryData historyData = new HistoryData(content, notifyType, dates, notifyCount);
        return historyData;
    }
}
