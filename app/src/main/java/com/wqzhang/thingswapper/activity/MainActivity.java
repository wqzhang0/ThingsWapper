package com.wqzhang.thingswapper.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.wqzhang.thingswapper.R;
import com.wqzhang.thingswapper.dao.BusinessProcess;
import com.wqzhang.thingswapper.db.DatebaseHelper;
import com.wqzhang.thingswapper.fragment.PoolFragment;
import com.wqzhang.thingswapper.fragment.ToDoFragment;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private String TAG = "MainActivity";
    private DatebaseHelper datebaseHelper = null;
    private TextView settingTextView, poolTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
