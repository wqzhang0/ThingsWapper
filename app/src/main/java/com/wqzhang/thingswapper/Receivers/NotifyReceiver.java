package com.wqzhang.thingswapper.Receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by wqzhang on 17-1-21.
 */

public class NotifyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("NotifyReceiver msg", "NotifyReceiver");
//        Toast.makeText(context, "naol", Toast.LENGTH_LONG).show();
    }
}
