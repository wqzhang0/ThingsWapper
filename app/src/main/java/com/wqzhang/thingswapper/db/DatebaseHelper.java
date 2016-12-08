package com.wqzhang.thingswapper.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by wqzhang on 16-12-8.
 */

public class DatebaseHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "ThingsWapper";
    private static SQLiteDatabase mSqLiteDatabase;
    private static Context mContext ;

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

//        sqLiteDatabase.openOrCreateDatabase("CREATE TABLE ");
        sqLiteDatabase.execSQL("create table user_info (id integer primary key autoincrement, name text , email text , account text , password text)");

        ContentValues contentValues = new ContentValues();
        contentValues.put("name","test");
        contentValues.put("email","wqzhang@iflytek.com");
        contentValues.put("account","wqzhang4");
        contentValues.put("password","Bate175756833");
        sqLiteDatabase.insert("user_info",null,contentValues);

        sqLiteDatabase.execSQL("insert into user_info (name,account,email) values ( ?,?,?)",new Object[]{"exeInsert","sss","ssss"});
        mSqLiteDatabase = sqLiteDatabase;
    }

    public DatebaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        mContext = context;

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //升级模块

    }

    public void readUserInfo(){

        Cursor cursor =  getReadableDatabase().rawQuery("select * from user_info",new String[]{});
        while(cursor.moveToNext()){
//            Toast.makeText(mContext,cursor.getInt(1),Toast.LENGTH_LONG);
//            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
//            String number = cursor.getString(cursor.getColumnIndex("text"));

            Log.d("SQL",name);
        }
        cursor.close();
//        while(cursor.moveToFirst())
//        mSqLiteDatabase.execSQL("select * from user_info");
    }
}
