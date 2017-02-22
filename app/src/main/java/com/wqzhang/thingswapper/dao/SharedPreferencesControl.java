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

    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    public static SharedPreferencesControl getInstanll() throws CustomerException {

        if (sharedPreferencesControl == null) {
            throw new CustomerException("SharedPreferencesControl 没有被初始化 ");

        }
        return sharedPreferencesControl;
    }

    public static void init(Context context) {
        mContext = context;
        sharedPreferencesControl = new SharedPreferencesControl();
        sharedPreferences = mContext.getSharedPreferences("unAddPref", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }


    public SharedPreferences.Editor getEditor() {
        return editor;
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

}
