package com.wqzhang.thingswapper.dao.dbOperation;

import com.wqzhang.thingswapper.dao.greendao.Connection_T_N;
import com.wqzhang.thingswapper.dao.greendao.DaoSession;
import com.wqzhang.thingswapper.dao.greendao.Notification;
import com.wqzhang.thingswapper.dao.greendao.NotificationDao;
import com.wqzhang.thingswapper.dao.greendao.ToDoThing;
import com.wqzhang.thingswapper.dao.dbOperation.impl.NotificationOperationImpl;
import com.wqzhang.thingswapper.util.DateUtil;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wqzhang on 17-3-2.
 */

public class NotificationOperation implements NotificationOperationImpl {
    static NotificationDao notificationDao;

    private NotificationOperation() {
    }

    public NotificationOperation(DaoSession mDaoSession) {
        notificationDao = mDaoSession.getNotificationDao();
    }

    @Override
    public void updateFinshNotifyByThing(ToDoThing toDoThing) {
        ArrayList<Notification> notifications = new ArrayList<Notification>();
        ArrayList<Connection_T_N> connection_t_ns = (ArrayList<Connection_T_N>) toDoThing.getNotificationIds();
        for (int i = 0; i < connection_t_ns.size(); i++) {
            Notification tmpNotification = connection_t_ns.get(i).getNotification();
            tmpNotification.setAlearyNotify(true);
            tmpNotification.setPreNotifyDate(new Date());
            tmpNotification.setInvalide(true);
            notifications.add(tmpNotification);
        }
        notificationDao.updateInTx(notifications);
    }

    @Override
    public ArrayList<Notification> listByIds(List<Long> ids) {
        QueryBuilder<Notification> notificationQueryBuilder = notificationDao.queryBuilder();
        notificationQueryBuilder.where(NotificationDao.Properties.Id.in(ids)).build();
        return (ArrayList<Notification>) notificationQueryBuilder.list();
    }


    @Override
    public ArrayList<Notification> listNoRepeatNotification() {
        long currentDate = DateUtil.getCurrentDate().getTime();
        //这里查询 的结果是不重复的提醒,判断依据是 :
        // 1 上次提醒时间为空;
        // 2 提醒类型不为空;
        // 3 设置的提醒时间大于此时;
        // 4 AlearyNotify为false
        // 5 invalide 字段为false
        // 6 repeatType 为 不重复
        //按照提醒时间倒叙排序
        QueryBuilder<Notification> noRepeatNotificationQueryBuilder = notificationDao.queryBuilder();
        noRepeatNotificationQueryBuilder.where(
                NotificationDao.Properties.PreNotifyDate.isNull(),
                NotificationDao.Properties.RepeatType.eq("不重复"),
                NotificationDao.Properties.NotifyType.notEq(0),
                NotificationDao.Properties.ReminderDate.ge(currentDate),
                NotificationDao.Properties.Invalide.eq(false),
                NotificationDao.Properties.AlearyNotify.eq(false))
                .orderAsc(NotificationDao.Properties.ReminderDate);
        ArrayList<Notification> noRepeatNotificationArrayList = (ArrayList<Notification>) noRepeatNotificationQueryBuilder.list();
        return noRepeatNotificationArrayList;
    }

    @Override
    public ArrayList<Notification> listRepeatNotification() {
        QueryBuilder<Notification> repeatNotificationQueryBuilder = notificationDao.queryBuilder();
        repeatNotificationQueryBuilder.where(
                NotificationDao.Properties.RepeatType.notEq("不重复"),
                NotificationDao.Properties.NotifyType.notEq(0),
                NotificationDao.Properties.Invalide.eq(false)
        ).orderAsc(NotificationDao.Properties.NextRemindDate);
        //提醒日期按照上次提醒的日期和创建的日期,和重复的类型 之间运算
        ArrayList<Notification> repeatNotificationArrayList = (ArrayList<Notification>) repeatNotificationQueryBuilder.list();

        return repeatNotificationArrayList;
    }

    @Override
    public ArrayList<Notification> listExpiredNotification() {
        long currentDate = DateUtil.getCurrentDate().getTime();
        QueryBuilder<Notification> notificationQueryBuilder = notificationDao.queryBuilder();
        notificationQueryBuilder.where(NotificationDao.Properties.PreNotifyDate.isNull(),
                NotificationDao.Properties.NotifyType.notEq(0),
                NotificationDao.Properties.ReminderDate.lt(currentDate),
                NotificationDao.Properties.AlearyNotify.eq(false))
                .orderAsc(NotificationDao.Properties.ReminderDate);
        ArrayList<Notification> notificationArrayList = (ArrayList<Notification>) notificationQueryBuilder.list();
        return notificationArrayList;
    }

    @Override
    public void updatePreNotifyDate(Long notifyId, Date date) {
        QueryBuilder<Notification> notificationQueryBuilder = notificationDao.queryBuilder();
        notificationQueryBuilder.where(NotificationDao.Properties.Id.eq(notifyId));
        ArrayList<Notification> notifications = (ArrayList<Notification>) notificationQueryBuilder.build().list();
        if (notifications.size() > 0) {
            notifications.get(0).setPreNotifyDate(date);
            notifications.get(0).setAlearyNotify(true);
        }
        notificationDao.update(notifications.get(0));
    }

    @Override
    public void save(Notification notification) {
        notificationDao.insert(notification);
    }

    @Override
    public void update(Notification notification) {
        notificationDao.update(notification);
    }

}
