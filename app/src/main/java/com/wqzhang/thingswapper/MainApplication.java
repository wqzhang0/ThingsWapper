package com.wqzhang.thingswapper;

import android.app.Application;
import android.content.Context;

import com.wqzhang.thingswapper.db.DatebaseHelper;
import com.wqzhang.thingswapper.model.UserModel;

import java.util.Date;

/**
 * Created by wqzhang on 16-12-20.
 */

public class MainApplication extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this.getApplicationContext();
        DatebaseHelper.getInstance();

        UserModel userModel = new UserModel(1, "wqzhang", "bate1217", "Ag958868", "@163.com", new Date());
        DatebaseHelper.getInstance().addUser(userModel);
    }

    public static Context getGlobleContext() {
        return mContext;
    }
}
