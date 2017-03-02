package com.wqzhang.thingswapper.dao.dbOperation.impl;

import com.wqzhang.thingswapper.dao.greendao.Connection_T_N;
import com.wqzhang.thingswapper.dao.greendao.Notification;

import java.util.ArrayList;

/**
 * Created by wqzhang on 17-3-2.
 */

public interface ConnectionTNOperationImpl {
    void saveAll(ArrayList<Connection_T_N> arrayList);

    ArrayList<Notification> listNotifications(long thingId);
}
