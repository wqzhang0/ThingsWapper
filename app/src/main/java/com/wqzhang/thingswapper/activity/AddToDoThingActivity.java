package com.wqzhang.thingswapper.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.wqzhang.thingswapper.R;
import com.wqzhang.thingswapper.adapter.RemindCountAdapter;
import com.wqzhang.thingswapper.db.DatebaseHelper;
import com.wqzhang.thingswapper.model.ToDoThing_model;
import com.wqzhang.thingswapper.tools.Common;
import com.wqzhang.thingswapper.ui.wheelView.LoopView;
import com.wqzhang.thingswapper.ui.wheelView.OnItemSelectedListener;

import java.util.ArrayList;

/**
 * Created by wqzhang on 16-12-21.
 */

public class AddToDoThingActivity extends Activity implements View.OnClickListener {
    EditText content;
    TextView reminderCountTextView, reminderDateTextView;
    Button submitBtn;
    FrameLayout reminderSettingLayout;
    ListView reminderCountChoicesListView;
    //    LoopView reminderDateAmOrPmLoopView;
    LoopView reminderDateHourLoopView;
    LoopView reminderDateMinuteLoopView;
    LinearLayout reminderDateChoicesLinearLayout;
    CheckBox alarmCheckbox, emailCheckbox, verticalCheckbox;

    //新设置的时间
    private int newHourValue, newMinuteValue;

    public enum ActionType {
        //隐藏,时间选择,提醒次数选择
        HIDE, SHOW_DATE, SHOW_COUNT
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_to_do_thing_main);
        content = (EditText) findViewById(R.id.content);
        submitBtn = (Button) findViewById(R.id.submit);
        reminderCountTextView = (TextView) findViewById(R.id.reminder_count);
        reminderDateTextView = (TextView) findViewById(R.id.reminder_date);
        reminderSettingLayout = (FrameLayout) findViewById(R.id.reminder_setting_layout);
        reminderCountChoicesListView = (ListView) findViewById(R.id.reminder_count_choices);
//        reminderDateAmOrPmLoopView = (LoopView) findViewById(R.id.reminder_date_am_pm_type);
        reminderDateHourLoopView = (LoopView) findViewById(R.id.reminder_date_hour);
        reminderDateMinuteLoopView = (LoopView) findViewById(R.id.reminder_date_minute);
        reminderDateChoicesLinearLayout = (LinearLayout) findViewById(R.id.reminder_date_choices);
        alarmCheckbox = (CheckBox) findViewById(R.id.alarm_checkbox);
        emailCheckbox = (CheckBox) findViewById(R.id.email_checkbox);
        verticalCheckbox = (CheckBox) findViewById(R.id.vertical_checkbox);

        submitBtn.setOnClickListener(this);
        reminderCountTextView.setOnClickListener(this);
        reminderDateTextView.setOnClickListener(this);
        reminderDateChoicesLinearLayout.setOnClickListener(this);
        reminderSettingLayout.setOnClickListener(this);
//        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        if (!imm.isActive()) {
//            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
//        }
//        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

        init();


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

                    ToDoThing_model toDoThing = new ToDoThing_model();
                    toDoThing.setReminderContext(remindContent);
                    toDoThing.setReminderType(reminderType);
                    DatebaseHelper.getInstance().addToDoThing(toDoThing);

                }
                Intent intent = new Intent("com.wqzhang.thingswapper.activity.MainActivity");
                startActivity(intent);
                break;

            case R.id.reminder_date:
                showSettingFrameLayout(ActionType.SHOW_DATE);
                break;
            case R.id.reminder_count:
                showSettingFrameLayout(ActionType.SHOW_COUNT);
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
            case R.id.reminder_setting_layout:
                showSettingFrameLayout(ActionType.HIDE);
                reminderSettingLayout.setVisibility(View.GONE);

            default:
                break;
        }
    }

    private void showSettingFrameLayout(ActionType actionType) {
        switch (actionType) {
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

//        final ArrayList<String> aOpList = new ArrayList<>();
//        aOpList.add("AM");
//        aOpList.add("PM");
//        reminderDateAmOrPmLoopView.setItems(aOpList);
//        reminderDateAmOrPmLoopView.setNotLoop();
//        reminderDateAmOrPmLoopView.setTextSize(20);
//        reminderDateAmOrPmLoopView.setListener(new OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(int index) {
//                Log.d("LoopView", aOpList.get(index) + "is selected");
//            }
//        });

        final ArrayList<String> hoursList = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            if (i < 10) {
                hoursList.add("0" + String.valueOf(i));
            } else {
                hoursList.add(String.valueOf(i));

            }
        }
        reminderDateHourLoopView.setItems(hoursList);
        reminderDateHourLoopView.setTextSize(20);
        reminderDateHourLoopView.setInitPosition(8);
        reminderDateHourLoopView.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                Log.d("LoopView", hoursList.get(index) + "is selected");
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
        reminderDateMinuteLoopView.setTextSize(20);

        reminderDateMinuteLoopView.setInitPosition(0);
        reminderDateMinuteLoopView.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                Log.d("LoopView", minuteList.get(index) + "is selected");
                newMinuteValue = Integer.valueOf(minuteList.get(index));
            }
        });
    }
}
