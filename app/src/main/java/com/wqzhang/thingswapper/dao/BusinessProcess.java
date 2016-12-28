package com.wqzhang.thingswapper.dao;

import com.wqzhang.thingswapper.dao.greendao.*;

import java.util.ArrayList;

/**
 * Created by wqzhang on 16-12-28.
 */

public class BusinessProcess implements BusinessProcessImpl {
    static BusinessProcess businessProcess;
    static DaoSession daoSession;
    static ToDoThingDao toDoThingDao;
    static UserDao userDao;
    static NotificationDao notificationDao;

    public static BusinessProcess getInstance() {
        return businessProcess;
    }

    private BusinessProcess() {
    }

    public static void init(DaoSession mDaoSession) {
        if (businessProcess == null) {
            businessProcess = new BusinessProcess();
            daoSession = mDaoSession;
            toDoThingDao = daoSession.getToDoThingDao();
            userDao = daoSession.getUserDao();
            notificationDao = daoSession.getNotificationDao();
        }


    }


    @Override
    public User readOrAddUserInfo() {
        return null;
    }

    @Override
    public void addToDoThing(ToDoThing toDoThing) {

    }

    @Override
    public ArrayList<ToDoThing> readAllThings(User user) {
        return null;
    }
}
