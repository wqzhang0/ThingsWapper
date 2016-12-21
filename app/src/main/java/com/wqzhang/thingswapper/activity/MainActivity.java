package com.wqzhang.thingswapper.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.wqzhang.thingswapper.R;
import com.wqzhang.thingswapper.ToDoFragment;
import com.wqzhang.thingswapper.db.DatebaseHelper;
import com.wqzhang.thingswapper.model.UserModel;

import java.util.Date;


public class MainActivity extends Activity {

    private String TAG = "MainActivity";
    private DatebaseHelper datebaseHelper = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.to_do_main);
        datebaseHelper = DatebaseHelper.getInstance();
        setDefaultFragment();

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
