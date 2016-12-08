package com.wqzhang.thingswapper.Activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.wqzhang.thingswapper.R;
import com.wqzhang.thingswapper.ToDoFragment;


public class MainActivity extends Activity {

    private String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.to_do_main);

        setDefaultFragment();

    }

    private void setDefaultFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment f = new ToDoFragment();
        fragmentTransaction.replace(R.id.main_content, f);
        fragmentTransaction.commit();
    }

}
