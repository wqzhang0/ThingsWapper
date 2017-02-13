package com.wqzhang.thingswapper.activitys;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.wqzhang.thingswapper.R;
import com.wqzhang.thingswapper.dao.AddThingOperationXMLData;
import com.wqzhang.thingswapper.dao.AddThingOperationXMLDataCache;
import com.wqzhang.thingswapper.dao.BusinessProcess;
import com.wqzhang.thingswapper.dao.SharedPreferencesControl;
import com.wqzhang.thingswapper.events.ChangeAddThingSubmitStateEvent;
import com.wqzhang.thingswapper.events.DataCacheChange;
import com.wqzhang.thingswapper.events.SaveChooseOperationEvent;
import com.wqzhang.thingswapper.events.ShowMoreSetEvent;
import com.wqzhang.thingswapper.exceptions.CustomerException;
import com.wqzhang.thingswapper.tools.Common;
import com.wqzhang.thingswapper.tools.DateUtil;
import com.wqzhang.thingswapper.vus.AddThingVu;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by wqzhang on 16-12-21.
 */

public class AddToDoThingActivity extends BasePartenerAppCompatActivity<AddThingVu> implements View.OnClickListener {


    SharedPreferencesControl sharedPreferencesControl;

    EventBus bus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bus = EventBus.getDefault();

