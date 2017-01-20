package com.wqzhang.thingswapper.tools;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.wqzhang.thingswapper.exceptions.CustomerException;

import java.util.Map;

/**
 * Created by wqzhang on 17-1-19.
 * 闹铃提醒类
 */

public class AlarmTimer {

    /**
     * 设置周期性提醒闹铃
     *
     * @param context
     * @param firstTime
     * @param cycTime
     * @param action
     * @param AlarmManagerType
     */
    public static void setRepeatingAlarmTimer(Context context, long firstTime, long cycTime, String action, int AlarmManagerType) {

        Intent intent = new Intent();
//        intent.setAction(action);
//        intent.putExtras();

        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManagerType, cycTime, sender);
//        alarmManager.cancel();
    }

    /**
     * 设置提醒闹铃
     *
     * @param context
     * @param cycTime
     * @param action
     * @param AlarmManagerType
     */
    public static void setAlarmTimer(Context context, long cycTime, String action, int AlarmManagerType, Map map) {
        Intent intent = new Intent();
        intent.setAction(action);
        try {
            intent.putExtras(NotifyParseUtil.parseMapToBundle(map));
        } catch (CustomerException e) {
            e.printStackTrace();
            //添加闹铃错误,暂未添加处理
            return;
        }
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManagerType, cycTime, sender);
    }

    public static void cancelAlarmTimer(Context context) {

    }
}
