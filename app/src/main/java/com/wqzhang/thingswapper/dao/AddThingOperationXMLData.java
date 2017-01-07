package com.wqzhang.thingswapper.dao;

import com.wqzhang.thingswapper.events.SaveChooseOperationEvent;
import com.wqzhang.thingswapper.exceptions.CustomerException;
import com.wqzhang.thingswapper.model.HistoryData;

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
        }

    }

    /**
     * 清除数据
     */
    public void clearHistory() {
        sharedPreferencesControl.getEditor().clear().commit();
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
