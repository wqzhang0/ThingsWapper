package com.wqzhang.thingswapper.vus;

import android.content.res.Resources;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.wqzhang.thingswapper.R;
import com.wqzhang.thingswapper.adapters.AddToDoThingRecyclerAdapter;
import com.wqzhang.thingswapper.adapters.RemindCountAdapter;
import com.wqzhang.thingswapper.events.DataCacheChange;
import com.wqzhang.thingswapper.tools.DateUtil;
import com.wqzhang.thingswapper.ui.wheelView.LoopView;
import com.wqzhang.thingswapper.ui.wheelView.OnItemSelectedListener;
import com.wqzhang.thingswapper.ui.wheelView.OnScrollEndListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wqzhang on 17-1-5.
 */

public class AddThingVu implements Vu {
    protected View view;
    TextView addCancel, addSubmit;
    EditText reminderContent;
    TextView timeChooseCancel, timeChooseSubmit;

    FrameLayout reminderSettingLayout;
    ListView reminderCountChoicesListView;
    LoopView reminderDateCalLoopView;
    LoopView reminderDateHourLoopView;
    LoopView reminderDateMinuteLoopView;
    LinearLayout reminderDateChoicesLinearLayout;

    AddToDoThingRecyclerAdapter addToDoThingRecyclerAdapter;

    Toolbar toolbar;

    int newHourValue, newMinuteValue;
    String newYMDValue;

    @Override
    public void init(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.add_reminder_main_layout, container, false);


        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        addCancel = (TextView) view.findViewById(R.id.add_cancel);
        addSubmit = (TextView) view.findViewById(R.id.add_submit);
        reminderSettingLayout = (FrameLayout) view.findViewById(R.id.add_thing_more_choice_frame);
        reminderCountChoicesListView = (ListView) view.findViewById(R.id.count_choices);
        reminderDateCalLoopView = (LoopView) view.findViewById(R.id.cal_loop_view);
        reminderDateHourLoopView = (LoopView) view.findViewById(R.id.hour_loop_view);
        reminderDateMinuteLoopView = (LoopView) view.findViewById(R.id.minute_loop_view);
        reminderDateChoicesLinearLayout = (LinearLayout) view.findViewById(R.id.date_choices);

        timeChooseCancel = (TextView) view.findViewById(R.id.time_choose_cancel);
        timeChooseSubmit = (TextView) view.findViewById(R.id.time_choose_submit);

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
        int _hours_position = DateUtil.getHour(new Date());
        reminderDateHourLoopView.setInitPosition(_hours_position);

        reminderDateHourLoopView.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                newHourValue = Integer.valueOf(hoursList.get(index));
            }
        });
        newHourValue = Integer.valueOf(hoursList.get(8));


        final ArrayList<String> minuteList = new ArrayList<>();
        int _currentMinute = DateUtil.getMinute(new Date());
        int _minuteListPosition = 0;
        for (int i = 0; i < 60; i += 5) {
            if (i < 10) {
                minuteList.add("0" + String.valueOf(i));
            } else {
                minuteList.add(String.valueOf(i));
            }
            if (_currentMinute > i) {
                _minuteListPosition = minuteList.size() - 1;
            }
        }
        if (_minuteListPosition < minuteList.size() - 1) {
            _minuteListPosition = _minuteListPosition + 1;
        } else if (_minuteListPosition == minuteList.size() - 1) {
            _minuteListPosition = 0;
        }
        reminderDateMinuteLoopView.setItems(minuteList);
        reminderDateMinuteLoopView.setTextSize(15);


        reminderDateMinuteLoopView.setInitPosition(_minuteListPosition);
        reminderDateMinuteLoopView.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                Log.d("LoopView", minuteList.get(index) + "is selected");
                newMinuteValue = Integer.valueOf(minuteList.get(index));
            }
        });
//        reminderDateCalLoopView.setNotLoop();
        newMinuteValue = Integer.valueOf(minuteList.get(0));


        final ArrayList<String> calList = new ArrayList<>();
        Date todayDate = new Date();
        for (int i = 0; i <= 30; i++) {
            Date _tmpDate = DateUtil.addDate(todayDate, i);
            String _total = DateUtil.formatDateByFormat(_tmpDate, DateUtil.LOOPVIEW_PATTERN) + " " + DateUtil.getWeekChina(_tmpDate);
            calList.add(_total);
        }

        reminderDateCalLoopView.setNotLoop();
        reminderDateCalLoopView.setAdd();
        reminderDateCalLoopView.setItems(calList);
        reminderDateCalLoopView.setTextSize(15);
        reminderDateCalLoopView.setInitPosition(0);
        reminderDateCalLoopView.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                newYMDValue = calList.get(index).split(" ")[0];
            }
        });
        reminderDateCalLoopView.setOnScrollEndListener(new OnScrollEndListener() {
            @Override
            public List<String> getMoreList(List<String> historyData) {
                List<String> newDates = new ArrayList<String>();
                String lastData = historyData.get(historyData.size() - 1);
                Date lastDate = DateUtil.parseDate(lastData, DateUtil.LOOPVIEW_PATTERN);
                for (int i = 0; i < 30; i++) {
                    Date _tmpDate = DateUtil.addDate(lastDate, i);
                    String _total = DateUtil.formatDateByFormat(_tmpDate, DateUtil.LOOPVIEW_PATTERN) + " " + DateUtil.getWeekChina(_tmpDate);
                    newDates.add(_total);
                }
                return newDates;
            }
        });
        newYMDValue = calList.get(0).split(" ")[0];

    }

    @Override
    public View getView() {
        return view;
    }

    public Toolbar getToolBar() {
        return toolbar;
    }


    public TextView getTimeChooseCancel() {
        return timeChooseCancel;
    }

    public TextView getTimeChooseSubmit() {
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

    public EditText getReminderContent() {
        return reminderContent;
    }

    public void save(DataCacheChange dataCacheChange) {
        addToDoThingRecyclerAdapter.save(dataCacheChange);
    }

    public Date getNotifyDate() {
        String dataChooseResult = newYMDValue + " " + newHourValue + " " + newMinuteValue;
        Date date = DateUtil.parseDate(dataChooseResult, DateUtil.CHOICE_PATTERN);
        return date;
    }
}
