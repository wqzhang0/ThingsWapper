package com.wqzhang.thingswapper.services;

import android.app.AlertDialog;
import android.app.IntentService;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by wqzhang on 17-1-21.
 */

public class NotifyService extends IntentService {

    public NotifyService(String name) {
        super(name);
    }

    public NotifyService() {
        super("s");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("NotifyService", "recevied message onHandleIntent");

    }


    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("NotifyService", "recevied message");
        new AlertDialog.Builder(this).setTitle("闹钟")
                .setMessage("闹钟响了，起床啦，懒虫！")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).show();
    }


}
