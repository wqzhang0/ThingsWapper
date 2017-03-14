package com.wqzhang.thingswapper.dao;

import com.wqzhang.thingswapper.dao.greendao.Notification;
import com.wqzhang.thingswapper.dao.greendao.ToDoThing;
import com.wqzhang.thingswapper.event.DataCacheChange;
import com.wqzhang.thingswapper.event.SaveChooseOperationEvent;
import com.wqzhang.thingswapper.exception.CustomerException;
import com.wqzhang.thingswapper.util.Common;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by wqzhang on 17-1-7.
 * 存储用户未保存的数据   在执行保存操作的时候,顺便将数据保存进DateCache中,
 */

public class AddThingOperationXMLData {
    private static EventBus bus;
    static SharedPreferencesControl sharedPreferencesControl;


    ArrayList<Date> dates;
    StringBuilder sb;

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
                sharedPreferencesControl.getUserDataCacheEditor().putString("NOTIFY_CONTENT", saveChooseOperationEvent.getContent()).commit();
                AddThingOperationXMLDataCache.setContent(saveChooseOperationEvent.getContent());
                break;
            case SaveChooseOperationEvent.TYPE_SAVE_NOTIFY_TYPE:
                int notifyType = sharedPreferencesControl.getUserDataCacheSP().getInt("NOTIFY_TYPE", 0);
                int tmpType = saveChooseOperationEvent.getNotifyType();

                if ((notifyType & Math.abs(tmpType)) > 0) {
                    if (tmpType < 0) {
                        sharedPreferencesControl.getUserDataCacheEditor().putInt("NOTIFY_TYPE", (notifyType + tmpType)).commit();
                        AddThingOperationXMLDataCache.setNotifyType(notifyType + tmpType);
                    }
                } else {
                    sharedPreferencesControl.getUserDataCacheEditor().putInt("NOTIFY_TYPE", (notifyType + tmpType)).commit();
                    AddThingOperationXMLDataCache.setNotifyType(notifyType + tmpType);
                }

                break;
            case SaveChooseOperationEvent.TYPE_SAVE_NOTYFLY_COUNTS:
                if (!saveChooseOperationEvent.getDetermine()) return;
                sharedPreferencesControl.getUserDataCacheEditor().putString("NOTIFY_COUNTS", saveChooseOperationEvent.getNotifityCounts()).commit();
                AddThingOperationXMLDataCache.setNotifyCounts(saveChooseOperationEvent.getNotifityCounts());
                //发送 改变视图的消息
                bus.post(new DataCacheChange(DataCacheChange.TYPE_NOTYFLY_COUNTS_CHANGE, saveChooseOperationEvent.getNotifityCounts()));
                break;
            case SaveChooseOperationEvent.TYPE_SAVE_NOTYFLY_DATE:
                if (!saveChooseOperationEvent.getDetermine()) return;
                dates = readNotifyTime();
                dates.add(saveChooseOperationEvent.getDate());
                sb = new StringBuilder();
                for (int i = 0; i < dates.size(); i++) {
                    if (i == 0) {
                        sb.append(dates.get(i).toString());
                    } else {
                        sb.append("&&" + dates.get(i).toString());
                    }
                }
                sharedPreferencesControl.getUserDataCacheEditor().putString("NOTIFY_DATES", sb.toString()).commit();
                AddThingOperationXMLDataCache.setDates(dates);
                bus.post(new DataCacheChange(DataCacheChange.TYPE_NOTYFLY_DATE_CHANGE, true));
                break;
            case SaveChooseOperationEvent.TYPE_REMOVE_NOTIFY_DATE:
                if (!saveChooseOperationEvent.getDetermine()) return;
                dates = readNotifyTime();
                for (int i = 0; i < dates.size(); i++) {
                    if (dates.get(i).equals(saveChooseOperationEvent.getDate())) {
                        dates.remove(i);
                        sb = new StringBuilder();
                        for (int j = 0; j < dates.size(); j++) {
                            if (j == 0) {
                                sb.append(dates.get(j).toString());
                            } else {
                                sb.append("&&" + dates.get(j).toString());
                            }
                        }
                        sharedPreferencesControl.getUserDataCacheEditor().putString("NOTIFY_DATES", sb.toString()).commit();
                        AddThingOperationXMLDataCache.setDates(dates);
                        bus.post(new DataCacheChange(DataCacheChange.TYPE_REMOVE_NOTIFY_DATE_CHANGE, true));
                        return;
                    }
                }

