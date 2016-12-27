package com.wqzhang.thingswapper.db;

import com.wqzhang.thingswapper.model.ToDoThing;
import com.wqzhang.thingswapper.model.UserModel;

import java.util.ArrayList;

/**
 * Created by wqzhang on 16-12-19.
 */

public interface dbOperationImpl {
    void addUser(UserModel userModel);

    UserModel readUserInfo();

    boolean addToDoThing(ToDoThing toDoThing);

    ArrayList<ToDoThing> readToBeDoneThings();

    ArrayList<ToDoThing> readFinshThings();

}
