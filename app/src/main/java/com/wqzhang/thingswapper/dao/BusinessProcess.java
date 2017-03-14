package com.wqzhang.thingswapper.dao;


import com.wqzhang.thingswapper.dao.greendao.Notification;
import com.wqzhang.thingswapper.dao.greendao.ToDoThing;
import com.wqzhang.thingswapper.dao.greendao.User;
import com.wqzhang.thingswapper.model.AlarmDTO;
import com.wqzhang.thingswapper.model.ChartDataDTO;
import com.wqzhang.thingswapper.model.ShowThingsDTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wqzhang on 16-12-28.
 */

public interface BusinessProcess {
    /**
     * 如果没有用户则添加一个默认用户
     * 如果有用户 则忽略操作
     */
    void insertDefaultUser();


    /**
     * 根据用户查找所有的事项
     *
     * @param user
     * @return
     */
    ArrayList<ToDoThing> listAllThingsByUser(User user);


    /**
     * 根据用户ID查找所有的事项
     *
     * @param userId 用户id
     * @return
     */
    ArrayList<ToDoThing> listAllThingsByUserId(int userId);


    /**
     * 查找数据库中所有的事项
     *
     * @return
     */
    ArrayList<ToDoThing> listAllThings();

    /**
     * 根据ToDoThing的id  删除事项
     *
     * @param id ToDoThing的id
     */
    void removeToDoTingById(Long id);


    /**
     * 根据ToDoThing的id 查找事项
     *
     * @param id ToDoThing的id
     * @return
     */
    ToDoThing getThingById(Long id);

    /**
     * 添加事项  已废弃
     *
     * @param toDoThing
     */
    void saveThing(ToDoThing toDoThing);


    /**
     * 添加事项, 和提醒时间(notificationList 可为空)
     *
     * @param toDoThing
     * @param notificationList
     */
    void saveThing(ToDoThing toDoThing, List<Notification> notificationList);

    /**
     * 查找已经完成的事情
     *
     * @return
     */
    ArrayList<ToDoThing> listFinshThingsOrderByFinshTimeDesc();


    /**
     * 查找还未做的事情
     *
     * @return
     */
    ArrayList<ToDoThing> listNotDoneThingsOrderByCreateTimeDesc();

    /**
     * 查找还未做的事情,附带展示需要的各种信息
     *
     * @return
     */
    ArrayList<ShowThingsDTO> listNotDoneThingsOrderByCreateTimeDescWithReminderTime();

    /**
     * 查找已经完成的事情 附带展示需要的各种信息
     * @return
     */
    ArrayList<ShowThingsDTO> listFinshThingsOrderByFinshTimeDescWithReminderTime();

    /**
     * 获取默认登录的用户
     *
     * @return
     */
    User getOnlineUser();


    /**
     * 改变ToDoThing的状态 如果更改为完成状态,则同时设上一次提醒时间为当前时间
     *
     * @param id    things id
     * @param state 状态信息
     */
    void updateThingState(Long id, int state);

    /**
     * 如果是重复提醒的notification 则
     * 计算下一次提醒的时间
     * 否则.不做操作
     *
     * @param ids
     */
    void updateCalculationNextReminderDate(List<Long> ids);

    /**
     * 查找距离此时最近的一次提醒
     *
     * @return
     */
    AlarmDTO listRecentNeedNotifyThings();


    /**
     * 获取到已经过期但是还未提醒的事项 Expired
     *
     * @return
     */
    AlarmDTO listExpiredThings();

    /**
     * 更新Notification  中 上一次提醒的时间
     *
     * @param id   Notifaction id
     * @param date
     */
    void updatePreNotifyDate(Long id, Date date);

    //获取最近七日内增加的事件数量 date-counts
    ArrayList<ChartDataDTO> countRecentWeekNewThings();

    /**
     * 获取最近七日完成的事件  date-counts
     */
    ArrayList<ChartDataDTO> countRecentWeekFinshThings();

    /**
     * 获得今天添加的事项 和 已完成的事情
     *
     * @return
     */
    ArrayList<ChartDataDTO> countTodayThings();


}
