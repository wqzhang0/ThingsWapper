package com.wqzhang.thingswapper.dao;

import android.util.Log;

import com.wqzhang.thingswapper.dao.greendao.Connection_T_N;
import com.wqzhang.thingswapper.dao.greendao.Connection_T_NDao;
import com.wqzhang.thingswapper.dao.greendao.DaoSession;
import com.wqzhang.thingswapper.dao.greendao.Notification;
import com.wqzhang.thingswapper.dao.greendao.NotificationDao;
import com.wqzhang.thingswapper.dao.greendao.ToDoThing;
import com.wqzhang.thingswapper.dao.greendao.ToDoThingDao;
import com.wqzhang.thingswapper.dao.greendao.User;
import com.wqzhang.thingswapper.dao.greendao.UserDao;
import com.wqzhang.thingswapper.model.AlarmModel;
import com.wqzhang.thingswapper.model.ChartDataModel;
import com.wqzhang.thingswapper.tools.Common;
import com.wqzhang.thingswapper.tools.DateUtil;
import com.wqzhang.thingswapper.tools.NotifyParseUtil;

import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.Calendar;
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
    public ArrayList<ToDoThing> readFinshThingsFinshTimeDesc() {
        QueryBuilder<ToDoThing> finshQueryBuilder = toDoThingDao.queryBuilder();
        ArrayList<ToDoThing> toDoThings = (ArrayList<ToDoThing>) finshQueryBuilder.where(ToDoThingDao.Properties.Status.eq(Common.STATUS_FINSH)).orderDesc(ToDoThingDao.Properties.FinshDate).list();
        return toDoThings;
    }

    @Override
    public ArrayList<ToDoThing> readNotDoneThingsCreateTimeDesc() {
        QueryBuilder<ToDoThing> notDoneQueryBuilder = toDoThingDao.queryBuilder();
        ArrayList<ToDoThing> toDoThings = (ArrayList<ToDoThing>) notDoneQueryBuilder.where(ToDoThingDao.Properties.Status.eq(Common.STATUS_TO_BE_DONE)).orderDesc(ToDoThingDao.Properties.CreateDate).list();
        return toDoThings;
    }

    @Override
    public User getOnlineUser() {
        QueryBuilder<User> userQueryBuilder = userDao.queryBuilder();
        userQueryBuilder.where(UserDao.Properties.DefaultLoginAccount.eq(true));
        ArrayList<User> userArrayList = (ArrayList<User>) userQueryBuilder.list();
        return userArrayList.get(0);
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
        if (state == Common.STATUS_FINSH) {
            toDoThing.setFinshDate(new Date());
            ArrayList<Notification> notifications = new ArrayList<Notification>();
            ArrayList<Connection_T_N> connection_t_ns = (ArrayList<Connection_T_N>) toDoThing.getNotificationIds();
            for (int i = 0; i < connection_t_ns.size(); i++) {
                Notification _tmpNotification = connection_t_ns.get(i).getNotification();
                _tmpNotification.setAlearyNotify(true);
                notifications.add(_tmpNotification);
            }
            notificationDao.updateInTx(notifications);
        }
        toDoThing.setStatus(state);
        toDoThingDao.update(toDoThing);
    }

    @Override
    public AlarmModel readNeedNotifyToDoThings() {
        //找到需要提醒的事项,判断依据,
        // 是否提醒(根据 提醒类型判断)
        // 上次是否有提醒时间,
        // 提醒时间是否大于当前时间

        AlarmModel alarmModel = null;
        Date currentDate = DateUtil.getCurrentDate();
        ArrayList<ToDoThing> toDoThings = new ArrayList<>();

        QueryBuilder<Notification> notificationQueryBuilder = notificationDao.queryBuilder();
        notificationQueryBuilder.where(NotificationDao.Properties.PreNotifyDate.isNull(),
                NotificationDao.Properties.NotifyType.notEq(0),
                NotificationDao.Properties.ReminderDate.ge(currentDate),
                NotificationDao.Properties.AlearyNotify.eq(false))
                .orderAsc(NotificationDao.Properties.ReminderDate);

        ArrayList<Notification> notificationArrayList = (ArrayList<Notification>) notificationQueryBuilder.list();
        ToDoThing _tmpToDoThings;
        if (notificationArrayList.size() == 0) {
            return alarmModel;
        } else if (notificationArrayList.size() == 1) {
            _tmpToDoThings = notificationArrayList.get(0).getToDoThingIds().get(0).getToDoThing();
            if (_tmpToDoThings.getStatus() == Common.STATUS_TO_BE_DONE) {
                toDoThings.add(notificationArrayList.get(0).getToDoThingIds().get(0).getToDoThing());
                Integer reminderType = toDoThings.get(0).getReminderType();
                alarmModel = new AlarmModel(toDoThings, notificationArrayList.get(0).getReminderDate(), reminderType);
                return alarmModel;
            }

        } else if (notificationArrayList.size() > 1) {
            Notification recentNotification = notificationArrayList.get(0);
            for (Notification _NOTIFYCATION : notificationArrayList) {
                if (_NOTIFYCATION.getReminderDate().equals(recentNotification.getReminderDate())) {
                    toDoThings.add(_NOTIFYCATION.getToDoThingIds().get(0).getToDoThing());
                }
            }
            Integer reminderType = NotifyParseUtil.getNotifyType(toDoThings);
            alarmModel = new AlarmModel(toDoThings, notificationArrayList.get(0).getReminderDate(), reminderType);
            return alarmModel;
        }
        return null;
    }

    @Override
    public AlarmModel readExpiredToDoThing() {
        //找到需要提醒的事项,判断依据,
        // 是否提醒(根据 提醒类型判断)
        // 上次是否有提醒时间,
        // 提醒时间是否大于当前时间         //比较上一次提醒的时间,
        AlarmModel alarmModel = null;
        Date currentDate = DateUtil.getCurrentDate();
        ArrayList<ToDoThing> toDoThings = new ArrayList<>();

        QueryBuilder<Notification> notificationQueryBuilder = notificationDao.queryBuilder();
        notificationQueryBuilder.where(NotificationDao.Properties.PreNotifyDate.isNull(),
                NotificationDao.Properties.NotifyType.notEq(0),
                NotificationDao.Properties.ReminderDate.lt(currentDate),
                NotificationDao.Properties.AlearyNotify.eq(false))
                .orderAsc(NotificationDao.Properties.ReminderDate);

        ArrayList<Notification> notificationArrayList = (ArrayList<Notification>) notificationQueryBuilder.list();
        ArrayList<Long> notificationIds = new ArrayList<>();
        if (notificationArrayList.size() == 0) {
            return alarmModel;
        } else if (notificationArrayList.size() > 0) {
            for (int i = 0; i < notificationArrayList.size(); i++) {
                ToDoThing _tmpToDoThings = notificationArrayList.get(i).getToDoThingIds().get(0).getToDoThing();
                if (_tmpToDoThings.getStatus() == Common.STATUS_TO_BE_DONE) {
                    toDoThings.add(notificationArrayList.get(i).getToDoThingIds().get(0).getToDoThing());
                    notificationIds.add(notificationArrayList.get(i).getId());
                }
            }
//            Integer reminderType = NotifyParseUtil.getNotifyType(toDoThings);
            Integer reminderType = Common.REMINDER_TYPE_NONE;

            alarmModel = new AlarmModel(toDoThings, new Date(), reminderType, notificationIds);
            return alarmModel;
        }

        return null;
    }

    @Override
    public void setPreNotifyDate(Long notifyId, Date date) {
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
    public ArrayList<ChartDataModel> readRecentWeekNewThings() {
        ArrayList<ChartDataModel> arrayList = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.add(Calendar.DAY_OF_YEAR, -6);

        for (int i = 0; i < 7; i++) {
            Date dayStart = calendar.getTime();
            calendar.add(calendar.DATE, +1);
            Date dayEnd = calendar.getTime();

            QueryBuilder<ToDoThing> toDoThingQueryBuilder = toDoThingDao.queryBuilder();

            int size = toDoThingQueryBuilder
                    .where(ToDoThingDao.Properties.CreateDate.ge(dayStart), ToDoThingDao.Properties.CreateDate.lt(dayEnd))
                    .list().size();

            arrayList.add(new ChartDataModel(calendar.getTime(), size));
        }

        return arrayList;
    }

    @Override
    public ArrayList<ChartDataModel> readRecentWeekFinshThings() {
        ArrayList<ChartDataModel> arrayList = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.add(Calendar.DAY_OF_YEAR, -6);

        for (int i = 0; i < 7; i++) {
            Date dayStart = calendar.getTime();
            calendar.add(calendar.DATE, +1);
            Date dayEnd = calendar.getTime();

            QueryBuilder<ToDoThing> toDoThingQueryBuilder = toDoThingDao.queryBuilder();

            int size = toDoThingQueryBuilder
                    .where(ToDoThingDao.Properties.FinshDate.ge(dayStart), ToDoThingDao.Properties.FinshDate.lt(dayEnd))
                    .list().size();

            arrayList.add(new ChartDataModel(calendar.getTime(), size));
        }

        return arrayList;
    }

    @Override
    public ArrayList<ChartDataModel> readTodayThings() {

        ArrayList<ChartDataModel> arrayList = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        Date dayStart = calendar.getTime();
        calendar.add(calendar.DATE, +1);
        Date dayEnd = calendar.getTime();

        QueryBuilder<ToDoThing> toDoThingQueryBuilder = toDoThingDao.queryBuilder();

        int todayNewThingsCounts = toDoThingQueryBuilder
                .where(ToDoThingDao.Properties.CreateDate.ge(dayStart), ToDoThingDao.Properties.CreateDate.lt(dayEnd))
                .list().size();

        int todayFinshThingsCounts = toDoThingQueryBuilder
                .where(ToDoThingDao.Properties.FinshDate.ge(dayStart), ToDoThingDao.Properties.FinshDate.lt(dayEnd))
                .list().size();

        int toBeDoneThingsCounts = readNotDoneThingsCreateTimeDesc().size();

        //依次添加顺序 未做,新增,完成
        arrayList.add(new ChartDataModel(new Date(), toBeDoneThingsCounts));
        arrayList.add(new ChartDataModel(new Date(), todayNewThingsCounts));
        arrayList.add(new ChartDataModel(new Date(), todayFinshThingsCounts));
        return arrayList;
    }

    @Override
    public void addToDoThing(ToDoThing toDoThing, List<Notification> notificationList) {
        ArrayList<Connection_T_N> connection_t_nArrayList = new ArrayList<>();
        toDoThing.setUser(readOrAddUserInfo());
        try {
            toDoThingDao.insert(toDoThing);

            for (Notification notification : notificationList) {
                Connection_T_N connection_t_n = new Connection_T_N();

                connection_t_n.setToDoThing(toDoThing);

                notificationDao.insert(notification);
                connection_t_n.setNotification(notification);

                connection_t_nArrayList.add(connection_t_n);
            }

            connection_t_nDao.insertInTx(connection_t_nArrayList);
        } catch (Exception e) {
            Log.e("SQL", "添加失败");
        }

    }


}
