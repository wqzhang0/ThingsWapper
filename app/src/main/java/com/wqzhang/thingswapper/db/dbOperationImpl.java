package com.wqzhang.thingswapper.db;

import com.wqzhang.thingswapper.model.ToDoThing_model;
import com.wqzhang.thingswapper.model.User_model;

import java.util.ArrayList;

/**
 * Created by wqzhang on 16-12-19.
 */

public interface dbOperationImpl {
    void addUser(User_model user);

    User_model readUserInfo();

    boolean addToDoThing(ToDoThing_model toDoThing);

    ArrayList<ToDoThing_model> readToBeDoneThings();

    ArrayList<ToDoThing_model> readFinshThings();

}
