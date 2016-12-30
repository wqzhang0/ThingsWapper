package com.wqzhang.thingswapper.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;

import com.wqzhang.thingswapper.R;
import com.wqzhang.thingswapper.adapter.AddToDoThingRecyclerAdapter;
import com.wqzhang.thingswapper.adapter.RemindCountAdapter;
import com.wqzhang.thingswapper.dao.BusinessProcess;
import com.wqzhang.thingswapper.dao.greendao.ToDoThing;
import com.wqzhang.thingswapper.listener.impl.ShowMoreSet;
import com.wqzhang.thingswapper.tools.Common;
import com.wqzhang.thingswapper.tools.DateUtil;
import com.wqzhang.thingswapper.ui.wheelView.LoopView;
import com.wqzhang.thingswapper.ui.wheelView.OnItemSelectedListener;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by wqzhang on 16-12-21.
 */

public class AddToDoThingActivity extends Activity implements View.OnClickListener, ShowMoreSet {
    EditText content;
    Switch reminderCountSwitch, reminderDateSwitch;
    Button submitBtn, dateCancelBtn, dateSubmitBtn;
    FrameLayout reminderSettingLayout;
    ListView reminderCountChoicesListView;
    LoopView reminderDateCalLoopView;
    LoopView reminderDateHourLoopView;
    LoopView reminderDateMinuteLoopView;
    LinearLayout reminderDateChoicesLinearLayout;
    CheckBox alarmCheckbox, emailCheckbox, verticalCheckbox;
    AddToDoThingRecyclerAdapter addToDoThingRecyclerAdapter;

    //新设置的时间
    private int newHourValue = 8;
    private int newMinuteValue = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_to_do_thing_main);
//        content = (EditText) findViewById(R.id.content);
//        submitBtn = (Button) findViewById(R.id.submit);

        reminderSettingLayout = (FrameLayout) findViewById(R.id.setting_frame_layout);
        reminderCountChoicesListView = (ListView) findViewById(R.id.count_choices_list_view);
        reminderDateCalLoopView = (LoopView) findViewById(R.id.cal_loop_view);
        reminderDateHourLoopView = (LoopView) findViewById(R.id.hour_loop_view);
        reminderDateMinuteLoopView = (LoopView) findViewById(R.id.minute_loop_view);
        reminderDateChoicesLinearLayout = (LinearLayout) findViewById(R.id.date_choices_layout);

        dateCancelBtn = (Button) findViewById(R.id.date_cancel_btn);
        dateSubmitBtn = (Button) findViewById(R.id.date_submit_btn);
//        alarmCheckbox = (CheckBox) findViewById(R.id.alarm_checkbox);
//        emailCheckbox = (CheckBox) findViewById(R.id.email_checkbox);
//        verticalCheckbox = (CheckBox) findViewById(R.id.vertical_checkbox);


//        submitBtn.setOnClickListener(this);
//        reminderCountSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
//                if (isChecked) {
////                    reminderDateChoicesLinearLayout.setVisibility(View.GONE);
//                    showSettingFrameLayout(ActionType.SHOW_COUNT);
//                } else {
//                    showSettingFrameLayout(ActionType.HIDE);
//                }
//
//            }
//        });
//
//        reminderDateChoicesLinearLayout.setOnClickListener(this);
        dateCancelBtn.setOnClickListener(this);
        dateSubmitBtn.setOnClickListener(this);
        reminderSettingLayout.setOnClickListener(this);
