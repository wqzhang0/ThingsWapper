package com.wqzhang.thingswapper.db;

import com.wqzhang.thingswapper.model.ToDoThingModel;
import com.wqzhang.thingswapper.model.UserModel;

import java.util.ArrayList;

/**
 * Created by wqzhang on 16-12-19.
 */

public interface dbOperationImpl {
    void addUser(UserModel userModel);

    UserModel readUserInfo();

    boolean addToDoThing(ToDoThingModel toDoThingModel);

    ArrayList<ToDoThingModel> readToBeDoneThings();

    ArrayList<ToDoThingModel> readFinshThings();

}
