package com.wqzhang.thingswapper.vus;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wqzhang.thingswapper.R;

/**
 * Created by wqzhang on 17-1-11.
 */

public class WelcomeVu implements Vu {

    View view;
    RelativeLayout registerRV, loginRV, checkUserRV;
    TextView registerToLogin, registerGuestComming, loginRegisterUser, loginGuestComming;

    public final int TYPE_LOGIN = 1;
    public final int TYPE_REGISTER = 2;
    public final int TYPE_LOGINING = 3;


    @Override
    public void init(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.welcome_layout, container);
        registerRV = (RelativeLayout) view.findViewById(R.id.register_layout);
        checkUserRV = (RelativeLayout) view.findViewById(R.id.check_user_layout);
        loginRV = (RelativeLayout) view.findViewById(R.id.login_layout);

        registerToLogin = (TextView) view.findViewById(R.id.register_to_login);
        registerGuestComming = (TextView) view.findViewById(R.id.register_guest_comming);
        loginRegisterUser = (TextView) view.findViewById(R.id.login_register_user);
        loginGuestComming = (TextView) view.findViewById(R.id.login_guest_comming);

    }

    @Override
    public View getView() {
        return view;
    }

    public void showFramelayouy(int Type) {
        if (Type == TYPE_REGISTER) {
            checkUserRV.setVisibility(View.INVISIBLE);
            registerRV.setVisibility(View.VISIBLE);
            loginRV.setVisibility(View.INVISIBLE);
        } else if (Type == TYPE_LOGIN) {
            checkUserRV.setVisibility(View.INVISIBLE);
            registerRV.setVisibility(View.INVISIBLE);
            loginRV.setVisibility(View.VISIBLE);
        } else if (Type == TYPE_LOGINING) {
            checkUserRV.setVisibility(View.VISIBLE);
            registerRV.setVisibility(View.INVISIBLE);
            loginRV.setVisibility(View.INVISIBLE);
        }
    }

    public RelativeLayout getRegisterRV() {
        return registerRV;
    }

    public RelativeLayout getLoginRV() {
        return loginRV;
    }

    public RelativeLayout getCheckUserRV() {
        return checkUserRV;
    }

    public TextView getRegisterToLogin() {
        return registerToLogin;
    }

    public TextView getRegisterGuestComming() {
        return registerGuestComming;
    }

    public TextView getLoginRegisterUser() {
        return loginRegisterUser;
    }

    public TextView getLoginGuestComming() {
        return loginGuestComming;
    }
}
