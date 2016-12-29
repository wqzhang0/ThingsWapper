package com.wqzhang.thingswapper.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.wqzhang.thingswapper.MainApplication;
import com.wqzhang.thingswapper.R;
import com.wqzhang.thingswapper.dao.BusinessProcess;
import com.wqzhang.thingswapper.dao.greendao.User;
import com.wqzhang.thingswapper.dao.greendao.UserDao;
import com.wqzhang.thingswapper.db.DatebaseHelper;
import com.wqzhang.thingswapper.fragment.PoolFragment;
import com.wqzhang.thingswapper.fragment.ToDoFragment;

import org.greenrobot.greendao.query.Query;

import java.util.ArrayList;
import java.util.Date;


public class MainActivity extends FragmentActivity implements View.OnClickListener {

    private String TAG = "MainActivity";
    private DatebaseHelper datebaseHelper = null;
    private TextView settingTextView, poolTextView;

    public Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.to_do_main);
//        datebaseHelper = DatebaseHelper.getInstance();
        settingTextView = (TextView) findViewById(R.id.setting);
        poolTextView = (TextView) findViewById(R.id.pool);
        settingTextView.setOnClickListener(this);
        poolTextView.setOnClickListener(this);

        setDefaultFragment();
        BusinessProcess.getInstance().readOrAddUserInfo();

    }

    private void setDefaultFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment toDoFragment = new ToDoFragment();
        fragmentTransaction.replace(R.id.main_content, toDoFragment);
        fragmentTransaction.commit();

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
            case R.id.pool:
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Fragment f = new PoolFragment();
                fragmentTransaction.replace(R.id.main_content, f);
                fragmentTransaction.commit();
            default:
                break;
        }
    }


}
