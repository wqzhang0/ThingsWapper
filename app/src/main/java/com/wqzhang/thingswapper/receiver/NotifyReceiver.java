package com.wqzhang.thingswapper.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.wqzhang.thingswapper.MainApplication;
import com.wqzhang.thingswapper.R;
import com.wqzhang.thingswapper.activity.MainActivity;
import com.wqzhang.thingswapper.dao.BusinessProcess;
import com.wqzhang.thingswapper.model.AlarmDTO;
import com.wqzhang.thingswapper.util.AlarmTimer;
import com.wqzhang.thingswapper.util.Common;
import com.wqzhang.thingswapper.util.DialogUtil;
import com.wqzhang.thingswapper.util.NotifyParseUtil;
import com.wqzhang.thingswapper.util.SystemUtil;

import java.util.ArrayList;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by wqzhang on 17-1-21.
 */

public class NotifyReceiver extends BroadcastReceiver {
    private final String TAG = "NotifyReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "收到消息  ");
        Bundle bundle = intent.getExtras();
        String intentType = (String) bundle.getCharSequence(Common.NOTIFY_TYPE);
        //有新的闹醒需要提醒

        if (intentType.equals(Common.NOTIFY_NEW_MEG)) {
            //如果是重复提醒,设置下次提醒的时间
            AlarmDTO alarmDTO = bundle.getParcelable(Common.INTENT_PARCELABLE_KEY);

            //判断应用是在前台还是后台
            boolean isBg = SystemUtil.isBackground(SystemUtil.APP_PACKAGE);

            if (isBg) {
                DialogUtil.showNotifyNow(MainApplication.getDialogContext(), alarmDTO.getToDoThingsContent());
                ArrayList<Long> notifyIds = alarmDTO.getNotifyIds();
                ArrayList<String> contents = alarmDTO.getToDoThingsContent();
                for (int i = 0; i < notifyIds.size(); i++) {
                    //发送通知消息
                    sendNotification(notifyIds.get(i), contents.get(i), intent);
                    //已经发送通知,这里修改数据库  提醒类型设置为已经提醒
//                    BusinessProcess.getInstance().updateThingState(notifyIds.get(i), Common.STATUS_FINSH);
                }
            } else {
                //如果在前台 直接显示
                DialogUtil.showNotifyNow(MainApplication.getDialogContext(), alarmDTO.getToDoThingsContent());
            }
            //刷新重复提醒的时间
            BusinessProcess.getInstance().updateCalculationNextReminderDate(alarmDTO.getNotifyIds());
            //通知之后  重新设置新的闹铃
            AlarmDTO needNotifyAlarmModel = BusinessProcess.getInstance().listRecentNeedNotifyThings();
            if (needNotifyAlarmModel != null) {
                //存在需要提醒的事项
                //设置Alerm
                AlarmTimer.setAlarmTimer(needNotifyAlarmModel);
            }
        }
    }

    public void sendNotification(Long notifyid, String contents, Intent sourceInent) {
        Intent showIntent = new Intent(MainApplication.getGlobleContext(), MainActivity.class);
//        showIntent.setFlags(Intent.NEED.)
        Bundle bundle = sourceInent.getExtras();

        showIntent.putExtras(bundle);
        PendingIntent piShowIntent = PendingIntent.getActivity(
                MainApplication.getGlobleContext(), 0, showIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(MainApplication.getGlobleContext()).
                setContentText(contents).setContentTitle("有新的提醒").setSmallIcon(R.drawable.finsh_light);
//        builder.setFullScreenIntent(piShowIntent, false);
        builder.setContentIntent(piShowIntent);

        Notification notification = builder.build();
        AlarmDTO alarmModel = bundle.getParcelable(Common.INTENT_PARCELABLE_KEY);

        if (NotifyParseUtil.isAlarm(alarmModel.getReminderType())) {
            notification.defaults |= Notification.DEFAULT_SOUND;
        }
        if (NotifyParseUtil.isVertical(alarmModel.getReminderType())) {
            notification.defaults |= Notification.DEFAULT_VIBRATE;
        }
        if (NotifyParseUtil.isEmail(alarmModel.getReminderType())) {
            notification.defaults |= Notification.DEFAULT_SOUND;
            notification.defaults |= Notification.DEFAULT_VIBRATE;
        }

        NotificationManager notificationManager = (NotificationManager) MainApplication.getGlobleContext().getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(notifyid.intValue(), notification);
    }

    public void sendNotification(Intent sourceInent) {
        Intent showIntent = new Intent(MainApplication.getGlobleContext(), MainActivity.class);
//        showIntent.setFlags(Intent.NEED.)
//        Bundle bundle = sourceInent.getBundleExtra(Common.INTENT_KEY_BUNDLE_KEY);
        Bundle bundle = sourceInent.getExtras();

        showIntent.putExtras(bundle);
//        showIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        showIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//        showIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent piShowIntent = PendingIntent.getActivity(
                MainApplication.getGlobleContext(), 0, showIntent, PendingIntent.FLAG_UPDATE_CURRENT);

//        Intent missIntent = new Intent(MainApplication.getGlobleContext(), NotifyService.class);

//        PendingIntent piMissIntent = PendingIntent.getService(MainApplication.getGlobleContext(), 0, missIntent, 0);

//        Intent snoozeIntent = new Intent(MainApplication.getGlobleContext(), NotifyService.class);
//        PendingIntent piSnooze =
//                PendingIntent.getService(MainApplication.getGlobleContext(), 0, snoozeIntent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(MainApplication.getGlobleContext()).
                setContentText("吃早饭\\n看电影").setContentTitle("有新的提醒").setSmallIcon(R.drawable.finsh_light);
//        builder.setFullScreenIntent(piShowIntent, false);
        builder.setContentIntent(piShowIntent);
//        builder.addAction(0, "五分钟后再次提醒", piSnooze);
//        builder.addAction(0, "不再提醒", piDismiss);


//        builder.setDeleteIntent(piDismiss);
//        builder.setContentIntent(piDismiss);
        //设置是否一直处于通知栏
//        builder.setOngoing(true);
        //可以点击通知栏的删除按钮删除
//        builder.setAutoCancel(true);

        Notification notification = builder.build();
        notification.defaults |= Notification.DEFAULT_SOUND;
        notification.defaults |= Notification.DEFAULT_VIBRATE;
        NotificationManager notificationManager = (NotificationManager) MainApplication.getGlobleContext().getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification);
        notificationManager.notify(2, notification);
        notificationManager.notify(3, notification);
        notificationManager.notify(4, notification);
    }

    public void showNotification(Intent sourceInent) {
        Context context = MainApplication.getDialogContext();
        android.support.v7.app.NotificationCompat.Builder builder = new android.support.v7.app.NotificationCompat.Builder(context);
        builder.setContentText("吃早饭//n看电影").setContentTitle("ThingsWapper提醒").setSmallIcon(R.drawable.finsh_light);
        Intent appIntent = new Intent(Intent.ACTION_MAIN);
        appIntent.addCategory(Intent.CATEGORY_LAUNCHER);
//        appIntent.setComponent(new ComponentName(context.getPackageName(), context.getPackageName() + "." + context.getLocalClassName()));
        appIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);//关键的一步，设置启动模式
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, appIntent, 0);

        builder.setContentIntent(contentIntent);

        Notification notification = builder.build();
        notification.flags = Notification.FLAG_AUTO_CANCEL;

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification);

    }

}
