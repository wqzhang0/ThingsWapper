package com.wqzhang.thingswapper.dao.dbOperation.impl;

import com.wqzhang.thingswapper.dao.greendao.ToDoThing;
import com.wqzhang.thingswapper.dao.greendao.User;
import com.wqzhang.thingswapper.model.ChartDataDTO;

import java.util.ArrayList;

/**
 * Created by wqzhang on 17-3-2.
 */

public interface ToDoThingsOperationImpl {

    ArrayList<ToDoThing> listFinshThingsOrderByFinshTimeDesc();

    ArrayList<ToDoThing> listNotDoneThingsOrderByCreateTimeDesc();

    ArrayList<ToDoThing> listAllThingsByUser(User user);

    ArrayList<ToDoThing> listAllThingsByUserId(int userId);

    ArrayList<ToDoThing> listAllThings();

    void removeToDoTingById(Long id);

    ToDoThing getThingById(Long id);

    void saveThing(ToDoThing toDoThing);

    void updateThingState(Long id, int state);

    ArrayList<ChartDataDTO> countRecentWeekNewThings();

    ArrayList<ChartDataDTO> countRecentWeekFinshThings();

    ArrayList<ChartDataDTO> countTodayThings();
}
