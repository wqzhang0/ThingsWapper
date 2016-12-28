package com.wqzhang.thingswapper.dao;

import android.util.Log;

import com.wqzhang.thingswapper.MainApplication;
import com.wqzhang.thingswapper.dao.greendao.*;

import org.greenrobot.greendao.query.Query;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        Query<User> userQuery = userDao.queryBuilder().build();
        ArrayList<User> userArrayList = (ArrayList<User>) userQuery.list();

        User user;
        if (userArrayList.size() == 0) {
            user = new User();
            user.setCreateDate(new Date(System.currentTimeMillis()));
            userDao.insert(user);
        } else {
            user = userArrayList.get(0);
            Log.e("User_Info", user.toString());
        }
        return user;
    }

    @Override
    public ArrayList<ToDoThing> readAllThingsByUser(User user) {
        return null;
    }

    @Override
    public ArrayList<ToDoThing> readAllThingsByUserId(int userId) {
        return null;
    }

    @Override
    public ArrayList<ToDoThing> readAllThings() {
        Query<ToDoThing> todoThingQueue =  toDoThingDao.queryBuilder().build();
        ArrayList<ToDoThing> toDoThings = (ArrayList<ToDoThing>) todoThingQueue.list();
        return toDoThings;
    }

    @Override
    public void addToDoThing(ToDoThing toDoThing) {
        toDoThingDao.insert(toDoThing);

    }

    @Override
    public void addToDoThing(ToDoThing toDoThing, List<Notification> notificationList) {

    }

    @Override
    public ArrayList<ToDoThing> readFinshThings() {
        return null;
    }

    @Override
    public ArrayList<ToDoThing> readNotDoneThings() {
        return null;
    }

    @Override
    public User getOnlineUser() {
        return null;
    }

}
