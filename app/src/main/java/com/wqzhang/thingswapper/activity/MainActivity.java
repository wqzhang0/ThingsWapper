package com.wqzhang.thingswapper.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.wqzhang.greendao.User;
import com.wqzhang.greendao.UserDao;
import com.wqzhang.thingswapper.MainApplication;
import com.wqzhang.thingswapper.R;
import com.wqzhang.thingswapper.fragment.ToDoFragment;
import com.wqzhang.thingswapper.db.DatebaseHelper;

import org.greenrobot.greendao.query.Query;

import java.util.ArrayList;
import java.util.Date;


public class MainActivity extends Activity implements View.OnClickListener {

    private String TAG = "MainActivity";
    private DatebaseHelper datebaseHelper = null;
    private TextView settingTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.to_do_main);
//        datebaseHelper = DatebaseHelper.getInstance();
        settingTextView = (TextView) findViewById(R.id.setting);
        settingTextView.setOnClickListener(this);

        setDefaultFragment();

        readOrAddUserInfo();

    }

    private void setDefaultFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment f = new ToDoFragment();
        fragmentTransaction.replace(R.id.main_content, f);
        fragmentTransaction.commit();
    }

    private User readOrAddUserInfo() {
        UserDao userDao = MainApplication.getDaoSession().getUserDao();

        Query<User> userQuery = userDao.queryBuilder().build();

        ArrayList<User> userArrayList = (ArrayList<User>) userQuery.list();
        if (userArrayList.size() == 0) {
            User user = new User();
            user.setCreateDate(new Date(System.currentTimeMillis()));
            userDao.insert(user);
        }else{
            Log.e("User_Info",userArrayList.get(0).toString());
        }

        return userArrayList.get(0);
    }

    @Override
    protected void onStart() {
        super.onStart();

//        datebaseHelper.readUserInfo();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        datebaseHelper.readUserInfo();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.setting:
                Intent intent = new Intent("com.wqzhang.thingswapper.activity.AddToDoThingActivity");
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
