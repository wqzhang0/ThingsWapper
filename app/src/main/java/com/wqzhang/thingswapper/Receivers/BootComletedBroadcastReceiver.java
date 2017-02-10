package com.wqzhang.thingswapper.Receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.wqzhang.thingswapper.dao.BusinessProcess;
import com.wqzhang.thingswapper.model.AlarmModel;
import com.wqzhang.thingswapper.tools.AlarmTimer;

/**
 * Created by wqzhang on 17-2-5.
 */

public class BootComletedBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
//        Intent intent1 = new Intent("com.wqzhang.thingswapper.activity.MainActivity");
//        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(intent1);
        AlarmModel expiredAlarmModel = BusinessProcess.getInstance().readExpiredToDoThing();
        if (expiredAlarmModel != null) {
            //存在提醒时间已过,但还未提醒的事件
            //用Notification 提示

        }
        AlarmModel needNotifyAlarmModel = BusinessProcess.getInstance().readNeedNotifyToDoThings();
        if (needNotifyAlarmModel != null) {
            //存在需要提醒的事项
            //设置Alerm
            AlarmTimer.setAlarmTimer(needNotifyAlarmModel);
        }


    }
}
