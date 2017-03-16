package com.wqzhang.thingswapper.dao.dbOperation.impl;

import com.wqzhang.thingswapper.dao.greendao.Notification;
import com.wqzhang.thingswapper.dao.greendao.ToDoThing;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wqzhang on 17-3-2.
 */

public interface NotificationOperation {

    /**
     * 更新已经完成事项 的 提醒
     *
     * @param toDoThing
     */
    void updateFinshNotifyByThing(ToDoThing toDoThing);

    /**
     * 根据notificatin 的IDS  批量查找
     *
     * @param ids
     * @return
     */
    ArrayList<Notification> listByIds(List<Long> ids);

    /**
     * 查找从不重复的提醒
     *
     * @return
     */
    ArrayList<Notification> listNoRepeatNotification();

    /**
     * 查找出重复的提醒
     *
     * @return
     */
    ArrayList<Notification> listRepeatNotification();

    /**
     * 查找出已经过期的提醒
     *
     * @return
     */
    ArrayList<Notification> listExpiredNotification();

    /**
     * 更新上次的提醒时间
     *
     * @param notifyId
     * @param date
     */
    void updatePreNotifyDate(Long notifyId, Date date);

    /**
     * 保存提醒
     *
     * @param notification
     */
    void save(Notification notification);

    /**
     * 更新提醒
     *
     * @param notification
     */
    void update(Notification notification);

    /**
     * 根据notification 的 id 设置为无效
     *
     * @param notifyId
     */
    void setInvalide(Long notifyId);

    /**
     * 根据提供的notificaion id 查找
     *
     * @param notifyId
     * @return
     */
    Notification getNotification(Long notifyId);
}
