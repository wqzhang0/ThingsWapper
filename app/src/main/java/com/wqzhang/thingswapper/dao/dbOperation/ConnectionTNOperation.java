package com.wqzhang.thingswapper.dao.dbOperation;

import com.wqzhang.thingswapper.dao.greendao.Connection_T_N;
import com.wqzhang.thingswapper.dao.greendao.Connection_T_NDao;
import com.wqzhang.thingswapper.dao.greendao.DaoSession;
import com.wqzhang.thingswapper.dao.dbOperation.impl.ConnectionTNOperationImpl;

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
}
