package com.wqzhang.thingswapper.activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.wqzhang.thingswapper.R;
import com.wqzhang.thingswapper.util.net.model.NetCommom;
import com.wqzhang.thingswapper.util.net.model.ResultFormat;
import com.wqzhang.thingswapper.util.net.model.UserDTO;
import com.wqzhang.thingswapper.util.net.retrofit.IUserBiz;
import com.wqzhang.thingswapper.util.net.retrofit.RetrofitUtil;
import com.wqzhang.thingswapper.vu.RegisterVu;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by wqzhang on 17-3-9.
 */

public class RegisterUserActivity extends BasePartenerAppCompatActivity<RegisterVu> implements View.OnClickListener {
    @Override
    protected void onBind() {
        super.onBind();
        vu.getAddSubmit().setOnClickListener(this);
        vu.getAddCancel().setOnClickListener(this);
    }

    @Override
    View getRootView() {
        return findViewById(R.id.root_view);
    }

    @Override
    public void onClick(final View view) {
        switch (view.getId()) {
            case R.id.add_cancel:
                Intent intent = new Intent("com.wqzhang.thingswapper.activity.MainActivity");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            case R.id.add_submit:
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                }
                vu.setBaffleViewGone();


                String name = vu.getName();
                String account = vu.getAccount();
                String password = vu.getPassword();
                String email = vu.getEmail();

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
                        } else if (resultInfo.getState().equals(NetCommom.OPERATION_FALSE)) {
                            //失败  弹窗显示信息
                            Snackbar.make(view, resultInfo.getErrorMsg(), Snackbar.LENGTH_SHORT).show();
                        }
                        vu.setBaffleViewVisibility();
                    }

                    @Override
                    public void onFailure(Call<ResultFormat<UserDTO>> call, Throwable t) {
                        vu.setBaffleViewVisibility();
                        //与服务器连接失败
                        Snackbar.make(view, "与服务器连接失败", Snackbar.LENGTH_SHORT).show();
                    }
                });
                break;
            default:
                break;
        }
    }

    @Override
    protected Class<RegisterVu> getVuClass() {
        return RegisterVu.class;
    }
}
