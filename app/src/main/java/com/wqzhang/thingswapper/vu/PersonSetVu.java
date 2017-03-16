package com.wqzhang.thingswapper.vu;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wqzhang.thingswapper.R;

/**
 * Created by wqzhang on 17-1-13.
 */

public class PersonSetVu implements Vu {
    private final String TAG = "PersonSetVu";
    View view;
    LinearLayout linearLayout;
    RelativeLayout userLoginOut, userSwitchUser, userSyncData, userLinkDefaultUserData;
    TextView userNameTV, userEmialTV;


    @Override
    public void init(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.persion_setting_layout, container, false);

        linearLayout = (LinearLayout) view.findViewById(R.id.user_info_layout);
        userNameTV = (TextView) view.findViewById(R.id.user_info_account);
        userEmialTV = (TextView) view.findViewById(R.id.user_info_email);
        userLoginOut = (RelativeLayout) view.findViewById(R.id.user_login_out);
        userSwitchUser = (RelativeLayout) view.findViewById(R.id.user_switch_user);
        userSyncData = (RelativeLayout) view.findViewById(R.id.user_sync_data);
        userLinkDefaultUserData = (RelativeLayout) view.findViewById(R.id.user_link_default_user_data);
    }

    @Override
    public View getView() {
        return view;
    }


    public LinearLayout getLinearLayout() {
        return linearLayout;
    }

    public RelativeLayout getUserLoginOut() {
        return userLoginOut;
    }

    public RelativeLayout getUserSwitchUser() {
        return userSwitchUser;
    }

    public RelativeLayout getUserSyncData() {
        return userSyncData;
    }

    public RelativeLayout getUserLinkDefaultUserData() {
        return userLinkDefaultUserData;
    }

    public void setUserName(String name) {
        userNameTV.setText(name);
    }

    public void setUserEmail(String email) {
        userEmialTV.setText(email);
    }
}
