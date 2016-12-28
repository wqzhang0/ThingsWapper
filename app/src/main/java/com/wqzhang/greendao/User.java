package com.wqzhang.greendao;

import org.greenrobot.greendao.annotation.*;

import java.util.List;
import com.wqzhang.greendao.DaoSession;
import org.greenrobot.greendao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END

/**
 * user Model ,保存用户基本信息
 */
@Entity(active = true)
public class User {

    @Id
    private Long id;
    private String name;
    private String account;
    private String password;
    private String email;
    private java.util.Date createDate;
    private Boolean isSynchronize;

    /** Used to resolve relations */
    @Generated
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated
    private transient UserDao myDao;

    @ToMany(joinProperties = {
        @JoinProperty(name = "id", referencedName = "userId")
    })
    private List<ToDoThing> toDoThings;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    @Generated
    public User() {
    }

    public User(Long id) {
        this.id = id;
    }

    @Generated
    public User(Long id, String name, String account, String password, String email, java.util.Date createDate, Boolean isSynchronize) {
        this.id = id;
        this.name = name;
        this.account = account;
        this.password = password;
        this.email = email;
        this.createDate = createDate;
        this.isSynchronize = isSynchronize;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getUserDao() : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public java.util.Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(java.util.Date createDate) {
        this.createDate = createDate;
    }

    public Boolean getIsSynchronize() {
        return isSynchronize;
    }

    public void setIsSynchronize(Boolean isSynchronize) {
        this.isSynchronize = isSynchronize;
    }

    /** To-many relationship, resolved on first access (and after reset). Changes to to-many relations are not persisted, make changes to the target entity. */
    @Generated
    public List<ToDoThing> getToDoThings() {
        if (toDoThings == null) {
            __throwIfDetached();
            ToDoThingDao targetDao = daoSession.getToDoThingDao();
            List<ToDoThing> toDoThingsNew = targetDao._queryUser_ToDoThings(id);
            synchronized (this) {
                if(toDoThings == null) {
                    toDoThings = toDoThingsNew;
                }
            }
        }
        return toDoThings;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated
    public synchronized void resetToDoThings() {
        toDoThings = null;
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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", createDate=" + createDate +
                ", isSynchronize=" + isSynchronize +
                ", daoSession=" + daoSession +
                ", myDao=" + myDao +
                ", toDoThings=" + toDoThings +
                '}';
    }

    // KEEP METHODS END

}