                break;


            case SaveChooseOperationEvent.TYPE_IS_REPEAT:
                sharedPreferencesControl.getUserDataCacheEditor().putBoolean("IS_REPEAT", saveChooseOperationEvent.getDetermine()).commit();
                AddThingOperationXMLDataCache.setRepeat(saveChooseOperationEvent.getDetermine());
                break;
            case SaveChooseOperationEvent.TYPE_IS_REMINDER:
                sharedPreferencesControl.getUserDataCacheEditor().putBoolean("IS_REMINDER", saveChooseOperationEvent.getDetermine()).commit();
                AddThingOperationXMLDataCache.setReminder(saveChooseOperationEvent.getDetermine());
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
        return sharedPreferencesControl.getUserDataCacheSP().getBoolean("IS_REPEAT", false);
    }

    /**
     * 是否需要提醒
     *
     * @return
     */
    public boolean isReminder() {
        return sharedPreferencesControl.getUserDataCacheSP().getBoolean("IS_REMINDER", false);
    }

    /**
     * 清除数据
     */
    public void clearHistory() {
        sharedPreferencesControl.getUserDataCacheEditor().clear().commit();
        AddThingOperationXMLDataCache.readHistory();
    }


    /**
     * 获取临时存储提醒时间的内容
     *
     * @return
     */
    public ToDoThing getToDothing() {
        ToDoThing toDoThing = new ToDoThing();
        toDoThing.setCreateDate(new Date());
        toDoThing.setSynchronize(false);
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
        //如果没有用户设置了提醒时间,但是没有又关闭了选择switch,这里给出提醒的时间为空
        if (!isReminder()) {
            return notificationArrayList;
        }

        ArrayList<Date> dates = readNotifyTime();
        int notifyType = readNotifyType();
        if (dates.size() == 0) {
            Notification notification = new Notification();
            notification.setAlearyNotify(false);
            notification.setNotifyType(Common.REMINDER_TYPE_NONE);
            notification.setSynchronize(false);
            notification.setInvalide(false);
            notification.setRepeatType(Common.REPEAT_TYPE_0);
            notificationArrayList.add(notification);
        } else if (dates.size() == 1) {
            //只有一个时间
            //现在版本 只有提醒时间  无其他
            Notification notification = new Notification();
            notification.setNotifyType(notifyType);
            notification.setSynchronize(false);
            notification.setReminderDate(dates.get(0));
            notification.setInvalide(false);
            notification.setAlearyNotify(false);
            notification.setRepeatType(Common.REPEAT_TYPE_0);
            //仅当只有一次提醒时间时可以设置重复  这里做重复的判断
            if (isRepeat()) {
                String rePeatType = readNotifyCounts();
                notification.setRepeatType(rePeatType);
            }

            notificationArrayList.add(notification);

        } else {
            //多个时间提醒
            for (Date date : dates) {
                Notification notification = new Notification();
                notification.setNotifyType(notifyType);
                notification.setSynchronize(false);
                notification.setReminderDate(date);
                notification.setRepeatType(Common.REPEAT_TYPE_0);
                notification.setAlearyNotify(false);
                notification.setInvalide(false);
                notificationArrayList.add(notification);
            }
        }
        return notificationArrayList;
    }

    public String readContent() {
        return sharedPreferencesControl.getUserDataCacheSP().getString("NOTIFY_CONTENT", "");
    }

    public int readNotifyType() {
        int notifyType = sharedPreferencesControl.getUserDataCacheSP().getInt("NOTIFY_TYPE", 0);
        return notifyType;
    }

    public ArrayList<Date> readNotifyTime() {
        ArrayList<Date> dates = new ArrayList<>();
        String characterDates = sharedPreferencesControl.getUserDataCacheSP().getString("NOTIFY_DATES", "");
        if (characterDates.equals("")) {
            return dates;
        }
        String[] arrayDates = characterDates.split("&&");
        for (String strDate : arrayDates) {
            dates.add(new Date(strDate));
        }

        return dates;
    }

    public String readNotifyCounts() {
        return sharedPreferencesControl.getUserDataCacheSP().getString("NOTIFY_COUNTS", "不重复");
    }

}
