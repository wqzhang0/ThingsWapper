package com.wqzhang.thingswapper.dao;


import com.wqzhang.thingswapper.dao.greendao.Notification;
import com.wqzhang.thingswapper.dao.greendao.ToDoThing;
import com.wqzhang.thingswapper.dao.greendao.User;
import com.wqzhang.thingswapper.model.ChartDataModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wqzhang on 16-12-28.
 */

public interface BusinessProcessImpl {
    //查询用户 如果没有用户则添加一个默认用户
    User readOrAddUserInfo();

    //根据用户查找所有的事项
    ArrayList<ToDoThing> readAllThingsByUser(User user);

    //根据用户ID查找所有的事项
    ArrayList<ToDoThing> readAllThingsByUserId(int userId);

    //查找数据库中所有的事项
    ArrayList<ToDoThing> readAllThings();

    //根据ToDoThing的id  删除事项
    void deleteToDoTingById(Long id);

    //根据ToDoThing的id 查找事项
    ToDoThing readThingById(Long id);

    //添加事项  已废弃
    void addToDoThing(ToDoThing toDoThing);

    //添加事项, 和提醒时间(notificationList 可为空)
    void addToDoThing(ToDoThing toDoThing, List<Notification> notificationList);

    //查找已经完成的事情
    ArrayList<ToDoThing> readFinshThingsFinshTimeDesc();

    //查找还未做的事情
    ArrayList<ToDoThing> readNotDoneThingsCreateTimeDesc();

    //获取默认登录的用户
    User getOnlineUser();

    //改变ToDoThing的状态
    void changeToDoThingState(Long id, int state);

    //查找距离此时最近的一次提醒
    ArrayList<ToDoThing> readRecentToDoThings();

    //获取到已经过期但是还未提醒的事项 Expired
    ArrayList<ToDoThing> readExpiredToDoThing();

    //获取最近七日内增加的事件数量 date-counts
    ArrayList<ChartDataModel> readRecentWeekNewThings();

    //获取最近七日完成的事件  date-counts
    ArrayList<ChartDataModel> readRecentWeekFinshThings();

    //获得今天添加的事项 和 已完成的事情
    ArrayList<ChartDataModel> readTodayThings();


}
