package com.wqzhang.thingswapper.dao;

import com.wqzhang.greendao.DaoSession;
import com.wqzhang.greendao.NotificationDao;
import com.wqzhang.greendao.ToDoThing;
import com.wqzhang.greendao.ToDoThingDao;
import com.wqzhang.greendao.User;
import com.wqzhang.greendao.UserDao;

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
    public ArrayList<ToDoThing> readAllThings(User user) {
        return null;
    }

    @Override
    public void addToDoThing(ToDoThing toDoThing) {

    }
}
