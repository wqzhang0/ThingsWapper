package com.wqzhang.thingswapper.tools;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.wqzhang.thingswapper.Receivers.NotifyReceiver;
import com.wqzhang.thingswapper.dao.greendao.ToDoThing;
import com.wqzhang.thingswapper.exceptions.CustomerException;
import com.wqzhang.thingswapper.services.NotifyService;

import java.util.Calendar;
import java.util.List;

/**
 * Created by wqzhang on 17-1-19.
 * 闹铃提醒类
 */

public class AlarmTimer {

    private final static String TAG = "AlarmTimer";

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
     * @param AlarmManagerType
     */
    public static void setAlarmTimer(Context context, long cycTime, int AlarmManagerType, List<ToDoThing> toDoThings) {
//        Intent intent = new Intent(context, NotifyService.class);
//        try {
//            intent.putExtras(NotifyParseUtil.parseTodoThingToBundle(toDoThings));
//        } catch (CustomerException e) {
//            e.printStackTrace();
//            //添加闹铃错误,暂未添加处理
//            return;
//        }
//        PendingIntent sender = PendingIntent.getService(context, 0, intent, 0);
//        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
////        alarmManager.set(AlarmManagerType, cycTime, sender);
//        alarmManager.setRepeating(AlarmManagerType, System.currentTimeMillis(), 1000 * 5, sender);


        Intent intent = new Intent(context, NotifyReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.MINUTE, 1);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 1000 * 1, pendingIntent);
        Log.d(TAG, "设置一分钟之后提醒");
    }

    public static void cancelAlarmTimer(Context context) {

    }
}
