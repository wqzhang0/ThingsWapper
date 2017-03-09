package com.wqzhang.thingswapper.fragment;

import android.content.Intent;
import android.view.View;

import com.wqzhang.thingswapper.activity.RegisterUserActivity;
import com.wqzhang.thingswapper.util.net.okhttp.OkHttpNetManager;
import com.wqzhang.thingswapper.util.net.retrofit.RetrofitUtil;
import com.wqzhang.thingswapper.vu.PersonSetVu;

/**
 * Created by wqzhang on 16-12-29.
 */

public class PersonSetFragment extends BasePartenerFragment<PersonSetVu> {


    @Override
    protected Class<PersonSetVu> getVuClass() {
        return PersonSetVu.class;
    }


    public static PersonSetFragment newInstance() {
        return new PersonSetFragment();
    }

    @Override
    protected void onBind() {
        super.onBind();
        vu.getRecyclerView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                OkHttpNetManager netManager = new OkHttpNetManager();
//                netManager.test();
//                RetrofitUtil.test();
                Intent intent = new Intent();
                intent.setAction("com.wqzhang.thingswapper.activity.RegisterUserActivity");
                startActivity(intent);
            }
        });
    }
}
