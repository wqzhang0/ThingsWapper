package com.wqzhang.thingswapper.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.wqzhang.thingswapper.R;
import com.wqzhang.thingswapper.dao.BusinessProcessImpl;
import com.wqzhang.thingswapper.dao.greendao.User;
import com.wqzhang.thingswapper.util.MD5;
import com.wqzhang.thingswapper.util.net.model.NetCommom;
import com.wqzhang.thingswapper.util.net.model.ResultFormat;
import com.wqzhang.thingswapper.util.net.model.UserDTO;
import com.wqzhang.thingswapper.util.net.netConvert.UserParseTools;
import com.wqzhang.thingswapper.util.net.retrofit.IUserBiz;
import com.wqzhang.thingswapper.util.net.retrofit.RetrofitUtil;
import com.wqzhang.thingswapper.vu.WelcomeVu;

import java.security.NoSuchAlgorithmException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by wqzhang on 17-1-11.
 */

public class WelcomeActivity extends BasePartenerActivity<WelcomeVu> implements View.OnClickListener {

    @Override
    protected Class<WelcomeVu> getVuClass() {
        return WelcomeVu.class;
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    //同步
                    vu.showFramelayouy(vu.TYPE_BAFFLE, "正在进行数据库转移操作,勿动!");
                    if (BusinessProcessImpl.getInstance().moveDataToOnlineAccount()) {
                        Intent toMain = new Intent("com.wqzhang.thingswapper.activity.MainActivity");
                        startActivity(toMain);
                    }
                    break;
                case 2:
                    //跳过
                    Intent toMain = new Intent("com.wqzhang.thingswapper.activity.MainActivity");
                    startActivity(toMain);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onBind() {

        super.onBind();
        User user = BusinessProcessImpl.getInstance().getOnlineUser();
        if (user.getId() == 1) {
            //默认账号登录
            //引导用户进入注册界面
            vu.showFramelayouy(vu.TYPE_LOGIN, vu.TITTLE_LOGIN);
        } else {
            //直接进入用户代做事项界面
//            Intent intent = new Intent("com.wqzhang.thingswapper.activity.MainActivity");
//            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//
//            startActivity(intent);
            vu.showFramelayouy(vu.TYPE_LOGIN, vu.TITTLE_LOGIN);
        }
        vu.getRegisterToLogin().setOnClickListener(this);
        vu.getRegisterForgetPassword().setOnClickListener(this);
        vu.getLoginRegisterUser().setOnClickListener(this);
        vu.getLoginForgetPassword().setOnClickListener(this);
        vu.getBackTV().setOnClickListener(this);
        vu.getLoginBtn().setOnClickListener(this);
        vu.getRegisterBtn().setOnClickListener(this);
    }


    @Override
    public void onClick(final View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }

        switch (view.getId()) {
            case R.id.login_login_btn:
                try {
                    String password = vu.getLoginPassword();
                    String account = vu.getLoginAccount();
                    if (TextUtils.isEmpty(password) || TextUtils.isEmpty(account)) {
                        Snackbar.make(view, "账号或者密码不能为空", Snackbar.LENGTH_SHORT).show();
                        return;
                    }
                    password = MD5.getMD5(password);
                    UserDTO userDTO = new UserDTO();
                    userDTO.setAccount(account);
                    userDTO.setPassword(password);
                    userDTO.setEmail(account);
                    vu.showFramelayouy(vu.TYPE_BAFFLE, "正在登录,请稍后");
                    Retrofit retrofit = RetrofitUtil.createRetrofit();
                    IUserBiz userBiz = retrofit.create(IUserBiz.class);

                    Call<ResultFormat<UserDTO>> call = userBiz.login(userDTO);
                    call.enqueue(new Callback<ResultFormat<UserDTO>>() {
                        @Override
                        public void onResponse(Call<ResultFormat<UserDTO>> call, Response<ResultFormat<UserDTO>> response) {
                            ResultFormat<UserDTO> resultInfo = response.body();
                            if (resultInfo.getState().equals(NetCommom.OPERATION_SUCESS)) {

                                Snackbar.make(view, "登录成功 欢迎用户 :" + resultInfo.getT().getName(), Snackbar.LENGTH_SHORT).show();
                                User resultUser = UserParseTools.userDTO2UserWithPwd(resultInfo.getT());
                                BusinessProcessImpl.getInstance().insertOrUpdateUserInfo(resultUser);
                                BusinessProcessImpl.getInstance().updateUserLoginState(resultUser);

                                //注册成功后检测是否有默认账号下的数据需要同步,
                                if (BusinessProcessImpl.getInstance().checkHaveDataLinkToOnlineAccount()) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(WelcomeActivity.this);
                                    builder.setTitle("注意");
                                    builder.setMessage("有东西需要同步");
                                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            Log.d("AGA", "选择了确定");
                                            handler.sendEmptyMessage(1);
                                        }
                                    });
                                    builder.setNegativeButton("跳过", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            Log.d("AGA", "选择了跳过");
                                            handler.sendEmptyMessage(2);
                                        }
                                    });
                                    builder.show();
                                } else {
                                    Intent toMain = new Intent("com.wqzhang.thingswapper.activity.MainActivity");
                                    startActivity(toMain);
                                }
                            } else if (resultInfo.getState().equals(NetCommom.OPERATION_FALSE)) {
                                //失败  弹窗显示信息
                                Snackbar.make(view, resultInfo.getErrorMsg(), Snackbar.LENGTH_SHORT).show();
                            }
                            vu.showFramelayouy(vu.TYPE_LOGIN, vu.TITTLE_LOGIN);
                        }

                        @Override
                        public void onFailure(Call<ResultFormat<UserDTO>> call, Throwable t) {
                            vu.showFramelayouy(vu.TYPE_LOGIN, vu.TITTLE_LOGIN);
                            //与服务器连接失败
                            Snackbar.make(view, "与服务器连接失败", Snackbar.LENGTH_SHORT).show();
                        }
                    });

                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                    //与服务器连接失败
                    Snackbar.make(view, "密码加密失败", Snackbar.LENGTH_SHORT).show();
                }
                break;
            case R.id.register_register_btn:
                vu.showFramelayouy(vu.TYPE_BAFFLE, vu.TITTLE_BAFFLE);

                String name = vu.getRegisterName();
                String account = vu.getRegisterAccount();
                String password = vu.getRegisterPassword();
                String email = vu.getRegisterEmail();

                UserDTO userDTO = new UserDTO(name, account, password, email);

                Retrofit retrofit = RetrofitUtil.createRetrofit();
                IUserBiz userBiz = retrofit.create(IUserBiz.class);

                Call<ResultFormat<UserDTO>> call = userBiz.register(userDTO);
                call.enqueue(new Callback<ResultFormat<UserDTO>>() {
                    @Override
                    public void onResponse(Call<ResultFormat<UserDTO>> call, Response<ResultFormat<UserDTO>> response) {
//                        call.request().body();
                        ResultFormat<UserDTO> resultInfo = response.body();
                        if (resultInfo.getState().equals(NetCommom.OPERATION_SUCESS)) {
                            //成功
                            Snackbar.make(view, "注册成功 欢迎用户 :" + resultInfo.getT().getName(), Snackbar.LENGTH_SHORT).show();
                            User resultUser = UserParseTools.userDTO2UserWithPwd(resultInfo.getT());
                            BusinessProcessImpl.getInstance().insertOrUpdateUserInfo(resultUser);
                            BusinessProcessImpl.getInstance().updateUserLoginState(resultUser);

                            //注册成功后检测是否有默认账号下的数据需要同步,
                            if (BusinessProcessImpl.getInstance().checkHaveDataLinkToOnlineAccount()) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(WelcomeActivity.this);
                                builder.setTitle("注意");
                                builder.setMessage("有东西需要同步");
                                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Log.d("AGA", "选择了确定");
                                        handler.sendEmptyMessage(1);
                                    }
                                });
                                builder.setNegativeButton("跳过", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Log.d("AGA", "选择了跳过");
                                        handler.sendEmptyMessage(2);
                                    }
                                });
                                builder.show();
                            } else {
                                Intent toMain = new Intent("com.wqzhang.thingswapper.activity.MainActivity");
                                startActivity(toMain);
                            }
                        } else if (resultInfo.getState().equals(NetCommom.OPERATION_FALSE)) {
                            //失败  弹窗显示信息
                            Snackbar.make(view, resultInfo.getErrorMsg(), Snackbar.LENGTH_SHORT).show();
                        }
                        vu.showFramelayouy(vu.TYPE_REGISTER, vu.TITTLE_REGISTER);
                    }

                    @Override
                    public void onFailure(Call<ResultFormat<UserDTO>> call, Throwable t) {
                        vu.showFramelayouy(vu.TYPE_REGISTER, vu.TITTLE_REGISTER);
                        //与服务器连接失败
                        Snackbar.make(view, "与服务器连接失败", Snackbar.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.register_to_login:
                vu.showFramelayouy(vu.TYPE_LOGIN, vu.TITTLE_LOGIN);
                break;
            case R.id.login_register_user:
                vu.showFramelayouy(vu.TYPE_REGISTER, vu.TITTLE_REGISTER);
                break;
            case R.id.login_forget_password:
            case R.id.register_forget_password:
                Intent intent = new Intent("com.wqzhang.thingswapper.activity.MainActivity");
                startActivity(intent);
                break;
            case R.id.add_cancel:
                Intent toMainAcitivityIntent = new Intent("com.wqzhang.thingswapper.activity.MainActivity");
                toMainAcitivityIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(toMainAcitivityIntent);
                break;
            default:
                break;
        }
    }
}
