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
import com.wqzhang.thingswapper.model.AlarmDTO;
import com.wqzhang.thingswapper.model.ChartDataDTO;
import com.wqzhang.thingswapper.util.Common;
import com.wqzhang.thingswapper.util.DateUtil;
import com.wqzhang.thingswapper.util.NotifyParseUtil;

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
            user.setSynchronize(false);
            user.setName("default");
            user.setDefaultLoginAccount(true);
            userDao.insert(user);
        } else {
            user = userQueryBuilder.where(UserDao.Properties.DefaultLoginAccount.eq(true)).list().get(0);
        }
        return user;
    }

    @Override
    public ArrayList<ToDoThing> listFinshThingsOrderByFinshTimeDesc() {
        QueryBuilder<ToDoThing> finshQueryBuilder = toDoThingDao.queryBuilder();
        ArrayList<ToDoThing> toDoThings = (ArrayList<ToDoThing>) finshQueryBuilder.where(ToDoThingDao.Properties.Status.eq(Common.STATUS_FINSH)).orderDesc(ToDoThingDao.Properties.FinshDate).list();
        return toDoThings;
    }

    @Override
    public ArrayList<ToDoThing> listNotDoneThingsOrderByCreateTimeDesc() {
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
    public ArrayList<ToDoThing> listAllThingsByUser(User user) {
        return null;
    }

    @Override
    public ArrayList<ToDoThing> listAllThingsByUserId(int userId) {
        return null;
    }

    @Override
    public ArrayList<ToDoThing> listAllThings() {
        Query<ToDoThing> todoThingQueue = toDoThingDao.queryBuilder().build();
        ArrayList<ToDoThing> toDoThings = (ArrayList<ToDoThing>) todoThingQueue.list();
        return toDoThings;
    }

    @Override
    public void removeToDoTingById(Long id) {
        ToDoThing toDoThing = getThingById(id);
        if (toDoThing != null) {
            toDoThingDao.delete(toDoThing);
        }
    }

    @Override
    public ToDoThing getThingById(Long id) {
        ToDoThing toDoThing = null;
        QueryBuilder<ToDoThing> toDoThingQueryBuilder = toDoThingDao.queryBuilder();
        ArrayList<ToDoThing> toDoThings = (ArrayList<ToDoThing>) toDoThingQueryBuilder.where(ToDoThingDao.Properties.Id.eq(id)).list();
        if (toDoThings != null || toDoThings.size() != 0) {
            toDoThing = toDoThings.get(0);
        }
        return toDoThing;
    }

    @Override
    public void saveThing(ToDoThing toDoThing) {
        toDoThingDao.insert(toDoThing);

    }

    @Override
    public void updateThingState(Long id, int state) {
        QueryBuilder<ToDoThing> toDoThingQueryBuilder = toDoThingDao.queryBuilder();
        ToDoThing toDoThing = toDoThingQueryBuilder.where(ToDoThingDao.Properties.Id.eq(id)).list().get(0);
        if (state == Common.STATUS_FINSH) {
            toDoThing.setFinshDate(new Date());
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
        toDoThing.setStatus(state);
        toDoThingDao.update(toDoThing);
    }

    @Override
    public AlarmDTO listNeedNotifyThings() {
        //找到需要提醒的事项,判断依据,
        // 是否提醒(根据 提醒类型判断)
        // 上次是否有提醒时间,
        // 提醒时间是否大于当前时间

        AlarmDTO alarmModel = null;
        long currentDate = DateUtil.getCurrentDate().getTime();
        ArrayList<ToDoThing> toDoThings = new ArrayList<>();


        //这里查询 的结果是不重复的提醒,判断依据是 :
        // 1 上次提醒时间为空;
        // 2 提醒类型不为空;
        // 3 设置的提醒时间大于此时;
        // 4 AlearyNotify为false
        // 5 invalide 字段为false
        //按照提醒时间倒叙排序
        QueryBuilder<Notification> noRepeatNotificationQueryBuilder = notificationDao.queryBuilder();
        noRepeatNotificationQueryBuilder.where(
                NotificationDao.Properties.PreNotifyDate.isNull(),
                NotificationDao.Properties.NotifyType.notEq(0),
                NotificationDao.Properties.ReminderDate.ge(currentDate),
                NotificationDao.Properties.Invalide.eq(false),
                NotificationDao.Properties.AlearyNotify.eq(false))
                .orderAsc(NotificationDao.Properties.ReminderDate);

        ArrayList<Notification> noRepeatNotificationArrayList = (ArrayList<Notification>) noRepeatNotificationQueryBuilder.list();

        //这里负责查询重复的提醒
        //查询的依据是
        // 1.重复提醒类型不是"不重复";
        // 2.invalide 字段为false
        QueryBuilder<Notification> repeatNotificationQueryBuilder = notificationDao.queryBuilder();
        repeatNotificationQueryBuilder.where(
                NotificationDao.Properties.RepeatType.notEq("不重复"),
                NotificationDao.Properties.Invalide.eq(false)
        ).orderAsc(NotificationDao.Properties.NextRemindDate);
        //提醒日期按照上次提醒的日期和创建的日期,和重复的类型 之间运算
        ArrayList<Notification> repeatNotificationArrayList = (ArrayList<Notification>) repeatNotificationQueryBuilder.list();

        if (noRepeatNotificationArrayList.size() == 0 && repeatNotificationArrayList.size() == 0) {
            return alarmModel;
        } else if (noRepeatNotificationArrayList.size() > 0 && repeatNotificationArrayList.size() == 0) {
            return NotifyParseUtil.getRecentNoRepeatNotifys(noRepeatNotificationArrayList);
        } else if (noRepeatNotificationArrayList.size() == 0 && repeatNotificationArrayList.size() > 0) {
            return NotifyParseUtil.getRecentRepeatNotifys(repeatNotificationArrayList);
        } else if (noRepeatNotificationArrayList.size() > 0 && repeatNotificationArrayList.size() > 0) {
            //如果不重复的提醒时间 大于 重复提醒的  下一次提醒时间
            if (noRepeatNotificationArrayList.get(0).getReminderDate().getTime() > repeatNotificationArrayList.get(0).getNextRemindDate().getTime()) {
                //返回 不重复提醒时间
                return NotifyParseUtil.getRecentNoRepeatNotifys(noRepeatNotificationArrayList);
            } else if (noRepeatNotificationArrayList.get(0).getReminderDate().getTime() == repeatNotificationArrayList.get(0).getNextRemindDate().getTime()) {
                //如果两者需要提醒的时间相等
                return NotifyParseUtil.getRecentNotifys(noRepeatNotificationArrayList, repeatNotificationArrayList);
            } else if (noRepeatNotificationArrayList.get(0).getReminderDate().getTime() < repeatNotificationArrayList.get(0).getNextRemindDate().getTime()) {
                //返回重复提醒的时间
                return NotifyParseUtil.getRecentRepeatNotifys(repeatNotificationArrayList);
            }
        }
        return alarmModel;
    }

    @Override
    public AlarmDTO listExpiredThings() {
        //找到需要提醒的事项,判断依据,
        // 是否提醒(根据 提醒类型判断)
        // 上次是否有提醒时间,
        // 提醒时间是否大于当前时间         //比较上一次提醒的时间,
        AlarmDTO alarmModel = null;
        long currentDate = DateUtil.getCurrentDate().getTime();
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

            alarmModel = new AlarmDTO(toDoThings, new Date(), reminderType, notificationIds);
            return alarmModel;
        }

        return null;
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
    public ArrayList<ChartDataDTO> countRecentWeekNewThings() {
        ArrayList<ChartDataDTO> arrayList = new ArrayList<>();

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

            arrayList.add(new ChartDataDTO(calendar.getTime(), size));
        }

        return arrayList;
    }

    @Override
    public ArrayList<ChartDataDTO> countRecentWeekFinshThings() {
        ArrayList<ChartDataDTO> arrayList = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.add(Calendar.DAY_OF_YEAR, -6);

        for (int i = 0; i < 7; i++) {
            long dayStart = calendar.getTime().getTime();
            calendar.add(calendar.DATE, +1);
            long dayEnd = calendar.getTime().getTime();

            QueryBuilder<ToDoThing> toDoThingQueryBuilder = toDoThingDao.queryBuilder();

            int size = toDoThingQueryBuilder
                    .where(ToDoThingDao.Properties.FinshDate.ge(dayStart), ToDoThingDao.Properties.FinshDate.lt(dayEnd))
                    .list().size();

            arrayList.add(new ChartDataDTO(calendar.getTime(), size));
        }

        return arrayList;
    }

    @Override
    public ArrayList<ChartDataDTO> countTodayThings() {

        ArrayList<ChartDataDTO> arrayList = new ArrayList<>();

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

        int toBeDoneThingsCounts = listNotDoneThingsOrderByCreateTimeDesc().size();

        //依次添加顺序 未做,新增,完成
        arrayList.add(new ChartDataDTO(new Date(), toBeDoneThingsCounts));
        arrayList.add(new ChartDataDTO(new Date(), todayNewThingsCounts));
        arrayList.add(new ChartDataDTO(new Date(), todayFinshThingsCounts));
        return arrayList;
    }

    @Override
    public void saveThing(ToDoThing toDoThing, List<Notification> notificationList) {
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
