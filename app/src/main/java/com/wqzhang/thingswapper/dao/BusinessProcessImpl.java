package com.wqzhang.thingswapper.dao;


import com.wqzhang.thingswapper.dao.greendao.ToDoThing;
import com.wqzhang.thingswapper.dao.greendao.User;

import java.util.ArrayList;

/**
 * Created by wqzhang on 16-12-28.
 */

public interface BusinessProcessImpl {
    User readOrAddUserInfo();
    ArrayList<ToDoThing> readAllThings(User user);
    void addToDoThing(ToDoThing toDoThing);
}
