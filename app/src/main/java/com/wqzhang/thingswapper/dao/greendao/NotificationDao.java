package com.wqzhang.thingswapper.dao.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "NOTIFICATION".
*/
public class NotificationDao extends AbstractDao<Notification, Long> {

    public static final String TABLENAME = "NOTIFICATION";

    /**
     * Properties of entity Notification.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property IsNotify = new Property(1, Boolean.class, "isNotify", false, "IS_NOTIFY");
        public final static Property ReminderDate = new Property(2, java.util.Date.class, "reminderDate", false, "REMINDER_DATE");
        public final static Property RemindFrequency = new Property(3, Integer.class, "remindFrequency", false, "REMIND_FREQUENCY");
        public final static Property RemindFrequencyInterval = new Property(4, Integer.class, "remindFrequencyInterval", false, "REMIND_FREQUENCY_INTERVAL");
        public final static Property RemindCount = new Property(5, Integer.class, "remindCount", false, "REMIND_COUNT");
        public final static Property EndDate = new Property(6, java.util.Date.class, "endDate", false, "END_DATE");
        public final static Property IsSynchronize = new Property(7, Boolean.class, "isSynchronize", false, "IS_SYNCHRONIZE");
    }

    private DaoSession daoSession;


    public NotificationDao(DaoConfig config) {
        super(config);
    }
    
    public NotificationDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"NOTIFICATION\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"IS_NOTIFY\" INTEGER," + // 1: isNotify
                "\"REMINDER_DATE\" INTEGER," + // 2: reminderDate
                "\"REMIND_FREQUENCY\" INTEGER," + // 3: remindFrequency
                "\"REMIND_FREQUENCY_INTERVAL\" INTEGER," + // 4: remindFrequencyInterval
                "\"REMIND_COUNT\" INTEGER," + // 5: remindCount
                "\"END_DATE\" INTEGER," + // 6: endDate
                "\"IS_SYNCHRONIZE\" INTEGER);"); // 7: isSynchronize
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"NOTIFICATION\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Notification entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        Boolean isNotify = entity.getIsNotify();
        if (isNotify != null) {
            stmt.bindLong(2, isNotify ? 1L: 0L);
        }
 
        java.util.Date reminderDate = entity.getReminderDate();
        if (reminderDate != null) {
            stmt.bindLong(3, reminderDate.getTime());
        }
 
        Integer remindFrequency = entity.getRemindFrequency();
        if (remindFrequency != null) {
            stmt.bindLong(4, remindFrequency);
        }
 
        Integer remindFrequencyInterval = entity.getRemindFrequencyInterval();
        if (remindFrequencyInterval != null) {
            stmt.bindLong(5, remindFrequencyInterval);
        }
 
        Integer remindCount = entity.getRemindCount();
        if (remindCount != null) {
            stmt.bindLong(6, remindCount);
        }
 
        java.util.Date endDate = entity.getEndDate();
        if (endDate != null) {
            stmt.bindLong(7, endDate.getTime());
        }
 
        Boolean isSynchronize = entity.getIsSynchronize();
        if (isSynchronize != null) {
            stmt.bindLong(8, isSynchronize ? 1L: 0L);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Notification entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        Boolean isNotify = entity.getIsNotify();
        if (isNotify != null) {
            stmt.bindLong(2, isNotify ? 1L: 0L);
        }
 
        java.util.Date reminderDate = entity.getReminderDate();
        if (reminderDate != null) {
            stmt.bindLong(3, reminderDate.getTime());
        }
 
        Integer remindFrequency = entity.getRemindFrequency();
        if (remindFrequency != null) {
            stmt.bindLong(4, remindFrequency);
        }
 
        Integer remindFrequencyInterval = entity.getRemindFrequencyInterval();
        if (remindFrequencyInterval != null) {
            stmt.bindLong(5, remindFrequencyInterval);
        }
 
        Integer remindCount = entity.getRemindCount();
        if (remindCount != null) {
            stmt.bindLong(6, remindCount);
        }
 
        java.util.Date endDate = entity.getEndDate();
        if (endDate != null) {
            stmt.bindLong(7, endDate.getTime());
        }
 
        Boolean isSynchronize = entity.getIsSynchronize();
        if (isSynchronize != null) {
            stmt.bindLong(8, isSynchronize ? 1L: 0L);
        }
    }

    @Override
    protected final void attachEntity(Notification entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public Notification readEntity(Cursor cursor, int offset) {
        Notification entity = new Notification( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getShort(offset + 1) != 0, // isNotify
            cursor.isNull(offset + 2) ? null : new java.util.Date(cursor.getLong(offset + 2)), // reminderDate
            cursor.isNull(offset + 3) ? null : cursor.getInt(offset + 3), // remindFrequency
            cursor.isNull(offset + 4) ? null : cursor.getInt(offset + 4), // remindFrequencyInterval
            cursor.isNull(offset + 5) ? null : cursor.getInt(offset + 5), // remindCount
            cursor.isNull(offset + 6) ? null : new java.util.Date(cursor.getLong(offset + 6)), // endDate
            cursor.isNull(offset + 7) ? null : cursor.getShort(offset + 7) != 0 // isSynchronize
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Notification entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setIsNotify(cursor.isNull(offset + 1) ? null : cursor.getShort(offset + 1) != 0);
        entity.setReminderDate(cursor.isNull(offset + 2) ? null : new java.util.Date(cursor.getLong(offset + 2)));
        entity.setRemindFrequency(cursor.isNull(offset + 3) ? null : cursor.getInt(offset + 3));
        entity.setRemindFrequencyInterval(cursor.isNull(offset + 4) ? null : cursor.getInt(offset + 4));
        entity.setRemindCount(cursor.isNull(offset + 5) ? null : cursor.getInt(offset + 5));
        entity.setEndDate(cursor.isNull(offset + 6) ? null : new java.util.Date(cursor.getLong(offset + 6)));
        entity.setIsSynchronize(cursor.isNull(offset + 7) ? null : cursor.getShort(offset + 7) != 0);
     }
    
    @Override
    protected final Long updateKeyAfterInsert(Notification entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(Notification entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(Notification entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
