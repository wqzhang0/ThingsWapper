package com.wqzhang.thingswapper.dao.dbOperation.impl;

import com.wqzhang.thingswapper.dao.greendao.User;

import java.util.ArrayList;

/**
 * Created by wqzhang on 17-3-2.
 */

public interface UserOperationImpl {
    ArrayList<User> listUser();

    void insertUser(User user);

    void insertDefaultUser();

    User getOnlineUser();
}
