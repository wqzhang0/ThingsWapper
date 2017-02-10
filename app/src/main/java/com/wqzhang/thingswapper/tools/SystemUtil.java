package com.wqzhang.thingswapper.tools;

import android.app.ActivityManager;
import android.content.Context;

import com.wqzhang.thingswapper.MainApplication;

import java.util.List;

/**
 * Created by wqzhang on 17-2-6.
 */

public class SystemUtil {
    public static final String APP_PACKAGE = "com.wqzhang.thingswapper";

    public static boolean isBackground(String pakegeName) {
        ActivityManager activityManager = (ActivityManager) MainApplication.getGlobleContext().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(pakegeName)) {
                if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    return false;
                } else {
                    return true;
                }
            }
        }
        return true;
    }
}
