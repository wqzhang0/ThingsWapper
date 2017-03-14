package com.wqzhang.thingswapper.dao;

import android.content.Context;
import android.content.SharedPreferences;

import com.wqzhang.thingswapper.exception.CustomerException;

/**
 * Created by wqzhang on 17-1-4.
 */

public class SharedPreferencesControl {
    private static Context mContext;
    private static SharedPreferencesControl sharedPreferencesControl;

    //用户临时操作数据保存
    private static SharedPreferences userDataCacheSP;
    private static SharedPreferences.Editor userDataCacheEditor;
    //用户登录账号信息保存 暂时使用数据库保存
    private static SharedPreferences userInfoSP;
    private static SharedPreferences.Editor userInfoEditor;

    public static SharedPreferencesControl getInstanll() throws CustomerException {

        if (sharedPreferencesControl == null) {
            throw new CustomerException("SharedPreferencesControl 没有被初始化 ");

        }
        return sharedPreferencesControl;
    }

    public static void init(Context context) {
        mContext = context;
        sharedPreferencesControl = new SharedPreferencesControl();
        userDataCacheSP = mContext.getSharedPreferences("userDataCachePref", Context.MODE_PRIVATE);
        userInfoSP = mContext.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        userDataCacheEditor = userDataCacheSP.edit();
        userInfoEditor = userInfoSP.edit();
    }


    public SharedPreferences.Editor getUserDataCacheEditor() {
        return userDataCacheEditor;
    }

    public SharedPreferences getUserDataCacheSP() {
        return userDataCacheSP;
    }

    public static SharedPreferences getUserInfoSP() {
        return userInfoSP;
    }

    public static SharedPreferences.Editor getUserInfoEditor() {
        return userInfoEditor;
    }
}
