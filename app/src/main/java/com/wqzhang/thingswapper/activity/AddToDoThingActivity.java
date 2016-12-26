package com.wqzhang.thingswapper.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.wqzhang.thingswapper.R;
import com.wqzhang.thingswapper.db.DatebaseHelper;
import com.wqzhang.thingswapper.model.ToDoThingModel;

import java.util.ArrayList;

/**
 * Created by wqzhang on 16-12-21.
 */

public class AddToDoThingActivity extends Activity {
    EditText content;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_to_do_thing_main);
        content = (EditText) findViewById(R.id.content);
        submit = (Button) findViewById(R.id.submit);

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (!imm.isActive()) {
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
//        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String remindContent = content.getText().toString();
                ToDoThingModel toDoThingModel = new ToDoThingModel();
                toDoThingModel.setReminderContext(remindContent);
//                ArrayList<ToDoThingModel> a = DatebaseHelper.getInstance().readToBeDoneThings();
                DatebaseHelper.getInstance().addToDoThing(toDoThingModel);

                Intent intent = new Intent("com.wqzhang.thingswapper.activity.MainActivity");
                startActivity(intent);
//                a.get(0);

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

}
