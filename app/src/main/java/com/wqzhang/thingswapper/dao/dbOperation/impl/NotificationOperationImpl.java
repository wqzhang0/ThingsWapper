package com.wqzhang.thingswapper.dao.dbOperation.impl;

import com.wqzhang.thingswapper.dao.greendao.Notification;
import com.wqzhang.thingswapper.dao.greendao.ToDoThing;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wqzhang on 17-3-2.
 */

public interface NotificationOperationImpl {

    void updateFinshNotifyByThing(ToDoThing toDoThing);

    ArrayList<Notification> listByIds(List<Long> ids);

    ArrayList<Notification> listNoRepeatNotification();

    ArrayList<Notification> listRepeatNotification();

    ArrayList<Notification> listExpiredNotification();

    void updatePreNotifyDate(Long notifyId, Date date);

    void save(Notification notification);

    void update(Notification notification);


}
