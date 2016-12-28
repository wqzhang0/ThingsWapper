package com.wqzhang.thingswapper;

import android.app.Application;
import android.content.Context;

import com.wqzhang.greendao.DaoMaster;
import com.wqzhang.greendao.DaoSession;
import com.wqzhang.thingswapper.dao.BusinessProcess;
import com.wqzhang.thingswapper.db.DatebaseHelper;
import com.wqzhang.thingswapper.model.User_model;


import org.greenrobot.greendao.database.Database;

import java.util.Date;

/**
 * Created by wqzhang on 16-12-20.
 */

public class MainApplication extends Application {
    private static Context mContext;
    private  static DaoSession daoSession;


    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this.getApplicationContext();

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "ThingsWapper-db");
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();

        BusinessProcess.init(daoSession);
//        DevOpenHelper helper = new DevOpenHelper(this, "notes-db");
//        Database db = helper.getWritableDb();
//        daoSession = new DaoMaster(db).newSession();
//        DevOpenHelper helper = new DevOpenHelper(this, ENCRYPTED ? "notes-db-encrypted" : "notes-db");
//        Database db = ENCRYPTED ? helper.getEncryptedWritableDb("super-secret") : helper.getWritableDb();
//        daoSession = new DaoMaster(db).newSession();


    }

    public static DaoSession getDaoSession() {
        return daoSession;
    }

    public static Context getGlobleContext() {
        return mContext;
    }
}
