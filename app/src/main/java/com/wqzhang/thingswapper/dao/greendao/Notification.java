package com.wqzhang.thingswapper.dao.greendao;

import org.greenrobot.greendao.annotation.*;

import java.util.List;
import com.wqzhang.thingswapper.dao.greendao.DaoSession;
import org.greenrobot.greendao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END

/**
 * Entity mapped to table "NOTIFICATION".
 */
@Entity(active = true)
public class Notification {

    @Id
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

    /** Used to resolve relations */
    @Generated
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated
    private transient NotificationDao myDao;

    @ToMany(joinProperties = {
        @JoinProperty(name = "id", referencedName = "notificationId")
    })
    private List<Connection_T_N> toDoThingIds;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    @Generated
    public Notification() {
    }

    public Notification(Long id) {
        this.id = id;
    }

    @Generated
    public Notification(Long id, Integer notifyType, java.util.Date reminderDate, String repeatType, Integer remindFrequency, Integer remindFrequencyInterval, Integer remindCount, java.util.Date endDate, Boolean synchronize, java.util.Date preNotifyDate, Boolean alearyNotify, Boolean invalide, java.util.Date nextRemindDate, Long serviceId) {
        this.id = id;
        this.notifyType = notifyType;
        this.reminderDate = reminderDate;
        this.repeatType = repeatType;
        this.remindFrequency = remindFrequency;
        this.remindFrequencyInterval = remindFrequencyInterval;
        this.remindCount = remindCount;
        this.endDate = endDate;
        this.synchronize = synchronize;
        this.preNotifyDate = preNotifyDate;
        this.alearyNotify = alearyNotify;
        this.invalide = invalide;
        this.nextRemindDate = nextRemindDate;
        this.serviceId = serviceId;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getNotificationDao() : null;
    }

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

    public java.util.Date getReminderDate() {
        return reminderDate;
    }

    public void setReminderDate(java.util.Date reminderDate) {
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

    public java.util.Date getEndDate() {
        return endDate;
    }

    public void setEndDate(java.util.Date endDate) {
        this.endDate = endDate;
    }

    public Boolean getSynchronize() {
        return synchronize;
    }

    public void setSynchronize(Boolean synchronize) {
        this.synchronize = synchronize;
    }

    public java.util.Date getPreNotifyDate() {
        return preNotifyDate;
    }

    public void setPreNotifyDate(java.util.Date preNotifyDate) {
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

    public java.util.Date getNextRemindDate() {
        return nextRemindDate;
    }

    public void setNextRemindDate(java.util.Date nextRemindDate) {
        this.nextRemindDate = nextRemindDate;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    /** To-many relationship, resolved on first access (and after reset). Changes to to-many relations are not persisted, make changes to the target entity. */
    @Generated
    public List<Connection_T_N> getToDoThingIds() {
        if (toDoThingIds == null) {
            __throwIfDetached();
            Connection_T_NDao targetDao = daoSession.getConnection_T_NDao();
            List<Connection_T_N> toDoThingIdsNew = targetDao._queryNotification_ToDoThingIds(id);
            synchronized (this) {
                if(toDoThingIds == null) {
                    toDoThingIds = toDoThingIdsNew;
                }
            }
        }
        return toDoThingIds;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated
    public synchronized void resetToDoThingIds() {
        toDoThingIds = null;
    }

    /**
    * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
    * Entity must attached to an entity context.
    */
    @Generated
    public void delete() {
        __throwIfDetached();
        myDao.delete(this);
    }

    /**
    * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
    * Entity must attached to an entity context.
    */
    @Generated
    public void update() {
        __throwIfDetached();
        myDao.update(this);
    }

    /**
    * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
    * Entity must attached to an entity context.
    */
    @Generated
    public void refresh() {
        __throwIfDetached();
        myDao.refresh(this);
    }

    @Generated
    private void __throwIfDetached() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
    }

    // KEEP METHODS - put your custom methods here
    // KEEP METHODS END

}
