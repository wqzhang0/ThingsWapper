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
    private Boolean isNotify;
    private java.util.Date reminderDate;
    private Integer remindFrequency;
    private Integer remindFrequencyInterval;
    private Integer remindCount;
    private java.util.Date endDate;
    private Boolean isSynchronize;

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
    public Notification(Long id, Boolean isNotify, java.util.Date reminderDate, Integer remindFrequency, Integer remindFrequencyInterval, Integer remindCount, java.util.Date endDate, Boolean isSynchronize) {
        this.id = id;
        this.isNotify = isNotify;
        this.reminderDate = reminderDate;
        this.remindFrequency = remindFrequency;
        this.remindFrequencyInterval = remindFrequencyInterval;
        this.remindCount = remindCount;
        this.endDate = endDate;
        this.isSynchronize = isSynchronize;
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

    public Boolean getIsNotify() {
        return isNotify;
    }

    public void setIsNotify(Boolean isNotify) {
        this.isNotify = isNotify;
    }

    public java.util.Date getReminderDate() {
        return reminderDate;
    }

    public void setReminderDate(java.util.Date reminderDate) {
        this.reminderDate = reminderDate;
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

    public Boolean getIsSynchronize() {
        return isSynchronize;
    }

    public void setIsSynchronize(Boolean isSynchronize) {
        this.isSynchronize = isSynchronize;
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
