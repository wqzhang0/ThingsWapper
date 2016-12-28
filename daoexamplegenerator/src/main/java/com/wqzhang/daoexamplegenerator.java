package com.wqzhang;

import org.greenrobot.greendao.generator.DaoGenerator;
import org.greenrobot.greendao.generator.Entity;
import org.greenrobot.greendao.generator.Property;
import org.greenrobot.greendao.generator.Schema;
import org.greenrobot.greendao.generator.ToMany;

import java.util.Date;

public class daoexamplegenerator {
    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(1, "com.wqzhang.greendao");
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

        user.setJavaDoc("user Model ,保存用户基本信息");

        Entity toDoThing = schema.addEntity("ToDoThing");
        toDoThing.addIdProperty();
        toDoThing.addStringProperty("reminderContext");
        toDoThing.addIntProperty("reminderType");
        toDoThing.addDateProperty("createDate");
        toDoThing.addIntProperty("Status");
        toDoThing.addBooleanProperty("isSynchronize");
        Property toDoThingPropertyUserId = toDoThing.addLongProperty("userId").getProperty();
        toDoThing.addToOne(user, toDoThingPropertyUserId);



        Entity notification = schema.addEntity("Notification");
        Property notificatinoPropertyToDoThingId = notification.addLongProperty("toDoThingId").getProperty();
        Property notificatinoPropertyUserId = notification.addLongProperty("userId").getProperty();

        notification.addIdProperty();
        notification.addToOne(user, notificatinoPropertyUserId);
        notification.addToOne(toDoThing, notificatinoPropertyToDoThingId);
        notification.addBooleanProperty("isNotify");
        notification.addDateProperty("reminderDate");
        notification.addIntProperty("remindFrequency");
        notification.addIntProperty("remindFrequencyInterval");
        notification.addIntProperty("remindCount");
        notification.addDateProperty("endDate");
        notification.addBooleanProperty("isSynchronize");

        Property notificationId = notification.addLongProperty("notificationId").notNull().getProperty();
//        Property todoThingPropertyNotificationId = toDoThing.addLongProperty("notificationId").getProperty();
        //一对多
        ToMany toDoThingToNotifition =toDoThing.addToMany(notification,notificationId);
        toDoThingToNotifition.setName("norifications");
        //根据 notification 的ID 升序排列
        toDoThingToNotifition.orderAsc(notificationId);


    }


}
