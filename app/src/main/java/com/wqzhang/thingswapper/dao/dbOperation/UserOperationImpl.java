package com.wqzhang.thingswapper.dao.dbOperation;

import com.wqzhang.thingswapper.dao.SharedPreferencesControl;
import com.wqzhang.thingswapper.dao.greendao.DaoSession;
import com.wqzhang.thingswapper.dao.greendao.User;
import com.wqzhang.thingswapper.dao.greendao.UserDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by wqzhang on 17-3-2.
 */

public class UserOperationImpl implements com.wqzhang.thingswapper.dao.dbOperation.impl.UserOperation {
    static UserDao userDao;

    private UserOperationImpl() {
    }

    public UserOperationImpl(DaoSession mDaoSession) {
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
        user.setName("默认用户");
        user.setEmail("暂无");
        user.setDefaultLoginAccount(true);
        user.setSynchronize(false);
        user.setVersion(-1);
        insertUser(user);
        SharedPreferencesControl.getUserInfoEditor().putBoolean("USER_CHANGE", true).commit();
    }

    @Override
    public User getOnlineUser() {
        QueryBuilder<User> userQueryBuilder = userDao.queryBuilder();
        userQueryBuilder.where(UserDao.Properties.DefaultLoginAccount.eq(true));
        ArrayList<User> userArrayList = (ArrayList<User>) userQueryBuilder.list();
        if (userArrayList.size() == 1) {
            return userArrayList.get(0);
        }
        return null;
    }

    @Override
    public void updateUser(User user) {
        userDao.update(user);
    }

    @Override
    public User getUserById(Long id) {
        QueryBuilder<User> userQueryBuilder = userDao.queryBuilder();
        userQueryBuilder.where(UserDao.Properties.Id.eq(id));
        ArrayList<User> userArrayList = (ArrayList<User>) userQueryBuilder.list();
        if (userArrayList.size() == 1) {
            return userArrayList.get(0);
        }
        return null;
    }

    @Override
    public User getUserByUser(User user) {
        return getUserById(user.getId());
    }
}
