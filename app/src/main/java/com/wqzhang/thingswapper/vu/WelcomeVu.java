package com.wqzhang.thingswapper.vu;

import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wqzhang.thingswapper.R;

/**
 * Created by wqzhang on 17-1-11.
 */

public class WelcomeVu implements Vu {
    private Toolbar toolbar;
    private View view;
    private RelativeLayout registerRV, loginRV, baffleRV;
    private TextView registerToLogin, registerForgetPassword, loginRegisterUser, loginForgetPassword;
    private TextView backTV, baffleTittle, toolbartitle;
    private EditText loginAccountEdit, loginPasswordEdit;
    private EditText registerAccountEdit, registerNameEdit, registerPasswordEdit, registerEmailEdit;

    private Button loginBtn, registerBtn;

    public final int TYPE_LOGIN = 1;
    public final int TYPE_REGISTER = 2;
    public final int TYPE_BAFFLE = 3;
    public final int TYPE_FINDPWD = 4;

    public final String TITTLE_LOGIN = "用户登录";
    public final String TITTLE_REGISTER = "注册用户";
    public final String TITTLE_BAFFLE = "正在操作";
    public final String TITTLE_FINDPWD = "忘记密码";

    @Override
    public void init(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.welcome_layout, container);
        registerRV = (RelativeLayout) view.findViewById(R.id.register_layout);
        baffleRV = (RelativeLayout) view.findViewById(R.id.baffle);
        loginRV = (RelativeLayout) view.findViewById(R.id.login_layout);

        registerToLogin = (TextView) view.findViewById(R.id.register_to_login);
        registerForgetPassword = (TextView) view.findViewById(R.id.register_forget_password);
        loginRegisterUser = (TextView) view.findViewById(R.id.login_register_user);
        loginForgetPassword = (TextView) view.findViewById(R.id.login_forget_password);
        loginBtn = (Button) view.findViewById(R.id.login_login_btn);
        registerBtn = (Button) view.findViewById(R.id.register_register_btn);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        backTV = (TextView) view.findViewById(R.id.add_cancel);
        baffleTittle = (TextView) view.findViewById(R.id.baffle_tittle);
        toolbartitle = (TextView) view.findViewById(R.id.toolbartitle);

        loginAccountEdit = (EditText) view.findViewById(R.id.login_account);
        loginPasswordEdit = (EditText) view.findViewById(R.id.login_password);

        registerNameEdit = (EditText) view.findViewById(R.id.register_name);
        registerAccountEdit = (EditText) view.findViewById(R.id.register_account);
        registerPasswordEdit = (EditText) view.findViewById(R.id.register_password);
        registerEmailEdit = (EditText) view.findViewById(R.id.register_account);

    }

    @Override
    public View getView() {
        return view;
    }

    public void showFramelayouy(int Type, String tittle) {
        toolbartitle.setText(tittle);
        if (Type == TYPE_REGISTER) {
            baffleRV.setVisibility(View.INVISIBLE);
            registerRV.setVisibility(View.VISIBLE);
            loginRV.setVisibility(View.INVISIBLE);
        } else if (Type == TYPE_LOGIN) {
            baffleRV.setVisibility(View.INVISIBLE);
            registerRV.setVisibility(View.INVISIBLE);
            loginRV.setVisibility(View.VISIBLE);
        } else if (Type == TYPE_BAFFLE) {
            baffleTittle.setText(tittle);
            baffleRV.setVisibility(View.VISIBLE);
            registerRV.setVisibility(View.INVISIBLE);
            loginRV.setVisibility(View.INVISIBLE);
        }
    }


    public TextView getRegisterToLogin() {
        return registerToLogin;
    }

    public TextView getRegisterForgetPassword() {
        return registerForgetPassword;
    }

    public TextView getLoginRegisterUser() {
        return loginRegisterUser;
    }

    public TextView getLoginForgetPassword() {
        return loginForgetPassword;
    }

    public TextView getBackTV() {
        return backTV;
    }

    public Button getRegisterBtn() {
        return registerBtn;
    }

    public Button getLoginBtn() {
        return loginBtn;
    }

    public String getLoginAccount() {
        return loginAccountEdit.getText().toString();
    }

    public String getLoginPassword() {
        return loginPasswordEdit.getText().toString();
    }

    public String getRegisterName() {
        return registerNameEdit.getText().toString();
    }

    public String getRegisterAccount() {
        return registerAccountEdit.getText().toString();
    }

    public String getRegisterPassword() {
        return registerPasswordEdit.getText().toString();
    }

    public String getRegisterEmail() {
        return registerEmailEdit.getText().toString();
    }

}
