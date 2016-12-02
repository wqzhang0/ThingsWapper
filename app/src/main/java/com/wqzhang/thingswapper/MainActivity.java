package com.wqzhang.thingswapper;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.wqzhang.thingswapper.UI.DecorationTest;
import com.wqzhang.thingswapper.UI.RecyclerAdapter;


public class MainActivity extends Activity   {

    private String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.to_do_main);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment f = new ToDoFragment();
        fragmentTransaction.replace(R.id.main_content,f);
        fragmentTransaction.commit();

    }


}
