package com.wqzhang.thingswapper.dao.greendao;

import java.util.List;
import java.util.ArrayList;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.SqlUtils;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "CONNECTION__T__N".
*/
public class Connection_T_NDao extends AbstractDao<Connection_T_N, Long> {

    public static final String TABLENAME = "CONNECTION__T__N";

    /**
     * Properties of entity Connection_T_N.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property ToDoThingId = new Property(1, Long.class, "toDoThingId", false, "TO_DO_THING_ID");
        public final static Property NotificationId = new Property(2, Long.class, "notificationId", false, "NOTIFICATION_ID");
    }

    private DaoSession daoSession;

    private Query<Connection_T_N> toDoThing_NotificationIdsQuery;
    private Query<Connection_T_N> notification_ToDoThingIdsQuery;

    public Connection_T_NDao(DaoConfig config) {
        super(config);
    }
    
    public Connection_T_NDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"CONNECTION__T__N\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"TO_DO_THING_ID\" INTEGER," + // 1: toDoThingId
                "\"NOTIFICATION_ID\" INTEGER);"); // 2: notificationId
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"CONNECTION__T__N\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Connection_T_N entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        Long toDoThingId = entity.getToDoThingId();
        if (toDoThingId != null) {
            stmt.bindLong(2, toDoThingId);
        }
 
        Long notificationId = entity.getNotificationId();
        if (notificationId != null) {
            stmt.bindLong(3, notificationId);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Connection_T_N entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        Long toDoThingId = entity.getToDoThingId();
        if (toDoThingId != null) {
            stmt.bindLong(2, toDoThingId);
        }
 
        Long notificationId = entity.getNotificationId();
        if (notificationId != null) {
            stmt.bindLong(3, notificationId);
        }
    }

    @Override
    protected final void attachEntity(Connection_T_N entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public Connection_T_N readEntity(Cursor cursor, int offset) {
        Connection_T_N entity = new Connection_T_N( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1), // toDoThingId
            cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2) // notificationId
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Connection_T_N entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setToDoThingId(cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1));
        entity.setNotificationId(cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(Connection_T_N entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(Connection_T_N entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(Connection_T_N entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
    /** Internal query to resolve the "notificationIds" to-many relationship of ToDoThing. */
    public List<Connection_T_N> _queryToDoThing_NotificationIds(Long toDoThingId) {
        synchronized (this) {
            if (toDoThing_NotificationIdsQuery == null) {
                QueryBuilder<Connection_T_N> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.ToDoThingId.eq(null));
                toDoThing_NotificationIdsQuery = queryBuilder.build();
            }
        }
        Query<Connection_T_N> query = toDoThing_NotificationIdsQuery.forCurrentThread();
        query.setParameter(0, toDoThingId);
        return query.list();
    }

    /** Internal query to resolve the "toDoThingIds" to-many relationship of Notification. */
    public List<Connection_T_N> _queryNotification_ToDoThingIds(Long notificationId) {
        synchronized (this) {
            if (notification_ToDoThingIdsQuery == null) {
                QueryBuilder<Connection_T_N> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.NotificationId.eq(null));
                notification_ToDoThingIdsQuery = queryBuilder.build();
            }
        }
        Query<Connection_T_N> query = notification_ToDoThingIdsQuery.forCurrentThread();
        query.setParameter(0, notificationId);
        return query.list();
    }

    private String selectDeep;

    protected String getSelectDeep() {
        if (selectDeep == null) {
            StringBuilder builder = new StringBuilder("SELECT ");
            SqlUtils.appendColumns(builder, "T", getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T0", daoSession.getToDoThingDao().getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T1", daoSession.getNotificationDao().getAllColumns());
            builder.append(" FROM CONNECTION__T__N T");
            builder.append(" LEFT JOIN TO_DO_THING T0 ON T.\"TO_DO_THING_ID\"=T0.\"_id\"");
            builder.append(" LEFT JOIN NOTIFICATION T1 ON T.\"NOTIFICATION_ID\"=T1.\"_id\"");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }
    
    protected Connection_T_N loadCurrentDeep(Cursor cursor, boolean lock) {
        Connection_T_N entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        ToDoThing toDoThing = loadCurrentOther(daoSession.getToDoThingDao(), cursor, offset);
        entity.setToDoThing(toDoThing);
        offset += daoSession.getToDoThingDao().getAllColumns().length;

        Notification notification = loadCurrentOther(daoSession.getNotificationDao(), cursor, offset);
        entity.setNotification(notification);

        return entity;    
    }

    public Connection_T_N loadDeep(Long key) {
        assertSinglePk();
        if (key == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder(getSelectDeep());
        builder.append("WHERE ");
        SqlUtils.appendColumnsEqValue(builder, "T", getPkColumns());
        String sql = builder.toString();
        
        String[] keyArray = new String[] { key.toString() };
        Cursor cursor = db.rawQuery(sql, keyArray);
        
        try {
            boolean available = cursor.moveToFirst();
            if (!available) {
                return null;
            } else if (!cursor.isLast()) {
                throw new IllegalStateException("Expected unique result, but count was " + cursor.getCount());
            }
            return loadCurrentDeep(cursor, true);
        } finally {
            cursor.close();
        }
    }
    
    /** Reads all available rows from the given cursor and returns a list of new ImageTO objects. */
    public List<Connection_T_N> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<Connection_T_N> list = new ArrayList<Connection_T_N>(count);
        
        if (cursor.moveToFirst()) {
            if (identityScope != null) {
                identityScope.lock();
                identityScope.reserveRoom(count);
            }
            try {
                do {
                    list.add(loadCurrentDeep(cursor, false));
                } while (cursor.moveToNext());
            } finally {
                if (identityScope != null) {
                    identityScope.unlock();
                }
            }
        }
        return list;
    }
    
    protected List<Connection_T_N> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }
    

    /** A raw-style query where you can pass any WHERE clause and arguments. */
    public List<Connection_T_N> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }
 
}
