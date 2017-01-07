package com.wqzhang.thingswapper.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.wqzhang.thingswapper.R;
import com.wqzhang.thingswapper.dao.AddThingOperationXMLData;
import com.wqzhang.thingswapper.dao.SharedPreferencesControl;
import com.wqzhang.thingswapper.events.SaveChooseOperationEvent;
import com.wqzhang.thingswapper.events.ShowMoreSetEvent;
import com.wqzhang.thingswapper.exceptions.CustomerException;
import com.wqzhang.thingswapper.tools.DateUtil;
import com.wqzhang.thingswapper.vus.AddThingVu;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Date;

/**
 * Created by wqzhang on 16-12-21.
 */

public class AddToDoThingActivity extends BasePartenerAppCompatActivity<AddThingVu> implements View.OnClickListener {


    //新设置的时间
    private int newHourValue = 8;
    private int newMinuteValue = 0;

    SharedPreferencesControl sharedPreferencesControl;

    EventBus bus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(vu.getView());
        bus = EventBus.getDefault();

        bus.register(this);
        try {
            sharedPreferencesControl = SharedPreferencesControl.getInstanll();
            vu.getAddToDoThingRecyclerAdapter().setDataAndFlushView(AddThingOperationXMLData.getInstall().readHistoryData());
        } catch (CustomerException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onBind() {
        setSupportActionBar(vu.getToolBar());

        vu.getAddCancel().setOnClickListener(this);
        vu.getAddSubmit().setOnClickListener(this);
        vu.getView().setOnClickListener(this);
        vu.getTimeChooseCancel().setOnClickListener(this);
        vu.getTimeChooseSubmit().setOnClickListener(this);
        vu.getReminderSettingLayout().setOnClickListener(this);

    }


    @Override
    protected Class<AddThingVu> getVuClass() {
        return AddThingVu.class;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_cancel:
                Intent intent = new Intent("com.wqzhang.thingswapper.activity.MainActivity");
                startActivity(intent);
                break;
            case R.id.add_submit:
                AddThingOperationXMLData.getInstall().clearHistory();

                Intent intent2 = new Intent("com.wqzhang.thingswapper.activity.MainActivity");
                startActivity(intent2);
                break;
            case R.id.setting_layout:
                bus.post(new ShowMoreSetEvent(ShowMoreSetEvent.HIDE));
                bus.post(new SaveChooseOperationEvent(SaveChooseOperationEvent.TYPE_SAVE_NOTYFLY_DATE, new Date(), false));
                bus.post(new SaveChooseOperationEvent(SaveChooseOperationEvent.TYPE_SAVE_NOTYFLY_COUNTS, new Date(), false));
//                addToDoThingRecyclerAdapter.saveOperationData(false, null);
                break;
            case R.id.time_choose_cancel:
//                addToDoThingRecyclerAdapter.saveOperationData(false, null);
                bus.post(new SaveChooseOperationEvent(SaveChooseOperationEvent.TYPE_SAVE_NOTYFLY_DATE, new Date(), false));
                bus.post(new ShowMoreSetEvent(ShowMoreSetEvent.HIDE));
                break;
            case R.id.time_choose_submit:
                Date date = DateUtil.parseDate("2016年12月30日 " + newHourValue + " " + newMinuteValue, DateUtil.CHOICE_PATTERN);
//                addToDoThingRecyclerAdapter.saveOperationData(true, date);

                bus.post(new SaveChooseOperationEvent(SaveChooseOperationEvent.TYPE_SAVE_NOTYFLY_DATE, date, true));
                bus.post(new ShowMoreSetEvent(ShowMoreSetEvent.HIDE));
                break;
            default:
                break;
        }
    }

    @Subscribe
    public void showMoreSet(ShowMoreSetEvent showMoreSetEvent) {
        int showType = showMoreSetEvent.getShowType();
        switch (showType) {
            case ShowMoreSetEvent.HIDE:
                vu.getReminderSettingLayout().setVisibility(View.GONE);
                break;
            case ShowMoreSetEvent.SHOW_DATE:
                vu.getReminderCountChoicesListView().setVisibility(View.GONE);
                vu.getReminderDateChoicesLinearLayout().setVisibility(View.VISIBLE);
                vu.getReminderSettingLayout().setVisibility(View.VISIBLE);
                break;
            case ShowMoreSetEvent.SHOW_COUNT:
                vu.getReminderDateChoicesLinearLayout().setVisibility(View.GONE);
                vu.getReminderCountChoicesListView().setVisibility(View.VISIBLE);
                vu.getReminderSettingLayout().setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }

}
