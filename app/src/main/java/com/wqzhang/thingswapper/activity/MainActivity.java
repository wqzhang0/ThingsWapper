package com.wqzhang.thingswapper.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.wqzhang.thingswapper.R;
import com.wqzhang.thingswapper.fragment.ToDoFragment;
import com.wqzhang.thingswapper.db.DatebaseHelper;


public class MainActivity extends Activity {

    private String TAG = "MainActivity";
    private DatebaseHelper datebaseHelper = null;
    private TextView settingTextView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.to_do_main);
        datebaseHelper = DatebaseHelper.getInstance();
        setDefaultFragment();
        settingTextView = (TextView) findViewById(R.id.setting);
        settingTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent("com.wqzhang.thingswapper.activity.AddToDoThingActivity");
                startActivity(intent);
            }
        });
    }

    private void setDefaultFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment f = new ToDoFragment();
        fragmentTransaction.replace(R.id.main_content, f);
        fragmentTransaction.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();

//        datebaseHelper.readUserInfo();

    }

    @Override
    protected void onResume() {
        super.onResume();
        datebaseHelper.readUserInfo();
    }
}
