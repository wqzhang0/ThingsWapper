package com.wqzhang.thingswapper.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.wqzhang.thingswapper.MainApplication;
import com.wqzhang.thingswapper.receiver.NotifyReceiver;
import com.wqzhang.thingswapper.dao.greendao.ToDoThing;
import com.wqzhang.thingswapper.model.AlarmDTO;

import java.util.Calendar;
import java.util.Date;

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
     * @param alarmModel
     */
    public static void setAlarmTimer(AlarmDTO alarmModel) {
        Context context = MainApplication.getGlobleContext();
        if (alarmModel == null) {
            Log.e(TAG, "AlarmModel is null ,return request");
        }

//        ArrayList<ToDoThing> toDoThings = alarmModel.getToDoThings();
        Date alarmDate = alarmModel.getNotifyDate();

        Intent intent = new Intent(context, NotifyReceiver.class);

        Bundle bundle = new Bundle();
        bundle.putParcelable(Common.INTENT_PARCELABLE_KEY, alarmModel);
        bundle.putString(Common.NOTIFY_TYPE, Common.NOTIFY_NEW_MEG);
//
        intent.putExtras(bundle);
//        intent.putExtra(Common.INTENT_PARCELABLE_KEY, alarmModel);
//        intent.putExtra(Common.NOTIFY_TYPE, Common.NOTIFY_NEW_MEG);
//        intent.putExtra("common", "value");
//        intent.putExtra(Common.INTENT_KEY_BUNDLE_KEY, bundle);

//        intent.putExtra(Common.INTENT_KEY_BUNDLE_KEY, bundle);


        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar calendar = Calendar.getInstance();

        calendar.setTime(alarmDate);
//        calendar.add(Calendar.MINUTE, 1);

        //设置前先取消 废弃  应为Pendintent设置的Flag 是用Intent 更新
//        cancelAlarmTimer();

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Log.d(TAG, "设置了闹铃");
        //ELAPSED_REALTIME 过了一段时间调用
        //ELAPSED_REALTIME_WAKEUP 关机也会调用
        //RTC 当系统调用System.currentTime 于triggerAtTime 相等时调用 Intent
        //RTC_WAKEUP 关机时也会调用
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);


    }

    public static void cancelAlarmTimer() {

        Context context = MainApplication.getGlobleContext();
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, NotifyReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.cancel(pendingIntent);
    }

    public static void checkNeedReset(ToDoThing toDoThing) {

        Context context = MainApplication.getGlobleContext();
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            AlarmManager.AlarmClockInfo alarmClockInfo = alarmManager.getNextAlarmClock();
            if (alarmClockInfo != null) {
                Date date = new Date(alarmClockInfo.getTriggerTime());
            }
        }
//        alarmManager.cancel();

    }
}
