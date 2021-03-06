package com.wqzhang.thingswapper.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wqzhang.thingswapper.listener.AcitivityLinkCallBackListener;
import com.wqzhang.thingswapper.vu.Vu;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by wqzhang on 17-1-5.
 */

public abstract class BasePartenerFragment<V extends Vu> extends Fragment implements AcitivityLinkCallBackListener {

    protected V vu;
    protected EventBus bus;
    protected AcitivityLinkCallBackListener acitivityLinkCallBackListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bus = EventBus.getDefault();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = null;
        try {
            vu = getVuClass().newInstance();
            vu.init(inflater, container);
            onBind();
            view = vu.getView();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return view;
    }

    protected abstract Class<V> getVuClass();

    protected void onBind() {
    }

    @Override
    public void onStop() {
        beforeOnStop();
        super.onStop();
    }

    protected void beforeOnStop() {

    }


    @Override
    public void onResume() {
        super.onResume();
        afterOnResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        afterOnStart();
    }

    protected void afterOnStart() {

    }

    protected void afterOnResume() {
    }

    @Override
    public void onDestroy() {
        beforOnDestory();
        vu = null;
        super.onDestroy();
    }

    protected void beforOnDestory() {
    }

    public void setCallBack(AcitivityLinkCallBackListener acitivityLinkCallBackListener) {
        this.acitivityLinkCallBackListener = acitivityLinkCallBackListener;
    }
}
