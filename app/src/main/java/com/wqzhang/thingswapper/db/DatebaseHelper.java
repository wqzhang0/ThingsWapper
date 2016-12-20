package com.wqzhang.thingswapper.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.wqzhang.thingswapper.MainApplication;
import com.wqzhang.thingswapper.model.ToDoThingModel;
import com.wqzhang.thingswapper.model.UserModel;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by wqzhang on 16-12-8.
 */

public class DatebaseHelper extends SQLiteOpenHelper implements dbOperationImpl {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "ThingsWapper";
    private static final String TABLE_NAME_USER_INFO = "user_info";
    private static final String TABLE_NAME_PERSONALIZED_SETTING = "personalized_setting";
    private static Context mContext;
    private static DatebaseHelper datebaseHelper;

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

//        sqLiteDatabase.openOrCreateDatabase("CREATE TABLE ");
        sqLiteDatabase.execSQL("create table " + TABLE_NAME_USER_INFO + " (" +
                "id integer primary key autoincrement, " +
                "name text , " +
                "account text ," +
                "password text ," +
                "email text ," +
                "createDate integer , " +
                "isSynchronize boolean " +
                ")");
        sqLiteDatabase.execSQL("create table " + TABLE_NAME_PERSONALIZED_SETTING + " (" +
                "id integer primary key autoincrement, " +
                "userId integer , " +
                "reminderType integer ," +
                "reminderTime integer ," +
                "isSynchronize boolean ," +
                "isDefault boolean" +
                ")");
    }

    public DatebaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        mContext = context;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //升级模块
//        mContext.deleteDatabase(DATABASE_NAME);
//        new DatebaseHelper(mContext);
    }

    public static DatebaseHelper getInstance(){
        if (datebaseHelper == null                ) {
            datebaseHelper = new DatebaseHelper(MainApplication.getGlobleContext());

        }
        return datebaseHelper;
    }

    @Override
    public void addUser(UserModel userModel) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put("name", "test");
//        contentValues.put("email", "wqzhang@iflytek.com");
//        contentValues.put("account", "wqzhang4");
//        contentValues.put("password", "Bate175756833");
//        sqLiteDatabase.insert("user_info", null, contentValues);
        sqLiteDatabase.execSQL("insert into user_info (name,account,password,email,createDate,isSynchronize) values (?,?,?,?,?,?)",
                new Object[]{userModel.getName(), userModel.getAccount(), userModel.getPassword(), userModel.getEmail(), userModel.getCreateDate().getTime(), userModel.isSynchronize()});
//        sqLiteDatabase.execSQL("");

    }

    @Override
    public UserModel readUserInfo() {
        ArrayList<UserModel> userModels = new ArrayList<>();
        Cursor cursor = getReadableDatabase().rawQuery("select * from user_info", new String[]{});
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String account = cursor.getString(cursor.getColumnIndex("account"));
            String password = cursor.getString(cursor.getColumnIndex("password"));
            String email = cursor.getString(cursor.getColumnIndex("email"));
            Date createDate = new Date(cursor.getLong(cursor.getColumnIndex("email")));
            UserModel userModel = new UserModel(id, name, account, password, email, createDate);

            userModels.add(userModel);
            Log.d("SQL", name + id);
        }
        cursor.close();
        return userModels.get(0);
    }

    @Override
    public boolean addToDoThing(ToDoThingModel toDoThingModel) {
        return false;
    }

}