package com.wqzhang.thingswapper.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;

import com.wqzhang.thingswapper.R;
import com.wqzhang.thingswapper.activity.WelcomeActivity;
import com.wqzhang.thingswapper.dao.BusinessProcess;
import com.wqzhang.thingswapper.dao.BusinessProcessImpl;
import com.wqzhang.thingswapper.dao.greendao.User;
import com.wqzhang.thingswapper.listener.AcitivityLinkCallBackListener;
import com.wqzhang.thingswapper.vu.PersonSetVu;

/**
 * Created by wqzhang on 16-12-29.
 */

public class PersonSetFragment extends BasePartenerFragment<PersonSetVu> implements View.OnClickListener {


    private BusinessProcess businessProcess = BusinessProcessImpl.getInstance();

    @Override
    protected Class<PersonSetVu> getVuClass() {
        return PersonSetVu.class;
    }


    public static PersonSetFragment newInstance() {
        return new PersonSetFragment();
    }

    @Override
    protected void onBind() {
        super.onBind();
        User user = BusinessProcessImpl.getInstance().getOnlineUser();
        if (user.getId() != 0) {
            vu.setUserName(user.getName());
            vu.setUserEmail(user.getEmail());
        }

        vu.getUserSwitchUser().setOnClickListener(this);
        vu.getUserLoginOut().setOnClickListener(this);
        vu.getUserSyncData().setOnClickListener(this);
        vu.getUserLinkDefaultUserData().setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.user_switch_user:
                Intent intent = new Intent();
                intent.setAction("com.wqzhang.thingswapper.activity.WelcomeActivity");
                startActivity(intent);
                break;
            case R.id.user_login_out:
                if (!businessProcess.checkOnlineIsDefaultAccount()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("退出");
                    builder.setMessage("确定退出当前用户吗");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Log.d("AGA", "选择了确定");
                            handler.sendEmptyMessage(1);
                        }
                    });
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Log.d("AGA", "选择了跳过");
                        }
                    });
                    builder.show();
                } else {
                    Snackbar.make(view, "您还没有登录任何账户", Snackbar.LENGTH_SHORT).show();
                }

                break;
            case R.id.user_sync_data:
                if (businessProcess.checkOnlineIsDefaultAccount()) {
                    Snackbar.make(view, "您还没有登录任何账户", Snackbar.LENGTH_SHORT).show();
                } else {

                }
                break;
            case R.id.user_link_default_user_data:
                if (businessProcess.checkOnlineIsDefaultAccount()) {
                    Snackbar.make(view, "您还没有登录任何账户", Snackbar.LENGTH_SHORT).show();
                } else {
                    if (BusinessProcessImpl.getInstance().checkHaveDataLinkToOnlineAccount()) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("注意");
                        builder.setMessage("有东西需要同步");
                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Log.d("AGA", "选择了确定");
                                handler.sendEmptyMessage(2);
                            }
                        });
                        builder.setNegativeButton("跳过", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Log.d("AGA", "选择了跳过");
                            }
                        });
                        builder.show();
                    } else {
                        Snackbar.make(view, "没有内容需要同步", Snackbar.LENGTH_SHORT).show();
                    }
                }
                //注册成功后检测是否有默认账号下的数据需要同步,


                break;

            default:
                break;
        }
    }

    public void refreshData() {
        User user = BusinessProcessImpl.getInstance().getOnlineUser();
        if (user.getId() != 0 && vu != null) {
            vu.setUserName(user.getName());
            vu.setUserEmail(user.getEmail());
        }
    }


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    businessProcess.updateUserLoginState(businessProcess.getDefaultUser());
                    break;
                case 2:
//                    vu.showFramelayouy(vu.TYPE_BAFFLE, "正在进行数据库转移操作,勿动!");
                    callBack(1);
                    if (BusinessProcessImpl.getInstance().moveDataToOnlineAccount()) {
                        callBack(2);
//                        Intent toMain = new Intent("com.wqzhang.thingswapper.activity.MainActivity");
//                        startActivity(toMain);
                    }
                    break;
            }
        }
    };

    @Override
    public void callBack(int type) {
        acitivityLinkCallBackListener.callBack(type);
    }
}
