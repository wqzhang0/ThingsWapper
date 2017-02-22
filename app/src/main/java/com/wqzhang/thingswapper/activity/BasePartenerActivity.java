package com.wqzhang.thingswapper.activity;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.wqzhang.thingswapper.vu.Vu;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by wqzhang on 17-1-5.
 */

public abstract class BasePartenerActivity<V extends Vu> extends AppCompatActivity {
    protected V vu;
    protected FragmentManager fragmentManager;
    protected EventBus bus;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = getFragmentManager();
        bus = EventBus.getDefault();
        try {
            vu = getVuClass().newInstance();
            vu.init(getLayoutInflater(), null);
            setContentView(vu.getView());
            onBind();

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


    }

    @Override
    protected void onStop() {
        beforeOnStop();
        super.onStop();
    }

    protected void beforeOnStop() {

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        beforeDestroy();
        onDestroyVu();
        vu = null;
        super.onDestroy();
    }

    @Override
    public final void onBackPressed() {
        if (!handleBackPressed()) {
            super.onBackPressed();
        }
    }

    public boolean handleBackPressed() {
        return false;
    }


    protected abstract Class<V> getVuClass();

    protected void onBind() {
    }

    protected void beforeDestroy() {
    }


    protected void onDestroyVu() {
    }


}
