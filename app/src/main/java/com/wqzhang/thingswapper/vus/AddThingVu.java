package com.wqzhang.thingswapper.vus;

import android.content.res.Resources;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.wqzhang.thingswapper.R;
import com.wqzhang.thingswapper.adapters.AddToDoThingRecyclerAdapter;
import com.wqzhang.thingswapper.adapters.RemindCountAdapter;
import com.wqzhang.thingswapper.ui.wheelView.LoopView;
import com.wqzhang.thingswapper.ui.wheelView.OnItemSelectedListener;

import java.util.ArrayList;

/**
 * Created by wqzhang on 17-1-5.
 */

public class AddThingVu implements Vu {
    protected View view;
    TextView addCancel, addSubmit;
    Button timeChooseCancel, timeChooseSubmit;

    FrameLayout reminderSettingLayout;
    ListView reminderCountChoicesListView;
    LoopView reminderDateCalLoopView;
    LoopView reminderDateHourLoopView;
    LoopView reminderDateMinuteLoopView;
    LinearLayout reminderDateChoicesLinearLayout;

    AddToDoThingRecyclerAdapter addToDoThingRecyclerAdapter;

    Toolbar toolbar;

    int newHourValue, newMinuteValue;

    @Override
    public void init(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.add_reminder_main_layout, container, false);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        addCancel = (TextView) view.findViewById(R.id.add_cancel);
        addSubmit = (TextView) view.findViewById(R.id.add_submit);
        reminderSettingLayout = (FrameLayout) view.findViewById(R.id.setting_layout);
        reminderCountChoicesListView = (ListView) view.findViewById(R.id.count_choices);
        reminderDateCalLoopView = (LoopView) view.findViewById(R.id.cal_loop_view);
        reminderDateHourLoopView = (LoopView) view.findViewById(R.id.hour_loop_view);
        reminderDateMinuteLoopView = (LoopView) view.findViewById(R.id.minute_loop_view);
        reminderDateChoicesLinearLayout = (LinearLayout) view.findViewById(R.id.date_choices);

        timeChooseCancel = (Button) view.findViewById(R.id.time_choose_cancel);
        timeChooseSubmit = (Button) view.findViewById(R.id.time_choose_submit);


//


        RecyclerView fillInformationLayout = (RecyclerView) view.findViewById(R.id.fill_information_layout);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(inflater.getContext());
        linearLayoutManager.setOrientation(LinearLayout.VERTICAL);
        fillInformationLayout.setLayoutManager(linearLayoutManager);

        addToDoThingRecyclerAdapter = new AddToDoThingRecyclerAdapter(inflater.getContext());
        fillInformationLayout.setAdapter(addToDoThingRecyclerAdapter);


        Resources resources = inflater.getContext().getResources();
        String[] tmpStringNum = resources.getStringArray(R.array.reminder_count_choices);
        ArrayList<String> tmpList = new ArrayList<>();
        for (String a : tmpStringNum) {
            tmpList.add(a);
        }
        RemindCountAdapter remindCountAdapter = new RemindCountAdapter(inflater.getContext());
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

    @Override
    public View getView() {
        return view;
    }

    public Toolbar getToolBar() {
        return toolbar;
    }


    public Button getTimeChooseCancel() {
        return timeChooseCancel;
    }

    public Button getTimeChooseSubmit() {
        return timeChooseSubmit;
    }

    public FrameLayout getReminderSettingLayout() {
        return reminderSettingLayout;
    }

    public ListView getReminderCountChoicesListView() {
        return reminderCountChoicesListView;
    }

    public LoopView getReminderDateCalLoopView() {
        return reminderDateCalLoopView;
    }

    public LoopView getReminderDateHourLoopView() {
        return reminderDateHourLoopView;
    }

    public LoopView getReminderDateMinuteLoopView() {
        return reminderDateMinuteLoopView;
    }

    public LinearLayout getReminderDateChoicesLinearLayout() {
        return reminderDateChoicesLinearLayout;
    }

    public AddToDoThingRecyclerAdapter getAddToDoThingRecyclerAdapter() {
        return addToDoThingRecyclerAdapter;
    }

    public TextView getAddSubmit() {
        return addSubmit;
    }

    public TextView getAddCancel() {
        return addCancel;
    }
}
