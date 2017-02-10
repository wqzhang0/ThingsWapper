package com.wqzhang.thingswapper.activitys;

import android.app.Fragment;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.wqzhang.thingswapper.MainApplication;
import com.wqzhang.thingswapper.R;
import com.wqzhang.thingswapper.Receivers.NotifyReceiver;
import com.wqzhang.thingswapper.dao.BusinessProcess;
import com.wqzhang.thingswapper.fragments.PersonSetFragment;
import com.wqzhang.thingswapper.fragments.PoolFragment;
import com.wqzhang.thingswapper.fragments.ShowThingsFragment;
import com.wqzhang.thingswapper.model.AlarmModel;
import com.wqzhang.thingswapper.tools.AlarmTimer;
import com.wqzhang.thingswapper.tools.Common;
import com.wqzhang.thingswapper.tools.DialogUtil;
import com.wqzhang.thingswapper.vus.MainVu;

import java.util.Calendar;


public class MainActivity extends BasePartenerAppCompatActivity<MainVu> implements View.OnClickListener {

    private String TAG = "MainActivity";

    private Fragment showThingsFragment, poolFragment, personSetFragment;

    @Override
    protected void onBind() {
        super.onBind();


        showThingsFragment = ShowThingsFragment.newInstance();
        poolFragment = PoolFragment.newInstance();
        personSetFragment = PersonSetFragment.newInstance();

        vu.getNavigationCharts().setOnClickListener(this);
        vu.getNavigationSetting().setOnClickListener(this);
        vu.getNavigationShowThings().setOnClickListener(this);

        vu.initFragment(showThingsFragment, fragmentManager);

        new Thread(new Runnable() {
            @Override
            public void run() {
                AlarmModel needNotifyAlarmModel = BusinessProcess.getInstance().readNeedNotifyToDoThings();
                if (needNotifyAlarmModel != null) {
                    //存在需要提醒的事项
                    //设置Alerm
                    AlarmTimer.setAlarmTimer(needNotifyAlarmModel);
                }
            }
        }).start();

    }

    @Override
    protected void onAfterResume() {
        super.onAfterResume();
        AlarmModel alarmModel = BusinessProcess.getInstance().readExpiredToDoThing();
        if (alarmModel != null && alarmModel.getToDoThingsContent().size() > 0) {
            DialogUtil.showHistoryThings(this, alarmModel.getToDoThingsContent());
        }
    }

    @Override
    protected Class<MainVu> getVuClass() {
        return MainVu.class;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.show_things:
                vu.switchContent(showThingsFragment, fragmentManager);
                break;
            case R.id.pool:
                vu.switchContent(poolFragment, fragmentManager);
                break;
            case R.id.setting:
                vu.switchContent(personSetFragment, fragmentManager);
                AlarmModel needNotifyAlarmModel = BusinessProcess.getInstance().readNeedNotifyToDoThings();
                if (needNotifyAlarmModel != null) {
                    //存在需要提醒的事项
                    //设置Alerm
                    Context context = MainApplication.getGlobleContext();
                    if (needNotifyAlarmModel == null) {
                        Log.e(TAG, "AlarmModel is null ,return request");
                    }


                    Intent intent = new Intent(context, NotifyReceiver.class);

                    Bundle bundle = new Bundle();
                    bundle.putParcelable(Common.INTENT_PARCELABLE_KEY, needNotifyAlarmModel);
                    bundle.putString(Common.NOTIFY_TYPE, Common.NOTIFY_NEW_MEG);

                    intent.putExtra(Common.INTENT_KEY_BUNDLE_KEY, bundle);

                    Calendar calendar = Calendar.getInstance();
//        calendar.setTime(alarmDate);
                    calendar.add(Calendar.MINUTE, 1);
                    sendBroadcast(intent);
                }
                break;
            default:
                break;
        }

    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //清除所有的通知信息
        NotificationManager notificationManager = (NotificationManager) MainApplication.getGlobleContext().getSystemService(NOTIFICATION_SERVICE);


        notificationManager.cancelAll();
    }


}
