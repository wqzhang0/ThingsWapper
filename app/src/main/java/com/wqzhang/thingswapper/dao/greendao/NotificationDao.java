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
        public final static Property NotifyType = new Property(1, Integer.class, "notifyType", false, "NOTIFY_TYPE");
        public final static Property ReminderDate = new Property(2, java.util.Date.class, "reminderDate", false, "REMINDER_DATE");
        public final static Property RepeatType = new Property(3, String.class, "repeatType", false, "REPEAT_TYPE");
        public final static Property RemindFrequency = new Property(4, Integer.class, "remindFrequency", false, "REMIND_FREQUENCY");
        public final static Property RemindFrequencyInterval = new Property(5, Integer.class, "remindFrequencyInterval", false, "REMIND_FREQUENCY_INTERVAL");
        public final static Property RemindCount = new Property(6, Integer.class, "remindCount", false, "REMIND_COUNT");
        public final static Property EndDate = new Property(7, java.util.Date.class, "endDate", false, "END_DATE");
        public final static Property Synchronize = new Property(8, Boolean.class, "synchronize", false, "SYNCHRONIZE");
        public final static Property PreNotifyDate = new Property(9, java.util.Date.class, "preNotifyDate", false, "PRE_NOTIFY_DATE");
        public final static Property AlearyNotify = new Property(10, Boolean.class, "alearyNotify", false, "ALEARY_NOTIFY");
        public final static Property Invalide = new Property(11, Boolean.class, "invalide", false, "INVALIDE");
        public final static Property NextRemindDate = new Property(12, java.util.Date.class, "nextRemindDate", false, "NEXT_REMIND_DATE");
        public final static Property ServiceId = new Property(13, Long.class, "serviceId", false, "SERVICE_ID");
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
                "\"NOTIFY_TYPE\" INTEGER," + // 1: notifyType
                "\"REMINDER_DATE\" INTEGER," + // 2: reminderDate
                "\"REPEAT_TYPE\" TEXT," + // 3: repeatType
                "\"REMIND_FREQUENCY\" INTEGER," + // 4: remindFrequency
                "\"REMIND_FREQUENCY_INTERVAL\" INTEGER," + // 5: remindFrequencyInterval
                "\"REMIND_COUNT\" INTEGER," + // 6: remindCount
                "\"END_DATE\" INTEGER," + // 7: endDate
                "\"SYNCHRONIZE\" INTEGER," + // 8: synchronize
                "\"PRE_NOTIFY_DATE\" INTEGER," + // 9: preNotifyDate
                "\"ALEARY_NOTIFY\" INTEGER," + // 10: alearyNotify
                "\"INVALIDE\" INTEGER," + // 11: invalide
                "\"NEXT_REMIND_DATE\" INTEGER," + // 12: nextRemindDate
                "\"SERVICE_ID\" INTEGER);"); // 13: serviceId
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
 
        Integer notifyType = entity.getNotifyType();
        if (notifyType != null) {
            stmt.bindLong(2, notifyType);
        }
 
        java.util.Date reminderDate = entity.getReminderDate();
        if (reminderDate != null) {
            stmt.bindLong(3, reminderDate.getTime());
        }
 
        String repeatType = entity.getRepeatType();
        if (repeatType != null) {
            stmt.bindString(4, repeatType);
        }
 
        Integer remindFrequency = entity.getRemindFrequency();
        if (remindFrequency != null) {
            stmt.bindLong(5, remindFrequency);
        }
 
        Integer remindFrequencyInterval = entity.getRemindFrequencyInterval();
        if (remindFrequencyInterval != null) {
            stmt.bindLong(6, remindFrequencyInterval);
        }
 
        Integer remindCount = entity.getRemindCount();
        if (remindCount != null) {
            stmt.bindLong(7, remindCount);
        }
 
        java.util.Date endDate = entity.getEndDate();
        if (endDate != null) {
            stmt.bindLong(8, endDate.getTime());
        }
 
        Boolean synchronize = entity.getSynchronize();
        if (synchronize != null) {
            stmt.bindLong(9, synchronize ? 1L: 0L);
        }
 
        java.util.Date preNotifyDate = entity.getPreNotifyDate();
        if (preNotifyDate != null) {
            stmt.bindLong(10, preNotifyDate.getTime());
        }
 
        Boolean alearyNotify = entity.getAlearyNotify();
        if (alearyNotify != null) {
            stmt.bindLong(11, alearyNotify ? 1L: 0L);
        }
 
        Boolean invalide = entity.getInvalide();
        if (invalide != null) {
            stmt.bindLong(12, invalide ? 1L: 0L);
        }
 
        java.util.Date nextRemindDate = entity.getNextRemindDate();
        if (nextRemindDate != null) {
            stmt.bindLong(13, nextRemindDate.getTime());
        }
 
        Long serviceId = entity.getServiceId();
        if (serviceId != null) {
            stmt.bindLong(14, serviceId);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Notification entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        Integer notifyType = entity.getNotifyType();
        if (notifyType != null) {
            stmt.bindLong(2, notifyType);
        }
 
        java.util.Date reminderDate = entity.getReminderDate();
        if (reminderDate != null) {
            stmt.bindLong(3, reminderDate.getTime());
        }
 
        String repeatType = entity.getRepeatType();
        if (repeatType != null) {
            stmt.bindString(4, repeatType);
        }
 
        Integer remindFrequency = entity.getRemindFrequency();
        if (remindFrequency != null) {
            stmt.bindLong(5, remindFrequency);
        }
 
        Integer remindFrequencyInterval = entity.getRemindFrequencyInterval();
        if (remindFrequencyInterval != null) {
            stmt.bindLong(6, remindFrequencyInterval);
        }
 
        Integer remindCount = entity.getRemindCount();
        if (remindCount != null) {
            stmt.bindLong(7, remindCount);
        }
 
        java.util.Date endDate = entity.getEndDate();
        if (endDate != null) {
            stmt.bindLong(8, endDate.getTime());
        }
 
        Boolean synchronize = entity.getSynchronize();
        if (synchronize != null) {
            stmt.bindLong(9, synchronize ? 1L: 0L);
        }
 
        java.util.Date preNotifyDate = entity.getPreNotifyDate();
        if (preNotifyDate != null) {
            stmt.bindLong(10, preNotifyDate.getTime());
        }
 
        Boolean alearyNotify = entity.getAlearyNotify();
        if (alearyNotify != null) {
            stmt.bindLong(11, alearyNotify ? 1L: 0L);
        }
 
        Boolean invalide = entity.getInvalide();
        if (invalide != null) {
            stmt.bindLong(12, invalide ? 1L: 0L);
        }
 
        java.util.Date nextRemindDate = entity.getNextRemindDate();
        if (nextRemindDate != null) {
            stmt.bindLong(13, nextRemindDate.getTime());
        }
 
        Long serviceId = entity.getServiceId();
        if (serviceId != null) {
            stmt.bindLong(14, serviceId);
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
            cursor.isNull(offset + 1) ? null : cursor.getInt(offset + 1), // notifyType
            cursor.isNull(offset + 2) ? null : new java.util.Date(cursor.getLong(offset + 2)), // reminderDate
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // repeatType
            cursor.isNull(offset + 4) ? null : cursor.getInt(offset + 4), // remindFrequency
            cursor.isNull(offset + 5) ? null : cursor.getInt(offset + 5), // remindFrequencyInterval
            cursor.isNull(offset + 6) ? null : cursor.getInt(offset + 6), // remindCount
            cursor.isNull(offset + 7) ? null : new java.util.Date(cursor.getLong(offset + 7)), // endDate
            cursor.isNull(offset + 8) ? null : cursor.getShort(offset + 8) != 0, // synchronize
            cursor.isNull(offset + 9) ? null : new java.util.Date(cursor.getLong(offset + 9)), // preNotifyDate
            cursor.isNull(offset + 10) ? null : cursor.getShort(offset + 10) != 0, // alearyNotify
            cursor.isNull(offset + 11) ? null : cursor.getShort(offset + 11) != 0, // invalide
            cursor.isNull(offset + 12) ? null : new java.util.Date(cursor.getLong(offset + 12)), // nextRemindDate
            cursor.isNull(offset + 13) ? null : cursor.getLong(offset + 13) // serviceId
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Notification entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setNotifyType(cursor.isNull(offset + 1) ? null : cursor.getInt(offset + 1));
        entity.setReminderDate(cursor.isNull(offset + 2) ? null : new java.util.Date(cursor.getLong(offset + 2)));
        entity.setRepeatType(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setRemindFrequency(cursor.isNull(offset + 4) ? null : cursor.getInt(offset + 4));
        entity.setRemindFrequencyInterval(cursor.isNull(offset + 5) ? null : cursor.getInt(offset + 5));
        entity.setRemindCount(cursor.isNull(offset + 6) ? null : cursor.getInt(offset + 6));
        entity.setEndDate(cursor.isNull(offset + 7) ? null : new java.util.Date(cursor.getLong(offset + 7)));
        entity.setSynchronize(cursor.isNull(offset + 8) ? null : cursor.getShort(offset + 8) != 0);
        entity.setPreNotifyDate(cursor.isNull(offset + 9) ? null : new java.util.Date(cursor.getLong(offset + 9)));
        entity.setAlearyNotify(cursor.isNull(offset + 10) ? null : cursor.getShort(offset + 10) != 0);
        entity.setInvalide(cursor.isNull(offset + 11) ? null : cursor.getShort(offset + 11) != 0);
        entity.setNextRemindDate(cursor.isNull(offset + 12) ? null : new java.util.Date(cursor.getLong(offset + 12)));
        entity.setServiceId(cursor.isNull(offset + 13) ? null : cursor.getLong(offset + 13));
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
