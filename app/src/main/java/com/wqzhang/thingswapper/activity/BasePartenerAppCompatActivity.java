package com.wqzhang.thingswapper.activity;

import android.app.FragmentManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.view.WindowManager;

import com.wqzhang.thingswapper.MainApplication;
import com.wqzhang.thingswapper.R;
import com.wqzhang.thingswapper.util.DialogUtil;
import com.wqzhang.thingswapper.vu.Vu;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

/**
 * Created by wqzhang on 17-1-5.
 */

public abstract class BasePartenerAppCompatActivity<V extends Vu> extends AppCompatActivity {
    protected V vu;
    protected FragmentManager fragmentManager;
    protected EventBus bus;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = getFragmentManager();
        bus = EventBus.getDefault();
        try {
            vu = getVuClass().newInstance();
            vu.init(getLayoutInflater(), null);
            setContentView(vu.getView());
            onBind();
            MainApplication.setDialogContext(this);



        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    public void showNotifyNow(ArrayList<String> msg) {
        DialogUtil.showNotifyNow(this, msg);
    }

    @Override
    protected void onStop() {
        beforeOnStop();
        super.onStop();
    }

    protected void beforeOnStop() {

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();
        onAfterResume();
    }

    protected void onAfterResume(){

    }


    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        beforeDestroy();
        onDestroyVu();
        vu = null;
        super.onDestroy();
    }

    @Override
    public final void onBackPressed() {
        if (!handleBackPressed()) {
            super.onBackPressed();
        }
    }

    public boolean handleBackPressed() {
        return false;
    }


    protected abstract Class<V> getVuClass();

    protected void onBind() {
    }

    protected void beforeDestroy() {
    }


    protected void onDestroyVu() {
    }


    public void showNotification(Intent sourceInent) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentText("吃早饭").setContentTitle("ThingsWapper提醒").setSmallIcon(R.drawable.finsh_light);

        Intent appIntent = new Intent(Intent.ACTION_MAIN);
        appIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        appIntent.setComponent(new ComponentName(this.getPackageName(), this.getPackageName() + "." + this.getLocalClassName()));
        appIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);//关键的一步，设置启动模式
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, appIntent, 0);

        builder.setContentIntent(contentIntent);

        Notification notification = builder.build();
        notification.flags = Notification.FLAG_AUTO_CANCEL;

        NotificationManager notificationManager = (NotificationManager) this.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification);
    }

    public void aa() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainApplication.getDialogContext());
        dialogBuilder.setTitle("下线通知");
        dialogBuilder.setMessage("你的账户在其他地方登录 Over");
        dialogBuilder.setCancelable(false);
        dialogBuilder.setPositiveButton("重新登录",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        AlertDialog alertDialog = dialogBuilder.create();
//        alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_TOAST);
        alertDialog.show();
    }

}
