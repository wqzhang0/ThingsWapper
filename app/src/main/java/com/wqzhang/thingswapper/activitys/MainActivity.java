package com.wqzhang.thingswapper.activitys;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.view.View;

import com.wqzhang.thingswapper.R;
import com.wqzhang.thingswapper.dao.BusinessProcess;
import com.wqzhang.thingswapper.fragments.PoolFragment;
import com.wqzhang.thingswapper.fragments.ShowThingsFragment;
import com.wqzhang.thingswapper.vus.MainVu;


public class MainActivity extends BasePartenerAppCompatActivity<MainVu> implements View.OnClickListener {

    private String TAG = "MainActivity";


    @Override
    protected void onBind() {
        super.onBind();


        setSupportActionBar(vu.getToolBar());
        fragmentManager.beginTransaction().replace(vu.getContainerId(), ShowThingsFragment.newInstance()).commit();
//        fragmentManager.beginTransaction().replace(vu.getContainerId(), PoolFragment.newInstance()).commit();
        vu.getPoolTextView().setOnClickListener(this);
        vu.getSettingTextView().setOnClickListener(this);
        vu.getShowThingsTextView().setOnClickListener(this);
    }

    @Override
    protected Class<MainVu> getVuClass() {
        return MainVu.class;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.setting:
                Intent intent = new Intent("com.wqzhang.thingswapper.activity.AddToDoThingActivity");
                startActivity(intent);
                break;
            case R.id.pool:
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Fragment f = new PoolFragment();
                fragmentTransaction.replace(R.id.main_content, f);
                fragmentTransaction.commit();
                break;
            case R.id.show_things:
                fragmentManager.beginTransaction().replace(vu.getContainerId(), ShowThingsFragment.newInstance()).commit();
                break;
            default:
                break;
        }

    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}
