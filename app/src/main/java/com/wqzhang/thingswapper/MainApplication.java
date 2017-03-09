package com.wqzhang.thingswapper;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.wqzhang.thingswapper.dao.AddThingOperationXMLData;
import com.wqzhang.thingswapper.dao.AddThingOperationXMLDataCache;
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
    private static Context dialogContext;


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


        BusinessProcess.getInstance().getOrAddUserInfo();
        //初始化未保存事项 的历史数据 作为缓存
        AddThingOperationXMLDataCache.readHistory();


//        DevOpenHelper helper = new DevOpenHelper(this, "notes-db");
//        Database db = helper.getWritableDb();
//        daoSession = new DaoMaster(db).newSession();
//        DevOpenHelper helper = new DevOpenHelper(this, ENCRYPTED ? "notes-db-encrypted" : "notes-db");
//        Database db = ENCRYPTED ? helper.getEncryptedWritableDb("super-secret") : helper.getWritableDb();
//        daoSession = new DaoMaster(db).newSession();


        //应用开启,设置新的闹铃
        MainApplication.startScanService();
    }

    public static DaoSession getDaoSession() {
        return daoSession;
    }

    public static Context getGlobleContext() {
        return mContext;
    }


    public static Context getDialogContext() {
        return dialogContext;
    }

    public static void setDialogContext(Context dialogContext) {
        MainApplication.dialogContext = dialogContext;
    }

    public static void startScanService() {
        Intent scanIntent = new Intent("com.wqzhang.thingswapper.service.ScanService");
        scanIntent.setPackage("com.wqzhang.thingswapper");
        mContext.startService(scanIntent);
    }
}
