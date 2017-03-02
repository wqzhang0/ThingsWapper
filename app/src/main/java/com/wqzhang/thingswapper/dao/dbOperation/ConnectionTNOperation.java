package com.wqzhang.thingswapper.dao.dbOperation;

import com.wqzhang.thingswapper.dao.dbOperation.impl.ConnectionTNOperationImpl;
import com.wqzhang.thingswapper.dao.greendao.Connection_T_N;
import com.wqzhang.thingswapper.dao.greendao.Connection_T_NDao;
import com.wqzhang.thingswapper.dao.greendao.DaoSession;
import com.wqzhang.thingswapper.dao.greendao.Notification;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;

/**
 * Created by wqzhang on 17-3-2.
 */

public class ConnectionTNOperation implements ConnectionTNOperationImpl {
    static Connection_T_NDao connection_t_nDao;

    private ConnectionTNOperation() {
    }

    public ConnectionTNOperation(DaoSession mDaoSession) {
        connection_t_nDao = mDaoSession.getConnection_T_NDao();
    }

    @Override
    public void saveAll(ArrayList<Connection_T_N> arrayList) {
        connection_t_nDao.insertInTx(arrayList);
    }

    @Override
    public ArrayList<Notification> listNotifications(long thingId) {
        QueryBuilder<Connection_T_N> queryBuilder = connection_t_nDao.queryBuilder();
        queryBuilder.where(Connection_T_NDao.Properties.ToDoThingId.eq(thingId));
        ArrayList<Connection_T_N> connection_t_ns = (ArrayList<Connection_T_N>) queryBuilder.list();
        ArrayList<Notification> notifications = new ArrayList<>();
        for (Connection_T_N connection_t_n : connection_t_ns) {
            notifications.add(connection_t_n.getNotification());
        }
        return notifications;
    }


}
