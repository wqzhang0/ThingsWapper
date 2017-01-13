package com.wqzhang.thingswapper;

import android.app.Application;
import android.content.Context;

import com.wqzhang.thingswapper.dao.AddThingOperationXMLData;
import com.wqzhang.thingswapper.dao.BusinessProcess;
import com.wqzhang.thingswapper.dao.SharedPreferencesControl;
import com.wqzhang.thingswapper.dao.greendao.DaoMaster;
import com.wqzhang.thingswapper.dao.greendao.DaoSession;


import org.greenrobot.greendao.database.Database;

/**
 * Created by wqzhang on 16-12-20.
 */

public class MainApplication extends Application {
    private static Context mContext;
    private static DaoSession daoSession;


    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this.getApplicationContext();

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "ThingsWapper-db");
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();

        BusinessProcess.init(daoSession);
        SharedPreferencesControl.init(mContext);

        AddThingOperationXMLData.getInstall().init();


        BusinessProcess.getInstance().readOrAddUserInfo();


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
