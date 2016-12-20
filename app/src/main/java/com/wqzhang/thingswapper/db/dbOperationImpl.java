package com.wqzhang.thingswapper.db;

import com.wqzhang.thingswapper.model.UserModel;

/**
 * Created by wqzhang on 16-12-19.
 */

public interface dbOperationImpl {
        public void addUser(UserModel userModel);
        public UserModel readUserInfo();

}
