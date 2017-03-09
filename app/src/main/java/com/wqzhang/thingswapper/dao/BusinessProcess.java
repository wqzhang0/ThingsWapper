package com.wqzhang.thingswapper.dao;

import android.util.Log;

import com.wqzhang.thingswapper.dao.dbOperation.ConnectionTNOperation;
import com.wqzhang.thingswapper.dao.dbOperation.NotificationOperationImpl;
import com.wqzhang.thingswapper.dao.dbOperation.ToDoThingsOperation;
import com.wqzhang.thingswapper.dao.dbOperation.UserOperation;
import com.wqzhang.thingswapper.dao.greendao.Connection_T_N;
import com.wqzhang.thingswapper.dao.greendao.DaoSession;
import com.wqzhang.thingswapper.dao.greendao.Notification;
import com.wqzhang.thingswapper.dao.greendao.ToDoThing;
import com.wqzhang.thingswapper.dao.greendao.User;
import com.wqzhang.thingswapper.model.AlarmDTO;
import com.wqzhang.thingswapper.model.ChartDataDTO;
import com.wqzhang.thingswapper.model.ShowThingsDTO;
import com.wqzhang.thingswapper.util.Common;
import com.wqzhang.thingswapper.util.DateUtil;
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
    static NotificationOperationImpl notificationOperationImpl;
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
            notificationOperationImpl = new NotificationOperationImpl(mDaoSession);
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
    public ArrayList<ShowThingsDTO> listNotDoneThingsOrderByCreateTimeDescWithReminderTime() {
        ArrayList<ToDoThing> toDoThings = toDoThingsOperation.listNotDoneThingsOrderByCreateTimeDesc();

        ArrayList<ShowThingsDTO> showThingsDTOs = new ArrayList<>();

        for (ToDoThing toDoThing : toDoThings) {
            ArrayList<Notification> tmpNotifications = connectionTNOperation.listNotifications(toDoThing.getId());
            if (tmpNotifications.size() == 0) {
                showThingsDTOs.add(new ShowThingsDTO(toDoThing, 0, false, null));
            } else if (tmpNotifications.size() == 1) {
                Notification tmpNotification = tmpNotifications.get(0);
                if (Common.REPEAT_TYPE_0.equals(tmpNotification.getRepeatType())) {
                    showThingsDTOs.add(new ShowThingsDTO(toDoThing, tmpNotification.getNotifyType(), tmpNotification.getAlearyNotify(), tmpNotification.getReminderDate()));
                } else {
                    showThingsDTOs.add(new ShowThingsDTO(toDoThing, tmpNotification.getNotifyType(), tmpNotification.getInvalide(), tmpNotification.getNextRemindDate()));
                }
            } else {
                Date recentTime = null;
                long index = -1;
                for (int i = 0; i < tmpNotifications.size(); i++) {
                    Notification tmpNotification = tmpNotifications.get(i);
                    if (!tmpNotification.getAlearyNotify()) {
                        if (recentTime == null) {
                            recentTime = tmpNotification.getReminderDate();
                            index = i;
                        }
                        if (DateUtil.leDateTime(recentTime, tmpNotification.getReminderDate())) {
                            recentTime = tmpNotification.getReminderDate();
                            index = i;
                        }
                    }
                }
                if (index == -1) {
                    showThingsDTOs.add(new ShowThingsDTO(toDoThing, 0, false, null));
                } else {
                    Notification recentNotification = tmpNotifications.get((int) index);
                    showThingsDTOs.add(new ShowThingsDTO(toDoThing, recentNotification.getNotifyType(), recentNotification.getAlearyNotify(), recentNotification.getReminderDate()));
                }
            }
        }
        return showThingsDTOs;
    }

    @Override
    public ArrayList<ShowThingsDTO> listFinshThingsOrderByFinshTimeDescWithReminderTime() {
        ArrayList<ToDoThing> toDoThings = toDoThingsOperation.listFinshThingsOrderByFinshTimeDesc();

        ArrayList<ShowThingsDTO> showThingsDTOs = new ArrayList<>();
        for (ToDoThing toDoThing : toDoThings) {
            showThingsDTOs.add(new ShowThingsDTO(toDoThing, 0, false, new Date()));
        }
        return showThingsDTOs;
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
        toDoThingsOperation.updateThingState(id, Common.STATUS_DELETE);
        notificationOperationImpl.setInvalide(id);
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
            notificationOperationImpl.updateFinshNotifyByThing(toDoThing);
        }
        toDoThingsOperation.updateThingState(id, state);
    }

    @Override
    public void updateCalculationNextReminderDate(List<Long> ids) {
        ArrayList<Notification> notificationArrayList = notificationOperationImpl.listByIds(ids);
        Date nowTime = new Date();
        for (Notification tmpNotification : notificationArrayList) {
            String repeatType = tmpNotification.getRepeatType();
            if (!repeatType.equals(Common.REPEAT_TYPE_0)) {
                Date reminderTime = tmpNotification.getReminderDate();
                if (tmpNotification.getRepeatType().equals(Common.REPEAT_TYPE_1)) {
                    Date nextReminderTime = DateUtil.produceDete(reminderTime);
                    //查看今天是周几.如果是工作日
                    //如果设定的时间HHMMSS还未到.不处理
                    //如果设定的时间HHMMSS已经超过 则设置nextReminderTime 为下个工作日
                    if (DateUtil.getWeek(nowTime) < 6) {
                        if (!DateUtil.reachCurrentTime(nextReminderTime)) {
                            if (DateUtil.getWeek(nextReminderTime) == 5) {
                                //如果今天是周五,加三天
                                nextReminderTime = DateUtil.addDate(nextReminderTime, 3);
                            } else {
                                nextReminderTime = DateUtil.addDate(nextReminderTime, 1);
                            }
                        }
                    } else if (DateUtil.getWeek(nowTime) == 6) {
                        nextReminderTime = DateUtil.addDate(nextReminderTime, 2);
                    } else if (DateUtil.getWeek(nowTime) == 7) {
                        nextReminderTime = DateUtil.addDate(nextReminderTime, 1);
                    }
                    tmpNotification.setNextRemindDate(nextReminderTime);
                } else if (repeatType.equals(Common.REPEAT_TYPE_2)) {
                    Date nextReminderTime = DateUtil.produceDete(reminderTime);
                    //如果设定的时间HHMMSS还未到.不处理
                    //如果设定的时间HHMMSS已经超过 则设置nextReminderTime 为明天
                    if (!DateUtil.reachCurrentTime(nextReminderTime)) {
                        nextReminderTime = DateUtil.addDate(nextReminderTime, 1);
                    }
                    tmpNotification.setNextRemindDate(nextReminderTime);
                } else if (repeatType.equals(Common.REPEAT_TYPE_3)) {
                    //找出提醒时间是周几
                    //从几天起后退一周设置为下次提醒时间
                    Date nextReminderTime = null;
                    int weekKey = DateUtil.getWeek(reminderTime);
                    int todayWeekKey = DateUtil.getWeek(nowTime);
                    if (todayWeekKey == weekKey) {
                        if (DateUtil.leDateTime(nowTime, DateUtil.produceDete(reminderTime))) {
                            nextReminderTime = DateUtil.addDate(DateUtil.produceDete(reminderTime), 7);
                        } else {
                            nextReminderTime = DateUtil.produceDete(reminderTime);
                        }
                    } else if (todayWeekKey > weekKey) {
                        nextReminderTime = DateUtil.addDate(DateUtil.produceDete(reminderTime), 7 - todayWeekKey + weekKey);
                    } else if (todayWeekKey < weekKey) {
                        nextReminderTime = DateUtil.addDate(DateUtil.produceDete(reminderTime), weekKey - todayWeekKey);
                    }
                    tmpNotification.setNextRemindDate(nextReminderTime);
                } else if (repeatType.equals(Common.REPEAT_TYPE_4)) {
                    //如果存在上一次提醒时间,则用上一次时间
                    //如果超过"nextReminderDate" 则天数+14
                    //否则,不变
                    if (DateUtil.reachCurrentTime(tmpNotification.getNextRemindDate())) {
                        Date nextReminderTime = DateUtil.addDate(tmpNotification.getNextRemindDate(), 14);
                        tmpNotification.setNextRemindDate(nextReminderTime);
                    }
                } else if (repeatType.equals(Common.REPEAT_TYPE_5)) {
                    //如果是周末
                    //今天是否是周末,
                    // 时间是否超过,
                    //未超过,下次提醒时间是今天
                    //超过,提醒时间为下个周末
                    Date nextReminderTime = DateUtil.produceDete(reminderTime);
                    if (DateUtil.getWeek(nextReminderTime) == 1) {
                        nextReminderTime = DateUtil.addDate(nextReminderTime, 5);
                    } else if (DateUtil.getWeek(nextReminderTime) == 2) {
                        nextReminderTime = DateUtil.addDate(nextReminderTime, 4);
                    } else if (DateUtil.getWeek(nextReminderTime) == 3) {
                        nextReminderTime = DateUtil.addDate(nextReminderTime, 3);
                    } else if (DateUtil.getWeek(nextReminderTime) == 4) {
                        nextReminderTime = DateUtil.addDate(nextReminderTime, 2);
                    } else if (DateUtil.getWeek(nextReminderTime) == 5) {
                        nextReminderTime = DateUtil.addDate(nextReminderTime, 1);
                    } else if (DateUtil.getWeek(nextReminderTime) == 6) {
                        if (!DateUtil.reachCurrentTime(nextReminderTime)) {
                            nextReminderTime = DateUtil.addDate(nextReminderTime, 1);
                        }
                    } else if (DateUtil.getWeek(nextReminderTime) == 7) {
                        if (!DateUtil.reachCurrentTime(nextReminderTime)) {
                            nextReminderTime = DateUtil.addDate(nextReminderTime, 6);
                        }
                    }
                    tmpNotification.setNextRemindDate(nextReminderTime);
                }
            }
            notificationOperationImpl.update(tmpNotification);
        }
    }

    @Override
    public AlarmDTO listRecentNeedNotifyThings() {
        //找到需要提醒的事项,判断依据,
        // 是否提醒(根据 提醒类型判断)
        // 上次是否有提醒时间,
        // 提醒时间是否大于当前时间

        AlarmDTO alarmDTO = null;
        ArrayList<Notification> noRepeatNotificationArrayList = notificationOperationImpl.listNoRepeatNotification();
        ArrayList<Notification> repeatNotificationArrayList = notificationOperationImpl.listRepeatNotification();

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
        ArrayList<Notification> notificationArrayList = notificationOperationImpl.listExpiredNotification();
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
        notificationOperationImpl.updatePreNotifyDate(notifyId, date);
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
                String repeatType = notification.getRepeatType();
                if (!repeatType.equals(Common.REPEAT_TYPE_0)) {
                    Date nowDate = new Date();
                    Date reminderTime = notification.getReminderDate();

                    if (repeatType.equals(Common.REPEAT_TYPE_1)) {
                        Date nextReminderTime = DateUtil.produceDete(reminderTime);
                        //查看今天是周几.如果是工作日
                        //如果设定的时间HHMMSS还未到.不处理
                        //如果设定的时间HHMMSS已经超过 则设置nextReminderTime 为下个工作日
                        if (DateUtil.getWeek(nowDate) < 6) {
                            if (!DateUtil.reachCurrentTime(nextReminderTime)) {
                                if (DateUtil.getWeek(nextReminderTime) == 5) {
                                    //如果今天是周五,加三天
                                    nextReminderTime = DateUtil.addDate(nextReminderTime, 3);
                                } else {
                                    nextReminderTime = DateUtil.addDate(nextReminderTime, 1);
                                }
                            }
                        } else if (DateUtil.getWeek(nowDate) == 6) {
                            nextReminderTime = DateUtil.addDate(nextReminderTime, 2);
                        } else if (DateUtil.getWeek(nowDate) == 7) {
                            nextReminderTime = DateUtil.addDate(nextReminderTime, 1);
                        }
                        notification.setNextRemindDate(nextReminderTime);
                    } else if (repeatType.equals(Common.REPEAT_TYPE_2)) {
                        Date nextReminderTime = DateUtil.produceDete(reminderTime);
                        //如果设定的时间HHMMSS还未到.不处理
                        //如果设定的时间HHMMSS已经超过 则设置nextReminderTime 为明天
                        if (!DateUtil.reachCurrentTime(nextReminderTime)) {
                            nextReminderTime = DateUtil.addDate(nextReminderTime, 1);
                        }
                        notification.setNextRemindDate(nextReminderTime);
                    } else if (repeatType.equals(Common.REPEAT_TYPE_3)) {
                        //找出提醒时间是周几
                        //从几天起后退一周设置为下次提醒时间
                        Date nextReminderTime = null;
                        int weekKey = DateUtil.getWeek(reminderTime);
                        int todayWeekKey = DateUtil.getWeek(nowDate);
                        if (todayWeekKey == weekKey) {
                            if (DateUtil.leDateTime(nowDate, DateUtil.produceDete(reminderTime))) {
                                nextReminderTime = DateUtil.addDate(DateUtil.produceDete(reminderTime), 7);
                            } else {
                                nextReminderTime = DateUtil.produceDete(reminderTime);
                            }
                        } else if (todayWeekKey > weekKey) {
                            nextReminderTime = DateUtil.addDate(DateUtil.produceDete(reminderTime), 7 - todayWeekKey + weekKey);
                        } else if (todayWeekKey < weekKey) {
                            nextReminderTime = DateUtil.addDate(DateUtil.produceDete(reminderTime), weekKey - todayWeekKey);
                        }
                        notification.setNextRemindDate(nextReminderTime);
                    } else if (repeatType.equals(Common.REPEAT_TYPE_4)) {
                        notification.setNextRemindDate(reminderTime);
                    } else if (repeatType.equals(Common.REPEAT_TYPE_5)) {
                        //如果是周末
                        //今天是否是周末,
                        // 时间是否超过,
                        //未超过,下次提醒时间是今天
                        //超过,提醒时间为下个周末
                        Date nextReminderTime = DateUtil.produceDete(reminderTime);
                        if (DateUtil.getWeek(nextReminderTime) == 1) {
                            nextReminderTime = DateUtil.addDate(nextReminderTime, 5);
                        } else if (DateUtil.getWeek(nextReminderTime) == 2) {
                            nextReminderTime = DateUtil.addDate(nextReminderTime, 4);
                        } else if (DateUtil.getWeek(nextReminderTime) == 3) {
                            nextReminderTime = DateUtil.addDate(nextReminderTime, 3);
                        } else if (DateUtil.getWeek(nextReminderTime) == 4) {
                            nextReminderTime = DateUtil.addDate(nextReminderTime, 2);
                        } else if (DateUtil.getWeek(nextReminderTime) == 5) {
                            nextReminderTime = DateUtil.addDate(nextReminderTime, 1);
                        } else if (DateUtil.getWeek(nextReminderTime) == 6) {
                            if (!DateUtil.reachCurrentTime(nextReminderTime)) {
                                nextReminderTime = DateUtil.addDate(nextReminderTime, 1);
                            }
                        } else if (DateUtil.getWeek(nextReminderTime) == 7) {
                            if (!DateUtil.reachCurrentTime(nextReminderTime)) {
                                nextReminderTime = DateUtil.addDate(nextReminderTime, 6);
                            }
                        }
                        notification.setNextRemindDate(nextReminderTime);
                    }
                }
                notificationOperationImpl.save(notification);
                connection_t_n.setNotification(notification);
                connection_t_nArrayList.add(connection_t_n);
            }
            connectionTNOperation.saveAll(connection_t_nArrayList);
        } catch (Exception e) {
            Log.e("SQL", "saveThing 添加失败");
        }
    }


}

