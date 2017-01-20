package com.wqzhang.thingswapper.tools;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

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
        intent.setAction(action);
//        intent.setData()
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
     * @param AlarmManager
     */
    public static void setAlarmTimer(Context context, long cycTime, String action, int AlarmManager) {

    }

    public static void cancelAlarmTimer(Context context) {

    }
}
