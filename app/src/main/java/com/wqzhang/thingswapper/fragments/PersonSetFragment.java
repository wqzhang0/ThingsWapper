package com.wqzhang.thingswapper.fragments;

import android.support.v7.widget.LinearLayoutManager;

import com.wqzhang.thingswapper.adapters.ChartsRecyclerAdapter;
import com.wqzhang.thingswapper.dao.BusinessProcess;
import com.wqzhang.thingswapper.vus.PersonSetVu;
import com.wqzhang.thingswapper.vus.PoolVu;

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
    }
}
