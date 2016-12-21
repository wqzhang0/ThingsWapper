package com.wqzhang.thingswapper.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String remindContent = content.getText().toString();
                ToDoThingModel toDoThingModel = new ToDoThingModel();
                toDoThingModel.setReminderContext(remindContent);
                ArrayList<ToDoThingModel> a = DatebaseHelper.getInstance().readToBeDoneThings();

                Intent intent = new Intent("com.wqzhang.thingswapper.activity.MainActivity");
                startActivity(intent);
//                DatebaseHelper.getInstance().addToDoThing(toDoThingModel);
//                a.get(0);

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

}