//

        init();

        RecyclerView addToDoThingRecyclerView = (RecyclerView) findViewById(R.id.add_thing_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayout.VERTICAL);
        addToDoThingRecyclerView.setLayoutManager(linearLayoutManager);
        addToDoThingRecyclerAdapter = new AddToDoThingRecyclerAdapter(this);
        addToDoThingRecyclerView.setAdapter(addToDoThingRecyclerAdapter);
        addToDoThingRecyclerAdapter.setShowMoreSet(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.submit:
                String remindContent = content.getText().toString();
//                ArrayList<ToDoThingModel> a = DatebaseHelper.getInstance().readToBeDoneThings();
                if (!TextUtils.isEmpty(remindContent)) {
                    int reminderType = Common.REMINDER_TYPE_NONE;

                    if (verticalCheckbox.isChecked()) {
                        reminderType += Common.REMINDER_TYPE_VERTICAL;
                    }
                    if (alarmCheckbox.isChecked()) {
                        reminderType += Common.REMINDER_TYPE_ALARM;
                    }
                    if (emailCheckbox.isChecked()) {
                        reminderType += Common.REMINDER_TYPE_EMAIL;
                    }

                    ToDoThing toDoThing = new ToDoThing();
                    toDoThing.setReminderContext(remindContent);
                    toDoThing.setReminderType(reminderType);
                    BusinessProcess.getInstance().addToDoThing(toDoThing);

                }
                Intent intent = new Intent("com.wqzhang.thingswapper.activity.MainActivity");
                startActivity(intent);
                break;

            case R.id.reminder_date_switch:
                break;
            case R.id.reminder_count_switch:
//                Calendar calendar = Calendar.getInstance();
//                Dialog dialog = new DatePickerDialog(AddToDoThingActivity.this, new DatePickerDialog.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
//
//                        Log.d("year", "|" + year);
//                        Log.d("month", "|" + month);
//                        Log.d("day", "|" + dayOfMonth);
//
//                    }
//                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
//                dialog.show();

                break;
            case R.id.setting_frame_layout:
                showMoreSetFrameLayout(ShowType.HIDE);
                reminderSettingLayout.setVisibility(View.GONE);
                break;
            case R.id.date_cancel_btn:
                addToDoThingRecyclerAdapter.saveOperationData(false, null);
                showMoreSetFrameLayout(ShowType.HIDE);
                break;
            case R.id.date_submit_btn:

                Date date = DateUtil.parseDate("2016年12月30日 "+newHourValue+" "+newMinuteValue, DateUtil.CHOICE_PATTERN);
                addToDoThingRecyclerAdapter.saveOperationData(true, date);
                showMoreSetFrameLayout(ShowType.HIDE);
                break;
            default:
                break;
        }
    }

    @Override
    public void showMoreSetFrameLayout(ShowType showType) {
        switch (showType) {
            case HIDE:
                reminderSettingLayout.setVisibility(View.GONE);
                break;
            case SHOW_DATE:
                reminderCountChoicesListView.setVisibility(View.GONE);
                reminderDateChoicesLinearLayout.setVisibility(View.VISIBLE);
                reminderSettingLayout.setVisibility(View.VISIBLE);
                break;
            case SHOW_COUNT:
                reminderDateChoicesLinearLayout.setVisibility(View.GONE);
                reminderCountChoicesListView.setVisibility(View.VISIBLE);
                reminderSettingLayout.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }


    public void init() {
        Resources resources = this.getResources();
        String[] tmpStringNum = resources.getStringArray(R.array.reminder_count_choices);
        ArrayList<String> tmpList = new ArrayList<>();
        for (String a : tmpStringNum) {
            tmpList.add(a);
        }
        RemindCountAdapter remindCountAdapter = new RemindCountAdapter(this);
        remindCountAdapter.setChoicesData(tmpList);
        reminderCountChoicesListView.setAdapter(remindCountAdapter);

        final ArrayList<String> hoursList = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            if (i < 10) {
                hoursList.add("0" + String.valueOf(i));
            } else {
                hoursList.add(String.valueOf(i));

            }
        }
        reminderDateHourLoopView.setItems(hoursList);
        reminderDateHourLoopView.setTextSize(15);
        reminderDateHourLoopView.setInitPosition(8);
        reminderDateHourLoopView.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                newHourValue = Integer.valueOf(hoursList.get(index));
            }
        });


        final ArrayList<String> minuteList = new ArrayList<>();
        for (int i = 0; i < 60; i++) {
            if (i < 10) {
                minuteList.add("0" + String.valueOf(i));
            } else {
                minuteList.add(String.valueOf(i));

            }
        }
        reminderDateMinuteLoopView.setItems(minuteList);
        reminderDateMinuteLoopView.setTextSize(15);

        reminderDateMinuteLoopView.setInitPosition(0);
        reminderDateMinuteLoopView.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                Log.d("LoopView", minuteList.get(index) + "is selected");
                newMinuteValue = Integer.valueOf(minuteList.get(index));
            }
        });


        final ArrayList<String> calList = new ArrayList<>();
        String tmpString = "2016年12月";
        for (int i = 2; i < 15; i++) {
            calList.add(tmpString + i + "日");
        }
        reminderDateCalLoopView.setItems(calList);
        reminderDateMinuteLoopView.setTextSize(15);
        reminderDateMinuteLoopView.setInitPosition(0);
    }
}
