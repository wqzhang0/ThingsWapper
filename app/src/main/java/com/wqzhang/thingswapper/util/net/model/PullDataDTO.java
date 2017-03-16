package com.wqzhang.thingswapper.util.net.model;

import org.greenrobot.greendao.annotation.Id;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by wqzhang on 17-3-16.
 */

public class PullDataDTO {
    private Long userId;
    private int syncVerson;
    private int targetClinetSQLVersion;

    private ArrayList<ToDoThingDTO> data;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public int getSyncVerson() {
        return syncVerson;
    }

    public void setSyncVerson(int syncVerson) {
        this.syncVerson = syncVerson;
    }

    public int getTargetClinetSQLVersion() {
        return targetClinetSQLVersion;
    }

    public void setTargetClinetSQLVersion(int targetClinetSQLVersion) {
        this.targetClinetSQLVersion = targetClinetSQLVersion;
    }

    public ArrayList<ToDoThingDTO> getData() {
        return data;
    }

    public void setData(ArrayList<ToDoThingDTO> data) {
        this.data = data;
    }

    class ToDoThingDTO {
        private Long id;
        private String reminderContext;
        private Integer reminderType;
        private java.util.Date createDate;
        private java.util.Date finshDate;
        private Integer status;
        private Boolean synchronize;
        private Long serviceId;
        private Long userId;
        private ArrayList<Notification> notifications;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getReminderContext() {
            return reminderContext;
        }

        public void setReminderContext(String reminderContext) {
            this.reminderContext = reminderContext;
        }

        public Integer getReminderType() {
            return reminderType;
        }

        public void setReminderType(Integer reminderType) {
            this.reminderType = reminderType;
        }

        public Date getCreateDate() {
            return createDate;
        }

        public void setCreateDate(Date createDate) {
            this.createDate = createDate;
        }

        public Date getFinshDate() {
            return finshDate;
        }

        public void setFinshDate(Date finshDate) {
            this.finshDate = finshDate;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public Boolean getSynchronize() {
            return synchronize;
        }

        public void setSynchronize(Boolean synchronize) {
            this.synchronize = synchronize;
        }

        public Long getServiceId() {
            return serviceId;
        }

        public void setServiceId(Long serviceId) {
            this.serviceId = serviceId;
        }

        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }

        public ArrayList<Notification> getNotifications() {
            return notifications;
        }

        public void setNotifications(ArrayList<Notification> notifications) {
            this.notifications = notifications;
        }
    }

    class Notification {
        private Long id;
        private Integer notifyType;
        private java.util.Date reminderDate;
        private String repeatType;
        private Integer remindFrequency;
        private Integer remindFrequencyInterval;
        private Integer remindCount;
        private java.util.Date endDate;
        private Boolean synchronize;
        private java.util.Date preNotifyDate;
        private Boolean alearyNotify;
        private Boolean invalide;
        private java.util.Date nextRemindDate;
        private Long serviceId;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Integer getNotifyType() {
            return notifyType;
        }

        public void setNotifyType(Integer notifyType) {
            this.notifyType = notifyType;
        }

        public Date getReminderDate() {
            return reminderDate;
        }

        public void setReminderDate(Date reminderDate) {
            this.reminderDate = reminderDate;
        }

        public String getRepeatType() {
            return repeatType;
        }

        public void setRepeatType(String repeatType) {
            this.repeatType = repeatType;
        }

        public Integer getRemindFrequency() {
            return remindFrequency;
        }

        public void setRemindFrequency(Integer remindFrequency) {
            this.remindFrequency = remindFrequency;
        }

        public Integer getRemindFrequencyInterval() {
            return remindFrequencyInterval;
        }

        public void setRemindFrequencyInterval(Integer remindFrequencyInterval) {
            this.remindFrequencyInterval = remindFrequencyInterval;
        }

        public Integer getRemindCount() {
            return remindCount;
        }

        public void setRemindCount(Integer remindCount) {
            this.remindCount = remindCount;
        }

        public Date getEndDate() {
            return endDate;
        }

        public void setEndDate(Date endDate) {
            this.endDate = endDate;
        }

        public Boolean getSynchronize() {
            return synchronize;
        }

        public void setSynchronize(Boolean synchronize) {
            this.synchronize = synchronize;
        }

        public Date getPreNotifyDate() {
            return preNotifyDate;
        }

        public void setPreNotifyDate(Date preNotifyDate) {
            this.preNotifyDate = preNotifyDate;
        }

        public Boolean getAlearyNotify() {
            return alearyNotify;
        }

        public void setAlearyNotify(Boolean alearyNotify) {
            this.alearyNotify = alearyNotify;
        }

        public Boolean getInvalide() {
            return invalide;
        }

        public void setInvalide(Boolean invalide) {
            this.invalide = invalide;
        }

        public Date getNextRemindDate() {
            return nextRemindDate;
        }

        public void setNextRemindDate(Date nextRemindDate) {
            this.nextRemindDate = nextRemindDate;
        }

        public Long getServiceId() {
            return serviceId;
        }

        public void setServiceId(Long serviceId) {
            this.serviceId = serviceId;
        }
    }
}