        bus.register(this);
        try {
            sharedPreferencesControl = SharedPreferencesControl.getInstanll();
        } catch (CustomerException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onBind() {
//        setSupportActionBar(vu.getToolBar());


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
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }

        return onTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                //使EditText触发一次失去焦点事件
                v.setFocusable(false);
//                v.setFocusable(true); //这里不需要是因为下面一句代码会同时实现这个功能
                v.setFocusableInTouchMode(true);
                return true;
            }
        }
        return false;
    }

    @Override
    public void onClick(View view) {
        //取消类指令   可以直接发送到Adapter 或者各个位置直接进行处理,
        //保存或者更改类的指令   统一发送到AddThingOperationXMLData 里面进行处理, 然后在里面抛出 UI事件的处理指令
        switch (view.getId()) {
            case R.id.add_cancel:
//                bus.post(new SaveChooseOperationEvent(SaveChooseOperationEvent.TYPE_SAVE_CONTEXT, ((EditText) findViewById(R.id.remind_content)).getText().toString()));
                Intent intent = new Intent("com.wqzhang.thingswapper.activity.MainActivity");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            case R.id.add_submit:
                //如果提醒时间里存在已经过去的时间,则提醒并且忽悠此次提交操作
                ArrayList<Date> dates = AddThingOperationXMLDataCache.getDates();
                for (Date _data : dates) {
                    if (_data.getTime() <= DateUtil.getCurrentDate().getTime()) {
                        Toast.makeText(this, "提醒时间内存在过期的时间,请检验", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
//                bus.post(new SaveChooseOperationEvent(SaveChooseOperationEvent.TYPE_SAVE_CONTEXT, ((EditText) findViewById(R.id.remind_content)).getText().toString()));
                //先去除 内容的前后空格  然后判断是否为空   不为空则添加事件
                if (TextUtils.isEmpty(AddThingOperationXMLDataCache.getContent().trim())) {
                    Toast.makeText(this, "内容为空,添加失败", Toast.LENGTH_SHORT).show();
                } else if (AddThingOperationXMLDataCache.getNotifyType() != Common.REMINDER_TYPE_NONE && !AddThingOperationXMLData.getInstall().isReminder()) {
                    //设置了提醒内容但是没有提醒时间
                    Toast.makeText(this, "请设置提醒时间!或取消提醒类型", Toast.LENGTH_SHORT).show();
                } else if (AddThingOperationXMLDataCache.getNotifyType() != Common.REMINDER_TYPE_NONE && AddThingOperationXMLData.getInstall().isReminder() && AddThingOperationXMLDataCache.getDates().size() <= 0) {
                    Toast.makeText(this, "请设置提醒时间!或取消提醒类型", Toast.LENGTH_SHORT).show();
                } else if (AddThingOperationXMLDataCache.getNotifyType() == Common.REMINDER_TYPE_NONE && AddThingOperationXMLData.getInstall().isReminder() && AddThingOperationXMLDataCache.getDates().size() > 0) {
                    Toast.makeText(this, "请选择提醒类型", Toast.LENGTH_SHORT).show();
                } else {
                    BusinessProcess.getInstance().addToDoThing(AddThingOperationXMLData.getInstall().getToDothing(),
                            AddThingOperationXMLData.getInstall().getNotifycation());

                    AddThingOperationXMLData.getInstall().clearHistory();
                    Intent mianIntent = new Intent("com.wqzhang.thingswapper.activity.MainActivity");
                    mianIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(mianIntent);
                }

                break;
            case R.id.add_thing_more_choice_frame:
                bus.post(new ShowMoreSetEvent(ShowMoreSetEvent.HIDE));
                //发送  已取消的事件   具体操作在 AddToDoThingRecyclerAdapter 里面 做收缩的动画效果
                bus.post(new DataCacheChange(DataCacheChange.TYPE_CANCEL));
                break;
            case R.id.time_choose_cancel:
                bus.post(new SaveChooseOperationEvent(SaveChooseOperationEvent.TYPE_SAVE_NOTYFLY_DATE, new Date(), false));
                bus.post(new ShowMoreSetEvent(ShowMoreSetEvent.HIDE));
                //将xml里存储的 是否提醒字段(IS_REMINDER) 值更改为false
                bus.post(new DataCacheChange(DataCacheChange.TYPE_NOTIFLY_DATE_CANCEL));
                break;
            case R.id.time_choose_submit:

                Date date = vu.getNotifyDate();


                boolean alreadyExists = false;
                ArrayList<Date> dateArrayList = AddThingOperationXMLDataCache.getDates();
                for (Date _tmpDate : dateArrayList) {
                    if (_tmpDate.equals(date)) {
                        alreadyExists = true;
                    }
                }
                if (alreadyExists) {
                    Toast.makeText(this, "提醒时间已存在", Toast.LENGTH_SHORT).show();
                } else if (DateUtil.getCurrentDate().getTime() > date.getTime()) {
                    Toast.makeText(this, "不能添加以前的时间", Toast.LENGTH_SHORT).show();
                    bus.post(new SaveChooseOperationEvent(SaveChooseOperationEvent.TYPE_SAVE_NOTYFLY_DATE, new Date(), false));
                    //将xml里存储的 是否提醒字段(IS_REMINDER) 值更改为false
                    bus.post(new DataCacheChange(DataCacheChange.TYPE_NOTIFLY_DATE_CANCEL));

                } else {
                    //将xml里存储的 是否提醒字段(IS_REMINDER) 值更改为true
                    bus.post(new SaveChooseOperationEvent(SaveChooseOperationEvent.TYPE_IS_REMINDER, true));
                    bus.post(new SaveChooseOperationEvent(SaveChooseOperationEvent.TYPE_SAVE_NOTYFLY_DATE, date, true));
                }
                bus.post(new ShowMoreSetEvent(ShowMoreSetEvent.HIDE));
                break;
            default:
                break;
        }
    }

    @Subscribe
    public void clickAble(ChangeAddThingSubmitStateEvent changeAddThingSubmitStateEvent) {
        if (changeAddThingSubmitStateEvent.getType() == ChangeAddThingSubmitStateEvent.TYPE_CAN_CLICK) {
            vu.getAddSubmit().setClickable(true);
            vu.getAddSubmit().setTextColor(Color.parseColor("#6AB344"));

        }
        if (changeAddThingSubmitStateEvent.getType() == ChangeAddThingSubmitStateEvent.TYPE_NOT_CLICK) {
            vu.getAddSubmit().setClickable(false);
            vu.getAddSubmit().setTextColor(Color.parseColor("#707070"));
        }
    }

    @Subscribe
    public void showMoreSet(ShowMoreSetEvent showMoreSetEvent) {
        int showType = showMoreSetEvent.getShowType();
        Animation animation;
        switch (showType) {
            case ShowMoreSetEvent.HIDE:
                animation = AnimationUtils.loadAnimation(this, R.anim.abc_shrink_fade_out_from_bottom);

                vu.getReminderSettingLayout().startAnimation(animation);
                vu.getReminderSettingLayout().setVisibility(View.GONE);
                break;
            case ShowMoreSetEvent.SHOW_DATE:
                vu.getReminderCountChoicesListView().setVisibility(View.GONE);
                vu.getReminderDateChoicesLinearLayout().setVisibility(View.VISIBLE);
                animation = AnimationUtils.loadAnimation(this, R.anim.abc_grow_fade_in_from_bottom);

                vu.getReminderSettingLayout().setVisibility(View.VISIBLE);
                vu.getReminderSettingLayout().startAnimation(animation);
                break;
            case ShowMoreSetEvent.SHOW_COUNT:
                vu.getReminderDateChoicesLinearLayout().setVisibility(View.GONE);
                vu.getReminderCountChoicesListView().setVisibility(View.VISIBLE);
                vu.getReminderSettingLayout().setVisibility(View.VISIBLE);

                animation = AnimationUtils.loadAnimation(this, R.anim.abc_grow_fade_in_from_bottom);
                vu.getReminderSettingLayout().startAnimation(animation);
                break;
            default:
                break;
        }
    }

    @Subscribe
    public void save(DataCacheChange dataCacheChange) {
        vu.save(dataCacheChange);
    }

    @Override
    protected void beforeOnStop() {
        bus.unregister(this);
        super.beforeOnStop();
    }

    @Override
    protected void beforeDestroy() {
        super.beforeDestroy();
        bus.unregister(this);
    }
}
