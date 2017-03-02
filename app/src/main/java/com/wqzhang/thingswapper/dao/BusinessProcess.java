package com.wqzhang.thingswapper.dao;

import android.util.Log;

import com.wqzhang.thingswapper.dao.dbOperation.ConnectionTNOperation;
import com.wqzhang.thingswapper.dao.dbOperation.NotificationOperation;
import com.wqzhang.thingswapper.dao.dbOperation.ToDoThingsOperation;
import com.wqzhang.thingswapper.dao.dbOperation.UserOperation;
import com.wqzhang.thingswapper.dao.greendao.Connection_T_N;
import com.wqzhang.thingswapper.dao.greendao.DaoSession;
import com.wqzhang.thingswapper.dao.greendao.Notification;
import com.wqzhang.thingswapper.dao.greendao.ToDoThing;
import com.wqzhang.thingswapper.dao.greendao.User;
import com.wqzhang.thingswapper.model.AlarmDTO;
import com.wqzhang.thingswapper.model.ChartDataDTO;
import com.wqzhang.thingswapper.util.Common;
import com.wqzhang.thingswapper.util.NotifyParseUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wqzhang on 16-12-28.
 */

public class BusinessProcess implements BusinessProcessImpl {
    static BusinessProcess businessProcess;
    static DaoSession daoSession;
    static ToDoThingsOperation toDoThingsOperation;
    static UserOperation userOperation;
    static NotificationOperation notificationOperation;
    static ConnectionTNOperation connectionTNOperation;

    public static BusinessProcess getInstance() {
        return businessProcess;
    }

    private BusinessProcess() {
    }

    public static void init(DaoSession mDaoSession) {
        if (businessProcess == null) {
            businessProcess = new BusinessProcess();
            daoSession = mDaoSession;
            toDoThingsOperation = new ToDoThingsOperation(mDaoSession);
            userOperation = new UserOperation(mDaoSession);
            notificationOperation = new NotificationOperation(mDaoSession);
            connectionTNOperation = new ConnectionTNOperation(mDaoSession);
        }
    }


    @Override
    public User getOrAddUserInfo() {
        //查看数据库中是否有用户账户  如果没有 则新创建一个
        ArrayList<User> userArrayList = userOperation.listUser();
        if (userArrayList.size() == 0) {
            userOperation.insertDefaultUser();
        }
        User user = userOperation.listUser().get(0);
        return user;
    }

    @Override
    public ArrayList<ToDoThing> listFinshThingsOrderByFinshTimeDesc() {
        return toDoThingsOperation.listFinshThingsOrderByFinshTimeDesc();
    }

    @Override
    public ArrayList<ToDoThing> listNotDoneThingsOrderByCreateTimeDesc() {
        return toDoThingsOperation.listNotDoneThingsOrderByCreateTimeDesc();
    }

    @Override
    public User getOnlineUser() {
        return userOperation.getOnlineUser();
    }

    @Override
    public ArrayList<ToDoThing> listAllThingsByUser(User user) {
        return toDoThingsOperation.listAllThingsByUser(user);
    }

    @Override
    public ArrayList<ToDoThing> listAllThingsByUserId(int userId) {
        return toDoThingsOperation.listAllThingsByUserId(userId);
    }

    @Override
    public ArrayList<ToDoThing> listAllThings() {
        return toDoThingsOperation.listAllThings();
    }

    @Override
    public void removeToDoTingById(Long id) {
        toDoThingsOperation.removeToDoTingById(id);
    }

    @Override
    public ToDoThing getThingById(Long id) {
        return toDoThingsOperation.getThingById(id);
    }

    @Override
    public void saveThing(ToDoThing toDoThing) {
        toDoThingsOperation.saveThing(toDoThing);
    }

    @Override
    public void updateThingState(Long id, int state) {
        ToDoThing toDoThing = toDoThingsOperation.getThingById(id);
        if (state == Common.STATUS_FINSH) {
            notificationOperation.updateFinshNotifyByThing(toDoThing);
        }
        toDoThingsOperation.updateThingState(id, state);
    }

    @Override
    public void updateCalculationNextReminderDate(List<Long> ids) {
        notificationOperation.updateCalculationNextReminderDate(ids);
    }

    @Override
    public AlarmDTO listRecentNeedNotifyThings() {
        //找到需要提醒的事项,判断依据,
        // 是否提醒(根据 提醒类型判断)
        // 上次是否有提醒时间,
        // 提醒时间是否大于当前时间

        AlarmDTO alarmDTO = null;
        ArrayList<Notification> noRepeatNotificationArrayList = notificationOperation.listNoRepeatNotification();
        ArrayList<Notification> repeatNotificationArrayList = notificationOperation.listRepeatNotification();

        if (noRepeatNotificationArrayList.size() == 0 && repeatNotificationArrayList.size() == 0) {
            return alarmDTO;
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
        return alarmDTO;
    }

    @Override
    public AlarmDTO listExpiredThings() {
        //找到需要提醒的事项,判断依据,
        // 是否提醒(根据 提醒类型判断)
        // 上次是否有提醒时间,
        // 提醒时间是否大于当前时间         //比较上一次提醒的时间,
        AlarmDTO alarmModel = null;
        ArrayList<Notification> notificationArrayList = notificationOperation.listExpiredNotification();
        ArrayList<Long> notificationIds = new ArrayList<>();
        ArrayList<ToDoThing> toDoThings = new ArrayList<>();
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
            Integer reminderType = Common.REMINDER_TYPE_NONE;
            alarmModel = new AlarmDTO(toDoThings, new Date(), reminderType, notificationIds);
            return alarmModel;
        }
        return null;
    }

    @Override
    public void updatePreNotifyDate(Long notifyId, Date date) {
        notificationOperation.updatePreNotifyDate(notifyId, date);
    }

    @Override
    public ArrayList<ChartDataDTO> countRecentWeekNewThings() {
        return toDoThingsOperation.countRecentWeekNewThings();
    }

    @Override
    public ArrayList<ChartDataDTO> countRecentWeekFinshThings() {
        return toDoThingsOperation.countRecentWeekFinshThings();
    }

    @Override
    public ArrayList<ChartDataDTO> countTodayThings() {
        return toDoThingsOperation.countTodayThings();
    }

    @Override
    public void saveThing(ToDoThing toDoThing, List<Notification> notificationList) {
        ArrayList<Connection_T_N> connection_t_nArrayList = new ArrayList<>();
        toDoThing.setUser(getOrAddUserInfo());
        try {
            toDoThingsOperation.saveThing(toDoThing);

            for (Notification notification : notificationList) {
                Connection_T_N connection_t_n = new Connection_T_N();

                connection_t_n.setToDoThing(toDoThing);

                notificationOperation.save(notification);
                connection_t_n.setNotification(notification);

                connection_t_nArrayList.add(connection_t_n);
            }
            connectionTNOperation.saveAll(connection_t_nArrayList);
        } catch (Exception e) {
            Log.e("SQL", "saveThing 添加失败");
        }
    }
}
