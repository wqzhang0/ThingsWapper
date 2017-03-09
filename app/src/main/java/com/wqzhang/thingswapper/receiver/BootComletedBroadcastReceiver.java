package com.wqzhang.thingswapper.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.wqzhang.thingswapper.MainApplication;
import com.wqzhang.thingswapper.dao.BusinessProcess;
import com.wqzhang.thingswapper.model.AlarmDTO;
import com.wqzhang.thingswapper.util.AlarmTimer;

/**
 * Created by wqzhang on 17-2-5.
 */

public class BootComletedBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
//        Intent intent1 = new Intent("com.wqzhang.thingswapper.activity.MainActivity");
//        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(intent1);
        AlarmDTO expiredAlarmModel = BusinessProcess.getInstance().listExpiredThings();
        if (expiredAlarmModel != null) {
            //存在提醒时间已过,但还未提醒的事件
            //用Notification 提示
        }

        MainApplication.startScanService();
    }
}
