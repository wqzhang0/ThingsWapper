package com.wqzhang.thingswapper.dao;

import android.util.Log;

import com.wqzhang.thingswapper.MainApplication;
import com.wqzhang.thingswapper.dao.greendao.*;
import com.wqzhang.thingswapper.model.HistoryData;
import com.wqzhang.thingswapper.tools.Common;

import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;

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
    static Connection_T_NDao connection_t_nDao;

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
            connection_t_nDao = daoSession.getConnection_T_NDao();
        }
    }


    @Override
    public User readOrAddUserInfo() {
        //查看数据库中是否有用户账户  如果没有 则新创建一个
        QueryBuilder<User> userQueryBuilder = userDao.queryBuilder();
        ArrayList<User> userArrayList = (ArrayList<User>) userQueryBuilder.list();
        User user;
        if (userArrayList.size() == 0) {
            user = new User();
            user.setCreateDate(new Date());
            user.setIsSynchronize(false);
            user.setName("default");
            user.setDefaultLoginAccount(true);
            userDao.insert(user);
        } else {
            user = userQueryBuilder.where(UserDao.Properties.DefaultLoginAccount.eq(true)).list().get(0);
        }
        return user;
    }

    @Override
    public ArrayList<ToDoThing> readFinshThings() {
        QueryBuilder<ToDoThing> finshQueryBuilder = toDoThingDao.queryBuilder();
        ArrayList<ToDoThing> toDoThings = (ArrayList<ToDoThing>) finshQueryBuilder.where(ToDoThingDao.Properties.Status.eq(Common.STATUS_FINSH)).list();
        return toDoThings;
    }

    @Override
    public ArrayList<ToDoThing> readNotDoneThings() {
        QueryBuilder<ToDoThing> notDoneQueryBuilder = toDoThingDao.queryBuilder();
        ArrayList<ToDoThing> toDoThings = (ArrayList<ToDoThing>) notDoneQueryBuilder.where(ToDoThingDao.Properties.Status.eq(Common.STATUS_TO_BE_DONE)).list();
        return toDoThings;
    }

    @Override
    public User getOnlineUser() {
        return null;
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
        Query<ToDoThing> todoThingQueue = toDoThingDao.queryBuilder().build();
        ArrayList<ToDoThing> toDoThings = (ArrayList<ToDoThing>) todoThingQueue.list();
        return toDoThings;
    }

    @Override
    public void deleteToDoTingById(Long id) {
        ToDoThing toDoThing = readThingById(id);
        if (toDoThing != null) {
            toDoThingDao.delete(toDoThing);
        }
    }

    @Override
    public ToDoThing readThingById(Long id) {
        ToDoThing toDoThing = null;
        QueryBuilder<ToDoThing> toDoThingQueryBuilder = toDoThingDao.queryBuilder();
        ArrayList<ToDoThing> toDoThings = (ArrayList<ToDoThing>) toDoThingQueryBuilder.where(ToDoThingDao.Properties.Id.eq(id)).list();
        if (toDoThings != null || toDoThings.size() != 0) {
            toDoThing = toDoThings.get(0);
        }
        return toDoThing;
    }

    @Override
    public void addToDoThing(ToDoThing toDoThing) {
        toDoThingDao.insert(toDoThing);

    }

    public void changeToDoThingState(Long id, int state) {
        QueryBuilder<ToDoThing> toDoThingQueryBuilder = toDoThingDao.queryBuilder();
        ToDoThing toDoThing = toDoThingQueryBuilder.where(ToDoThingDao.Properties.Id.eq(id)).list().get(0);
        toDoThing.setStatus(state);
        toDoThingDao.update(toDoThing);
    }

    @Override
    public void addToDoThing(ToDoThing toDoThing, List<Notification> notificationList) {
        ArrayList<Connection_T_N> connection_t_nArrayList = new ArrayList<>();
        toDoThing.setUser(readOrAddUserInfo());
        toDoThingDao.insert(toDoThing);

        for (Notification notification : notificationList) {
            Connection_T_N connection_t_n = new Connection_T_N();

            connection_t_n.setToDoThing(toDoThing);

            notificationDao.insert(notification);
            connection_t_n.setNotification(notification);

            connection_t_nArrayList.add(connection_t_n);
        }

        connection_t_nDao.insertInTx(connection_t_nArrayList);
    }


}
