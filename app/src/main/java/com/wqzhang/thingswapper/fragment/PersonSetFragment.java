package com.wqzhang.thingswapper.fragment;

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
    }
}
