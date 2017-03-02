package com.wqzhang.thingswapper.dao.dbOperation;

import com.wqzhang.thingswapper.dao.greendao.DaoSession;
import com.wqzhang.thingswapper.dao.greendao.User;
import com.wqzhang.thingswapper.dao.greendao.UserDao;
import com.wqzhang.thingswapper.dao.dbOperation.impl.UserOperationImpl;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by wqzhang on 17-3-2.
 */

public class UserOperation implements UserOperationImpl {
    static UserDao userDao;

    private UserOperation() {
    }

    public UserOperation(DaoSession mDaoSession) {
        userDao = mDaoSession.getUserDao();
    }

    @Override
    public ArrayList<User> listUser() {
        QueryBuilder<User> userQueryBuilder = userDao.queryBuilder();
        ArrayList<User> userArrayList = (ArrayList<User>) userQueryBuilder.list();
        return userArrayList;
    }

    @Override
    public void insertUser(User user) {
        userDao.insert(user);
    }

    @Override
    public void insertDefaultUser() {
        User user = new User();
        user.setCreateDate(new Date());
        user.setSynchronize(false);
        user.setName("default");
        user.setDefaultLoginAccount(true);
        insertUser(user);
    }

    @Override
    public User getOnlineUser() {
        QueryBuilder<User> userQueryBuilder = userDao.queryBuilder();
        userQueryBuilder.where(UserDao.Properties.DefaultLoginAccount.eq(true));
        ArrayList<User> userArrayList = (ArrayList<User>) userQueryBuilder.list();
        return userArrayList.get(0);
    }
}
