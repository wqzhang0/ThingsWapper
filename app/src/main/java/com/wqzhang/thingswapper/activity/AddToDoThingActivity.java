package com.wqzhang.thingswapper.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.wqzhang.thingswapper.R;
import com.wqzhang.thingswapper.adapter.RemindCalAdapter;
import com.wqzhang.thingswapper.db.DatebaseHelper;
import com.wqzhang.thingswapper.model.ToDoThingModel;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by wqzhang on 16-12-21.
 */

public class AddToDoThingActivity extends Activity implements View.OnClickListener {
    EditText content;
    TextView reminderCal, reminderDate;
    Button submit;
    FrameLayout reminderCalTottalLayout;
    ListView reminderCalChoices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_to_do_thing_main);
        content = (EditText) findViewById(R.id.content);
        submit = (Button) findViewById(R.id.submit);
        reminderCal = (TextView) findViewById(R.id.reminder_cal);
        reminderDate = (TextView) findViewById(R.id.reminder_date);
        reminderCalTottalLayout = (FrameLayout) findViewById(R.id.reminder_cal_tottal_layout);
        reminderCalChoices = (ListView) findViewById(R.id.reminder_cal_choices);


//        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        if (!imm.isActive()) {
//            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
//        }
//        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

        reminderDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reminderCalTottalLayout.setVisibility(View.VISIBLE);
            }
        });
        reminderCalTottalLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reminderCalTottalLayout.setVisibility(View.GONE);
            }
        });
        reminderCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                Dialog dialog = new DatePickerDialog(AddToDoThingActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {

                        Log.d("year", "|" + year);
                        Log.d("month", "|" + month);
                        Log.d("day", "|" + dayOfMonth);

                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                dialog.show();


            }
        });


        Resources resources = this.getResources();
        String[] tmpStringNum = resources.getStringArray(R.array.reminder_cal_choices);
        ArrayList<String> tmpList = new ArrayList<>();
        for(String a : tmpStringNum){
            tmpList.add(a);
        }
        RemindCalAdapter remindCalAdapter = new RemindCalAdapter(this);
        remindCalAdapter.setChoicesData(tmpList);
        reminderCalChoices.setAdapter(remindCalAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.reminder_date:
                break;
            case R.id.reminder_cal:
                Calendar calendar = Calendar.getInstance();
                Dialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                dialog.show();

                break;
            case R.id.submit:
                String remindContent = content.getText().toString();
                ToDoThingModel toDoThingModel = new ToDoThingModel();
                toDoThingModel.setReminderContext(remindContent);
//                ArrayList<ToDoThingModel> a = DatebaseHelper.getInstance().readToBeDoneThings();
                DatebaseHelper.getInstance().addToDoThing(toDoThingModel);

                Intent intent = new Intent("com.wqzhang.thingswapper.activity.MainActivity");
                startActivity(intent);
                break;
        }
    }
}
