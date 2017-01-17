package com.wqzhang.thingswapper.activitys;

import android.content.Intent;
import android.view.View;

import com.wqzhang.thingswapper.R;
import com.wqzhang.thingswapper.dao.BusinessProcess;
import com.wqzhang.thingswapper.dao.greendao.User;
import com.wqzhang.thingswapper.vus.WelcomeVu;

/**
 * Created by wqzhang on 17-1-11.
 */

public class WelcomeActivity extends BasePartenerActivity<WelcomeVu> implements View.OnClickListener {

    @Override
    protected Class<WelcomeVu> getVuClass() {
        return WelcomeVu.class;
    }

    @Override
    protected void onBind() {
        super.onBind();
        User user = BusinessProcess.getInstance().getOnlineUser();
        Intent intent = new Intent("com.wqzhang.thingswapper.activity.MainActivity");
        startActivity(intent);

        if (user.getId() == 1) {
            //无用户自己账号登录
            //引导用户进入注册界面

            vu.showFramelayouy(vu.TYPE_REGISTER);
        } else {
            //直接进入用户代做事项界面
            Intent intent1 = new Intent("com.wqzhang.thingswapper.activity.MainActivity");
            startActivity(intent);
        }
        vu.getRegisterToLogin().setOnClickListener(this);
        vu.getRegisterGuestComming().setOnClickListener(this);
        vu.getLoginRegisterUser().setOnClickListener(this);
        vu.getLoginGuestComming().setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register_to_login:
                vu.showFramelayouy(vu.TYPE_LOGIN);
                break;
            case R.id.login_register_user:
                vu.showFramelayouy(vu.TYPE_REGISTER);
                break;
            case R.id.login_guest_comming:
            case R.id.register_guest_comming:
                Intent intent = new Intent("com.wqzhang.thingswapper.activity.MainActivity");
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
