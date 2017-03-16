package com.wqzhang.thingswapper.dao.dbOperation.impl;

import com.wqzhang.thingswapper.dao.greendao.ToDoThing;
import com.wqzhang.thingswapper.dao.greendao.User;
import com.wqzhang.thingswapper.model.ChartDataDTO;

import java.util.ArrayList;

/**
 * Created by wqzhang on 17-3-2.
 */

public interface ToDoThingsOperation {
    /**
     * 根据提供的userid列出已经完成的事项,并且按照完成时间降序排列
     *
     * @return
     */
    ArrayList<ToDoThing> listFinshThingsOrderByFinshTimeDescByUserId(Long userId);

    /**
     * 根据提供的userid列出已经完成的事项,并且按照完成时间降序排列
     *
     * @return
     */
    ArrayList<ToDoThing> listNotDoneThingsOrderByCreateTimeDescByUserId(Long userId);

    /**
     * 列出已经完成的事项,并且按照完成时间降序排列
     *
     * @return
     */
    ArrayList<ToDoThing> listUserFinshThingsOrderByFinshTimeDesc(Long userId);


    /**
     * 列出还未完成的事项,并且按照完成时间降序排列
     *
     * @return
     */
    ArrayList<ToDoThing> listUserNotDoneThingsOrderByCreateTimeDesc(Long userId);

    /**
     * 列出提供用户的所有事项
     *
     * @param user
     * @return
     */
    ArrayList<ToDoThing> listAllThingsByUser(User user);

    /**
     * 列出在用户下的所有事项
     *
     * @param userId
     * @return
     */
    ArrayList<ToDoThing> listAllThingsByUserId(Long userId);

    /**
     * 列出数据库所有的事项
     *
     * @return
     */
    ArrayList<ToDoThing> listAllThings();

    /**
     * 根据thing 的id  删除事项
     *
     * @param id
     */
    void removeToDoTingById(Long id);

    /**
     * 根据thing 的id 查找该事项
     *
     * @param id
     * @return
     */
    ToDoThing getThingById(Long id);

    /**
     * 添加事项
     *
     * @param toDoThing
     */
    void saveThing(ToDoThing toDoThing);

    /**
     * 更新事项
     *
     * @param toDoThing
     */
    void updateThings(ToDoThing toDoThing);

    /**
     * 更新事项的状态
     *
     * @param id
     * @param state
     */
    void updateThingState(Long id, int state);

    /**
     * 计算最近一周添加的事项,结果封装为ChartDataDTO 对象
     *
     * @return
     */
    ArrayList<ChartDataDTO> countRecentWeekNewThings(Long userId);

    /**
     * 计算最近一周完成的事项,结果封装为ChartDataDTO 对象
     *
     * @return
     */
    ArrayList<ChartDataDTO> countRecentWeekFinshThings(Long userId);

    /**
     * 计算今天 添加/完成 的事项,结果封装为ChartDataDTO 对象
     *
     * @return
     */
    ArrayList<ChartDataDTO> countTodayThings(Long userId);

    /**
     * 计算今天新加事项的数量
     *
     * @return
     */
    int countToDayNewThings(Long userId);

    /**
     * 计算今天完成事项的数量
     *
     * @return
     */
    int countTodayFinshThings(Long userId);
}
