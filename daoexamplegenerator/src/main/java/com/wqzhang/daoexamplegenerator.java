package com.wqzhang;

import org.greenrobot.greendao.generator.DaoGenerator;
import org.greenrobot.greendao.generator.Entity;
import org.greenrobot.greendao.generator.Property;
import org.greenrobot.greendao.generator.Schema;
import org.greenrobot.greendao.generator.ToMany;

import java.util.Date;

public class daoexamplegenerator {
    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(10, "com.wqzhang.thingswapper.dao.greendao");
        schema.enableKeepSectionsByDefault();
        initDatabase(schema);
        new DaoGenerator().generateAll(schema, "/home/wqzhang/GraduationProjectCode/ThingsWapper/app/src/main/java");
    }

    private static void initDatabase(Schema schema) {
        // 一个实体（类）就关联到数据库中的一张表，此处表名为「Note」（既类名）
        Entity user = schema.addEntity("User");

        user.addIdProperty();
        user.addStringProperty("name");
        user.addStringProperty("account");
        user.addStringProperty("password");
        user.addStringProperty("email");
        user.addDateProperty("createDate");
        user.addBooleanProperty("isSynchronize");
        user.addBooleanProperty("defaultLoginAccount");
        user.setJavaDoc("user Model ,保存用户基本信息");

        Entity toDoThing = schema.addEntity("ToDoThing");
        toDoThing.addIdProperty();
        toDoThing.addStringProperty("reminderContext");
        toDoThing.addIntProperty("reminderType");
        toDoThing.addDateProperty("createDate");
        toDoThing.addDateProperty("finshDate");
        toDoThing.addIntProperty("Status");
        toDoThing.addBooleanProperty("isSynchronize");

        Entity notification = schema.addEntity("Notification");

        notification.addIdProperty();
        notification.addIntProperty("notifyType");
        notification.addDateProperty("reminderDate");
        notification.addIntProperty("remindFrequency");
        notification.addIntProperty("remindFrequencyInterval");
        notification.addIntProperty("remindCount");
        notification.addDateProperty("endDate");
        notification.addBooleanProperty("isSynchronize");
        notification.addDateProperty("preNotifyDate");
        notification.addBooleanProperty("alearyNotify");

        Entity connection_T_N = schema.addEntity("Connection_T_N");
        connection_T_N.addIdProperty();


        Property toDoThingPropertyUserId = toDoThing.addLongProperty("userId").getProperty();

        //创建 User 和 ToDoThing 的 一对多关系  addToMany
        ToMany userToToDoThing = user.addToMany(toDoThing, toDoThingPropertyUserId);
        userToToDoThing.setName("toDoThings");
        //创建 ToDoThing 和 User 的 多对一关系  addToOne
        toDoThing.addToOne(user, toDoThingPropertyUserId);

        /**
         * 创建 ToDoThing 和 Notification 的 多对多关系
         * 1 建立中间表 Connection_T_N
         * 2 建立 ToDoThing 和 Connection_T_N 的 一对多关系
         * 3 建立 Connection_T_N 和 ToDoThing 的 多对一关系
         * 4 建立 Notification 和 Connection_T_N 的 一对多关系
         * 5 建立 Connection_T_N 和 Notification 的 多对一关系
         */


        Property connection_T_Id = connection_T_N.addLongProperty("toDoThingId").getProperty();
        Property connection_N_Id = connection_T_N.addLongProperty("notificationId").getProperty();

        //2 建立 ToDoThing 和 Connection_T_N 的 一对多关系
        ToMany toDoThingToCnn = toDoThing.addToMany(connection_T_N, connection_T_Id);
        toDoThingToCnn.setName("notificationIds");

        //3 建立 Connection_T_N 和 ToDoThing 的 多对一关系
        connection_T_N.addToOne(toDoThing, connection_T_Id);

        //4 建立 Notification 和 Connection_T_N 的 一对多关系
        ToMany notificationToConn = notification.addToMany(connection_T_N, connection_N_Id);
        notificationToConn.setName("toDoThingIds");

        //5 建立 Connection_T_N 和 Notification 的 多对一关系
        connection_T_N.addToOne(notification, connection_N_Id);

    }
}
