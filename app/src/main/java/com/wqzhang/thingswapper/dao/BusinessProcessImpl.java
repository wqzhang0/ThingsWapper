package com.wqzhang.thingswapper.dao;


import com.wqzhang.thingswapper.dao.greendao.Notification;
import com.wqzhang.thingswapper.dao.greendao.ToDoThing;
import com.wqzhang.thingswapper.dao.greendao.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wqzhang on 16-12-28.
 */

public interface BusinessProcessImpl {
    User readOrAddUserInfo();

    ArrayList<ToDoThing> readAllThingsByUser(User user);

    ArrayList<ToDoThing> readAllThingsByUserId(int userId);

    ArrayList<ToDoThing> readAllThings();

    void deleteToDoTingById(Long id);

    ToDoThing readThingById(Long id);

    void addToDoThing(ToDoThing toDoThing);

    void addToDoThing(ToDoThing toDoThing, List<Notification> notificationList);

    ArrayList<ToDoThing> readFinshThings();

    ArrayList<ToDoThing> readNotDoneThings();

    User getOnlineUser();

    void changeToDoThingState(Long id, int state);
}
