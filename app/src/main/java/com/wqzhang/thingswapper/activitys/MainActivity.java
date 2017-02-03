package com.wqzhang.thingswapper.activitys;

import android.app.Fragment;
import android.view.View;

import com.wqzhang.thingswapper.R;
import com.wqzhang.thingswapper.fragments.PersonSetFragment;
import com.wqzhang.thingswapper.fragments.PoolFragment;
import com.wqzhang.thingswapper.fragments.ShowThingsFragment;
import com.wqzhang.thingswapper.vus.MainVu;


public class MainActivity extends BasePartenerAppCompatActivity<MainVu> implements View.OnClickListener {

    private String TAG = "MainActivity";

    private Fragment showThingsFragment, poolFragment, personSetFragment;

    @Override
    protected void onBind() {
        super.onBind();


        showThingsFragment = ShowThingsFragment.newInstance();
        poolFragment = PoolFragment.newInstance();
        personSetFragment = PersonSetFragment.newInstance();

        vu.getNavigationCharts().setOnClickListener(this);
        vu.getNavigationSetting().setOnClickListener(this);
        vu.getNavigationShowThings().setOnClickListener(this);

        vu.initFragment(showThingsFragment, fragmentManager);
    }

    @Override
    protected Class<MainVu> getVuClass() {
        return MainVu.class;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.show_things:
                vu.switchContent(showThingsFragment, fragmentManager);
                break;
            case R.id.pool:
                vu.switchContent(poolFragment, fragmentManager);
                break;
            case R.id.setting:
                vu.switchContent(personSetFragment, fragmentManager);
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
