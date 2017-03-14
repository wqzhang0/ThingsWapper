package com.wqzhang.thingswapper.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.wqzhang.thingswapper.dao.BusinessProcessImpl;
import com.wqzhang.thingswapper.model.AlarmDTO;
import com.wqzhang.thingswapper.util.AlarmTimer;

/**
 * Created by wqzhang on 17-3-7.
 */

public class ScanService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("ScanService", "onBind");
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("ScanService", "onStartCommand");
        AlarmDTO needNotifyAlarmModel = BusinessProcessImpl.getInstance().listRecentNeedNotifyThings();
        if (needNotifyAlarmModel != null) {
            //设置Alerm
            AlarmTimer.setAlarmTimer(needNotifyAlarmModel);
        }
        return super.onStartCommand(intent, flags, startId);
    }

}
