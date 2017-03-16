package com.wqzhang.thingswapper.activity;

import android.app.Fragment;
import android.app.NotificationManager;
import android.content.Intent;
import android.view.View;

import com.wqzhang.thingswapper.MainApplication;
import com.wqzhang.thingswapper.R;
import com.wqzhang.thingswapper.dao.BusinessProcessImpl;
import com.wqzhang.thingswapper.fragment.BasePartenerFragment;
import com.wqzhang.thingswapper.fragment.PersonSetFragment;
import com.wqzhang.thingswapper.fragment.PoolFragment;
import com.wqzhang.thingswapper.fragment.ShowThingsFragment;
import com.wqzhang.thingswapper.listener.AcitivityLinkCallBackListener;
import com.wqzhang.thingswapper.model.AlarmDTO;
import com.wqzhang.thingswapper.util.DialogUtil;
import com.wqzhang.thingswapper.vu.MainVu;


public class MainActivity extends BasePartenerAppCompatActivity<MainVu> implements View.OnClickListener, AcitivityLinkCallBackListener {

    private String TAG = "MainActivity";

    private Fragment showThingsFragment, poolFragment, personSetFragment;

    @Override
    protected void onBind() {
        super.onBind();
        showThingsFragment = ShowThingsFragment.newInstance();
        poolFragment = PoolFragment.newInstance();
        personSetFragment = PersonSetFragment.newInstance();
        ((BasePartenerFragment) personSetFragment).setCallBack(this);

        vu.getNavigationCharts().setOnClickListener(this);
        vu.getNavigationSetting().setOnClickListener(this);
        vu.getNavigationShowThings().setOnClickListener(this);
        vu.getFab().setOnClickListener(this);
        vu.initFragment(showThingsFragment, fragmentManager);

    }

    @Override
    View getRootView() {
        return findViewById(R.id.root_view);
    }

    @Override
    protected void onAfterResume() {
        super.onAfterResume();
        AlarmDTO alarmModel = BusinessProcessImpl.getInstance().listOnlineUserExpiredThings();
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
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                vu.switchContent(personSetFragment, fragmentManager);
//                AlarmModel needNotifyAlarmModel = BusinessProcess.getInstance().listOnlineUserRecentNeedNotifyThings();
//
//
//                if (needNotifyAlarmModel != null) {
//                    //存在需要提醒的事项
//                    //设置Alerm
//                    Context context = MainApplication.getGlobleContext();
//                    if (needNotifyAlarmModel == null) {
//                        Log.e(TAG, "AlarmModel is null ,return request");
//                    }
//
//
//                    Intent intent = new Intent(context, NotifyReceiver.class);
//
//                    Bundle bundle = new Bundle();
//                    bundle.putParcelable(Common.INTENT_PARCELABLE_KEY, needNotifyAlarmModel);
//                    bundle.putString(Common.NOTIFY_TYPE, Common.NOTIFY_NEW_MEG);
//
////                    intent.putExtra(Common.INTENT_KEY_BUNDLE_KEY, bundle);
//                    intent.putExtras(bundle);
//                    Calendar calendar = Calendar.getInstance();
////        calendar.setTime(alarmDate);
//                    calendar.add(Calendar.MINUTE, 1);
//                    sendBroadcast(intent);
//                }
                break;
            case R.id.fab:
                Intent intent = new Intent("com.wqzhang.thingswapper.activity.AddToDoThingActivity");
                startActivity(intent);
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


    @Override
    public void callBack(int type) {
        if (type == 1) {
            showBaffle("正在进行同步操作");
        } else if (type == 2) {
            hideBaffle();
        }
    }
}
