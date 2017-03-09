package com.wqzhang.thingswapper.vu;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wqzhang.thingswapper.R;
import com.wqzhang.thingswapper.fragment.PersonSetFragment;
import com.wqzhang.thingswapper.fragment.PoolFragment;
import com.wqzhang.thingswapper.fragment.ShowThingsFragment;

/**
 * Created by wqzhang on 17-1-5.
 */

public class RegisterVu implements Vu {
    private View view;
    private Toolbar toolbar;
    private EditText nameEdit, accountEdit, passwordEdit, emailEdit;
    TextView addCancel, addSubmit;
    View baffleView;

    @Override
    public void init(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.register_user_layout, container, false);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        addCancel = (TextView) view.findViewById(R.id.add_cancel);
        addSubmit = (TextView) view.findViewById(R.id.add_submit);
        nameEdit = (EditText) view.findViewById(R.id.register_name);
        accountEdit = (EditText) view.findViewById(R.id.register_account);
        passwordEdit = (EditText) view.findViewById(R.id.register_password);
        emailEdit = (EditText) view.findViewById(R.id.register_email);
        baffleView = view.findViewById(R.id.baffle);
    }

    @Override
    public View getView() {
        return view;
    }

    public Toolbar getToolBar() {
        return toolbar;
    }

    public String getAccount() {
        return accountEdit.getText().toString();
    }

    public String getName() {
        return nameEdit.getText().toString();
    }

    public String getPassword() {
        return passwordEdit.getText().toString();
    }

    public String getEmail() {
        return emailEdit.getText().toString();
    }

    public TextView getAddCancel() {
        return addCancel;
    }

    public TextView getAddSubmit() {
        return addSubmit;
    }

    public void setBaffleViewVisibility() {
        baffleView.setVisibility(View.GONE);
        addSubmit.setClickable(true);
    }

    public void setBaffleViewGone() {
        baffleView.setVisibility(View.VISIBLE);
        addSubmit.setClickable(false);
    }
}
