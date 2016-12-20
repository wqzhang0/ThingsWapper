package com.wqzhang.thingswapper;

import android.app.Application;
import android.content.Context;

/**
 * Created by wqzhang on 16-12-20.
 */

public class MainApplication extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this.getApplicationContext();
    }

    public static Context getGlobleContext(){
        return mContext;
    }
}
